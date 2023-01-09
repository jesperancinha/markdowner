package org.jesperancinha.parser.markdowner.badges.model

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Value

@Value
@Builder
@AllArgsConstructor
class BadgeSettingGroup {
    var badgeType: BadgeType? = null
    var badgeSettingList: List<BadgePattern>? = null
}