package org.jesperancinha.parser.markdowner.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jesperancinha.parser.markdowner.helper.TagHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class Paragraphs {

    private final Map<String, Paragraph> allParagraphs;

    private final List<String> tags;

    public int getParagraphCount() {
        return allParagraphs.size();
    }

    public Paragraph getParagraphByTag(String tag) {
        return allParagraphs.get(tag);
    }

    public static class ParagraphsBuilder {

        private final Map<String, Paragraph> allParagraphs = new HashMap<>();

        private final List<String> tags = new ArrayList<>();

        public ParagraphsBuilder withTagParagraph(String tag, String paragraph) {
            final String sanitizedTag = TagHelper.sanitizeTag(tag);
            allParagraphs.put(sanitizedTag, (Paragraph.builder().tag(tag).text(paragraph)).build());
            tags.add(sanitizedTag);
            return this;
        }

        public Paragraphs build() {
            return new Paragraphs(allParagraphs, tags);

        }
    }
}
