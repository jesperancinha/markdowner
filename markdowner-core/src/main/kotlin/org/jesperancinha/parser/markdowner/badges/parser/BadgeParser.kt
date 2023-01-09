package org.jesperancinha.parser.markdowner.badges.parser

import com.fasterxml.jackson.databind.ObjectMapper
import lombok.extern.slf4j.Slf4j
import org.jesperancinha.parser.markdowner.badges.model.*
import java.io.IOException
import java.util.*
import java.util.function.BiConsumer
import java.util.function.Function
import java.util.function.Supplier
import java.util.regex.Pattern
import java.util.stream.Collectors

@Slf4j
object BadgeParser {
    private val objectMapper = ObjectMapper()
    private const val GENERIC_REGEX = "a-zA-Z0-9\\/\\.\\]\\?\\=\\-\\&\\%%\\;\\_\\#\\:\\@\\\"\\ "
    private const val EMOJI_REGEX = ".{0,4}"
    const val FULL_REGEX = GENERIC_REGEX + EMOJI_REGEX
    private const val BADGE_REGEX =
        "(\\[!\\[%s]\\(http[s]*:\\/\\/%s[" + GENERIC_REGEX + "]*" + EMOJI_REGEX + "[" + GENERIC_REGEX + "]*" + "\\)]\\((http[s]*:\\/\\/)*[" + GENERIC_REGEX + "]*\\))"
    private val NOT_ACCEPTED_REGEX = Pattern.compile("color=(?!(informational)).")
    val badgeTypes = parseBadgeTypes()
    private fun parseBadgeTypes(): Map<String, BadgeType>? {
        try {
            return Arrays.stream(
                objectMapper.readValue(
                    BadgeParser::class.java.getResourceAsStream("/jeorg.badges.types.json"),
                    Array<BadgeType>::class.java
                )
            )
                .collect(Collectors.toMap(
                    Function { obj: BadgeType -> obj.type }, Function { badgeType: BadgeType -> badgeType })
                )
        } catch (e: IOException) {
            BadgeParser.log.error("Error!", e)
            System.exit(1)
        }
        return null
    }

    val badgeSettingGroups = parseSettings()
    fun parse(readmeText: String?): Map<BadgeType, BadgeGroup> {
        return badgeSettingGroups
            .values
            .stream()
            .filter { obj: BadgeSettingGroup? -> Objects.nonNull(obj) }
            .collect(Collectors.toMap(
                Function { obj: BadgeSettingGroup? -> obj.getBadgeType() },
                Function { badgeSettingGroup: BadgeSettingGroup? ->
                    val allBadges: Map<Pattern, Badge> = badgeSettingGroup
                        .getBadgeSettingList()
                        .stream()
                        .filter { obj: BadgePattern? -> Objects.nonNull(obj) }
                        .collect(
                            Supplier<HashMap<Pattern, Badge>> { HashMap() },
                            BiConsumer<HashMap<Pattern?, Badge?>, BadgePattern> { map: HashMap<Pattern?, Badge?>, badgeSetting: BadgePattern ->
                                val matcher = badgeSetting.pattern.matcher(readmeText)
                                if (matcher.find()) {
                                    val badgeText = matcher.group(0)
                                    val matcher1 = NOT_ACCEPTED_REGEX.matcher(badgeText)
                                    val linkPrefix = badgeSetting.linkPrefix
                                    if (matcher1.find() || linkPrefix != null && !badgeText.contains(linkPrefix)) {
                                        map[badgeSetting.pattern] = null
                                    } else {
                                        map[badgeSetting.pattern] = Badge.builder()
                                            .badgeText(badgeText)
                                            .title(badgeSetting.title)
                                            .build()
                                    }
                                } else {
                                    map[badgeSetting.pattern] = null
                                }
                            },
                            BiConsumer<HashMap<Pattern?, Badge?>, HashMap<Pattern?, Badge?>> { obj: HashMap<Pattern?, Badge?>, m: HashMap<Pattern?, Badge?>? ->
                                obj.putAll(
                                    m!!
                                )
                            })
                    BadgeGroup.builder()
                        .badgeType(badgeSettingGroup.getBadgeType())
                        .badgeHashMap(allBadges)
                        .build()
                }
            ))
    }

    fun parseSettings(): Map<BadgeType, BadgeSettingGroup?> {
        return badgeTypes!!.values.stream()
            .collect(
                Collectors.toMap(
                    Function { badgeType: BadgeType -> badgeType },
                    Function<BadgeType, BadgeSettingGroup?> { badgeType: BadgeType ->
                        try {
                            val badgeSettingList = Arrays.stream(
                                objectMapper.readValue(
                                    BadgeParser::class.java.getResourceAsStream("/" + badgeType.badgeFile),
                                    Array<BadgeSetting>::class.java
                                )
                            ).collect(Collectors.toList())
                            return@toMap BadgeSettingGroup.builder()
                                .badgeType(badgeType)
                                .badgeSettingList(
                                    badgeSettingList
                                        .stream().map { badgeSetting: BadgeSetting ->
                                            BadgePattern.builder()
                                                .title(badgeSetting.title)
                                                .pattern(
                                                    Pattern.compile(
                                                        String.format(
                                                            BADGE_REGEX,
                                                            badgeSetting.badge,
                                                            badgeSetting.codePrefix
                                                                .replace(".", "\\.")
                                                                .replace("/", "\\/")
                                                        )
                                                    )
                                                )
                                                .linkPrefix(badgeSetting.linkPrefix)
                                                .build()
                                        }
                                        .collect(Collectors.toList())
                                )
                                .build()
                        } catch (e: IOException) {
                            BadgeParser.log.error("Error", e)
                        }
                        null
                    })
            )
    }
}