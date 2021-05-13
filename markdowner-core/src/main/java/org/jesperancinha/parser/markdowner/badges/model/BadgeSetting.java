package org.jesperancinha.parser.markdowner.badges.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor_ = @JsonCreator)
public class BadgeSetting {
    String badge;
    String codePrefix;
}
