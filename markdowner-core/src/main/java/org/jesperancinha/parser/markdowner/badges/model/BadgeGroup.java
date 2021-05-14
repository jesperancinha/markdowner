package org.jesperancinha.parser.markdowner.badges.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Map;
import java.util.regex.Pattern;

@Value
@Builder
@AllArgsConstructor
public class BadgeGroup {
    BadgeType badgeType;
    Map<Pattern, Badge> badgeHashMap;
}
