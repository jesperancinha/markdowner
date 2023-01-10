package org.jesperancinha.parser.markdowner.badges.model

import java.util.regex.Pattern

class BadgePattern(
    var title: String? = null,
    var pattern: Pattern,
    var linkPrefix: String? = null
)