package org.jesperancinha.parser.markdowner.badges.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class BadgeSettingGroup {
    BadgeType badgeType;
    List<BadgePattern> badgeSettingList;
}
