package org.jesperancinha.parser.markdowner.badges.model

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Value
import java.util.regex.Pattern

@Value
@Builder
@AllArgsConstructor
class BadgePattern {
    var title: String? = null
    var pattern: Pattern? = null
    var linkPrefix: String? = null
}