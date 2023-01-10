package org.jesperancinha.parser.markdowner.badges.model

import java.util.regex.Pattern

class BadgeGroup (
    var badgeType: BadgeType? = null,
    var badgeHashMap: Map<Pattern, Badge?>
)