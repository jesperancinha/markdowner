package org.jesperancinha.projectsigner.model;

import static org.jesperancinha.projectsigner.utils.StandardUtils.sanitizeTag;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class Paragraphs {

    private final Map<String, Paragraph> allParagraphs;

    public int getParagraphCount() {
        return allParagraphs.size();
    }

    public Paragraph getParagraphByTag(String tag) {
        return allParagraphs.get(tag);
    }

    public static class ParagraphsBuilder {

        private final Map<String, Paragraph> allParagraphs = new HashMap<>();

        public ParagraphsBuilder withTagParagraph(String tag, String paragraph) {
            allParagraphs.put(sanitizeTag(tag), (Paragraph.builder().tag(tag).text(paragraph)).build());
            return this;
        }


        public Paragraphs build() {
            return new Paragraphs(allParagraphs);

        }
    }
}
