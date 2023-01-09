package org.jesperancinha.parser.markdowner.badges.model

import lombok.AllArgsConstructor
import lombok.Value

@Value
@AllArgsConstructor
class BadgeType {
    var title: String? = null
    var type: String? = null
    var badgeFile: String? = null
    var destinationFile: String? = null
}