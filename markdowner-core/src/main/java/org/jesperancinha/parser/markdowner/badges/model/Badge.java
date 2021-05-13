package org.jesperancinha.parser.markdowner.badges.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor_ = @JsonCreator)
public class Badge {
    String badge;
    String code;
    String link;
}
