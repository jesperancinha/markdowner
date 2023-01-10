package org.jesperancinha.parser.markdowner.badges.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.regex.Pattern

data class Badge (
    @JsonProperty("title")
    var title: String? = null,
    @JsonProperty("badgeText")
    var badgeText: String? = null
)

data class BadgeGroup (
    @JsonProperty("badgeType")
    var badgeType: BadgeType? = null,
    @JsonProperty("badgeHashMap")
    var badgeHashMap: Map<Pattern, Badge?>
)

data class BadgePattern(
    @JsonProperty("title")
    var title: String? = null,
    @JsonProperty("pattern")
    var pattern: Pattern,
    @JsonProperty("linkPrefix")
    var linkPrefix: String? = null
)

data class BadgeSetting (
    @JsonProperty("title")
    var title: String? = null,
    @JsonProperty("badge")
    var badge: String? = null,
    @JsonProperty("codePrefix")
    var codePrefix: String,
    @JsonProperty("linkPrefix")
    var linkPrefix: String? = null
)

data class BadgeSettingGroup(
    @JsonProperty("badgeType")
    var badgeType: BadgeType,
    @JsonProperty("badgeSettingList")
    var badgeSettingList: List<BadgePattern>
)

data class BadgeType (
    @JsonProperty("title")
    var title: String? = null,
    @JsonProperty("type")
    var type: String,
    @JsonProperty("badgeFile")
    var badgeFile: String,
    @JsonProperty("destinationFile")
    var destinationFile: String? = null
)