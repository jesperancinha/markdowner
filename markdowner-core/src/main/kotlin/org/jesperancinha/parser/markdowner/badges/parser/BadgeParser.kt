package org.jesperancinha.parser.markdowner.badges.parser

import com.fasterxml.jackson.databind.ObjectMapper
import org.jesperancinha.parser.markdowner.badges.model.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.*
import java.util.regex.Pattern
import kotlin.system.exitProcess

class BadgeParser {

    companion object{
        private const val GENERIC_REGEX = "a-zA-Z0-9\\/\\.\\]\\?\\=\\-\\&\\%%\\;\\_\\#\\:\\@\\\"\\ "
        private const val EMOJI_REGEX = ".{0,4}"
        const val FULL_REGEX = GENERIC_REGEX + EMOJI_REGEX
        private const val BADGE_REGEX =
            "(\\[!\\[%s]\\(http[s]*:\\/\\/%s[$GENERIC_REGEX]*$EMOJI_REGEX[$GENERIC_REGEX]*\\)]\\((http[s]*:\\/\\/)*[$GENERIC_REGEX]*\\))"
        private val NOT_ACCEPTED_REGEX = Pattern.compile("color=(?!(informational)).")
        private val objectMapper = ObjectMapper()
        private val logger: Logger = LoggerFactory.getLogger(BadgeParser::class.java)

        private val badgeTypes = parseBadgeTypes()
        private fun parseBadgeTypes(): Map<String, BadgeType> = try {
            objectMapper.readValue(
                BadgeParser::class.java.getResourceAsStream("/jeorg.badges.types.json"),
                Array<BadgeType>::class.java
            ).associateBy { it.type }
        } catch (e: IOException) {
            logger.error("Error!", e)
            exitProcess(1)
        }

        val badgeSettingGroups = parseSettings()

        fun parse(readmeText: String): Map<BadgeType, BadgeGroup> {
            return badgeSettingGroups.values
                .filterNotNull().associate { badgeSettingGroup ->
                    val allBadges = badgeSettingGroup.badgeSettingList.associate { badgePattern ->
                        badgePattern.pattern to run {
                            val matcher = badgePattern.pattern.matcher(readmeText)
                            if (matcher.find()) {
                                val badgeText = matcher.group(0)
                                val matcher1 = NOT_ACCEPTED_REGEX.matcher(badgeText)
                                val linkPrefix = badgePattern.linkPrefix
                                if (matcher1.find() || linkPrefix != null && !badgeText.contains(linkPrefix)) {
                                    null
                                } else {
                                    Badge(
                                        badgeText = badgeText,
                                        title = badgePattern.title
                                    )
                                }
                            } else {
                                null
                            }
                        }
                    }
                    badgeSettingGroup.badgeType to BadgeGroup(
                        badgeType = badgeSettingGroup.badgeType,
                        badgeHashMap = allBadges
                    )
                }
        }

        fun parseSettings(): Map<BadgeType, BadgeSettingGroup?> =
            badgeTypes.values.associateWith { badgeType ->
                try {
                    val badgeSettingList = objectMapper.readValue(
                        BadgeParser::class.java.getResourceAsStream("/" + badgeType.badgeFile),
                        Array<BadgeSetting>::class.java
                    )
                    BadgeSettingGroup(
                        badgeType = badgeType,
                        badgeSettingList = badgeSettingList.map { badgeSetting ->
                            BadgePattern(
                                title = badgeSetting.title,
                                pattern = Pattern.compile(
                                    String.format(
                                        BADGE_REGEX,
                                        badgeSetting.badge,
                                        badgeSetting.codePrefix
                                            .replace(".", "\\.")
                                            .replace("/", "\\/")
                                    )
                                ),
                                linkPrefix = badgeSetting.linkPrefix
                            )
                        }
                    )
                } catch (e: IOException) {
                    logger.error("Error", e)
                    null
                }
            }

    }
}