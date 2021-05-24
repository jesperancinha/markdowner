package org.jesperancinha.parser.markdowner.badges.model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class BadgeType {
    String title;

    String type;

    String badgeFile;

    String destinationFile;
}
