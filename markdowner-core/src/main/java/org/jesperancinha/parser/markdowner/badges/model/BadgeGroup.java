package org.jesperancinha.parser.markdowner.badges.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Value
@Builder
@AllArgsConstructor(onConstructor_ = @JsonCreator)
public class BadgeGroup {
    BadgeType badgeType;
    Map<Pattern, Badge> badgeHashMap;
}
