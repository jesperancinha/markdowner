package org.jesperancinha.parser.markdowner.badges.model

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Value

@Value
@Builder
@AllArgsConstructor
class Badge {
    var title: String? = null
    var badgeText: String? = null
}