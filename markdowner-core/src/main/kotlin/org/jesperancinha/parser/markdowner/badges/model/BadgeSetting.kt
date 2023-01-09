package org.jesperancinha.parser.markdowner.badges.model

import lombok.AllArgsConstructor
import lombok.Value

@Value
@AllArgsConstructor
class BadgeSetting {
    var title: String? = null
    var badge: String? = null
    var codePrefix: String? = null
    var linkPrefix: String? = null
}