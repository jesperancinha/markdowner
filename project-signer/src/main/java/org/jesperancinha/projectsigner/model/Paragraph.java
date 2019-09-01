package org.jesperancinha.projectsigner.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Paragraph {
    private final String tag;
    private final String text;
}
