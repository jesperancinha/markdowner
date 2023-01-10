package org.jesperancinha.parser.markdowner.badges.parser

import com.fasterxml.jackson.databind.ObjectMapper
import org.jesperancinha.parser.markdowner.badges.model.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.*
import java.util.function.BiConsumer
import java.util.function.Function
import java.util.function.Supplier
import java.util.regex.Pattern
import java.util.stream.Collectors
import kotlin.collections.HashMap
import kotlin.system.exitProcess

object BadgeParser {
    private val objectMapper = ObjectMapper()
    private const val GENERIC_REGEX = "a-zA-Z0-9\\/\\.\\]\\?\\=\\-\\&\\%%\\;\\_\\#\\:\\@\\\"\\ "
    private const val EMOJI_REGEX = ".{0,4}"
    const val FULL_REGEX = GENERIC_REGEX + EMOJI_REGEX
    private const val BADGE_REGEX =
        "(\\[!\\[%s]\\(http[s]*:\\/\\/%s[" + GENERIC_REGEX + "]*" + EMOJI_REGEX + "[" + GENERIC_REGEX + "]*" + "\\)]\\((http[s]*:\\/\\/)*[" + GENERIC_REGEX + "]*\\))"
    private val NOT_ACCEPTED_REGEX = Pattern.compile("color=(?!(informational)).")
    val badgeTypes = parseBadgeTypes()
    val logger: Logger = LoggerFactory.getLogger(BadgeParser::class.java)
    private fun parseBadgeTypes(): Map<String, BadgeType>? = try {
        objectMapper.readValue(
            BadgeParser::class.java.getResourceAsStream("/jeorg.badges.types.json"),
            Array<BadgeType>::class.java
        ).associateBy { it.type }
    } catch (e: IOException) {
            logger.error("Error!", e)
            exitProcess(1)
        }

    val badgeSettingGroups = parseSettings()
    fun parse(readmeText: String?): Map<BadgeType, BadgeGroup> {
        return badgeSettingGroups
            .values
            .filterNotNull()
            .groupByTo(HashMap(),{ obj: BadgeSettingGroup -> obj.badgeType },
                 { badgeSettingGroup: BadgeSettingGroup ->
                    val allBadges = badgeSettingGroup
                        .badgeSettingList
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
                    BadgeGroup(
                        badgeType = badgeSettingGroup.badgeType,
                        badgeHashMap = allBadges
                    )
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