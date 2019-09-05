package org.jesperancinha.parser.model;

import lombok.Builder;
import lombok.Getter;

import static java.lang.System.lineSeparator;

@Getter
@Builder
public class Paragraph {
    private final String tag;
    private final String text;

    @Override
    public String toString() {
        return tag
                .concat(lineSeparator())
                .concat(text)
                .concat(lineSeparator());
    }
}
