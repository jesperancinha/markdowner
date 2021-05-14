package org.jesperancinha.parser.markdowner.badges.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class Badge {
    String title;
    String badgeText;
}
