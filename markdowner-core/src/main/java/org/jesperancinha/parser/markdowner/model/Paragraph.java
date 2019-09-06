package org.jesperancinha.parser.markdowner.model;

import static java.lang.System.lineSeparator;

import lombok.Builder;
import lombok.Getter;

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
