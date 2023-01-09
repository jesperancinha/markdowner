package org.jesperancinha.parser.markdowner.badges.model

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Value
import java.util.regex.Pattern

@Value
@Builder
@AllArgsConstructor
class BadgeGroup {
    var badgeType: BadgeType? = null
    var badgeHashMap: Map<Pattern, Badge>? = null
}