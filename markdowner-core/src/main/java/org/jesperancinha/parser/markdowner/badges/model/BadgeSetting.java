package org.jesperancinha.parser.markdowner.badges.model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class BadgeSetting {
    String title;
    String badge;
    String codePrefix;
    String linkPrefix;
}
