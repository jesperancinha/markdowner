package org.jesperancinha.parser.markdowner.helper;

import org.jesperancinha.parser.markdowner.parser.Paragraphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import static org.jesperancinha.parser.markdowner.helper.TagHelper.counHashTags;

public class TemplateParserHelper {

    private TemplateParserHelper() {
    }

    /**
     * Receives an input markdown text stream nd parses its content to a Paragraphs object see {@link Paragraphs}
     *
     * @param templateInputStream An input markdown text stream
     * @return A {@link Paragraphs} parsed object
     * @throws IOException Any kind of IO Exception
     */
    public static Paragraphs findAllParagraphs(final InputStream templateInputStream) throws IOException {
        Paragraphs.ParagraphsBuilder paragraphsBuilder = new Paragraphs.ParagraphsBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(templateInputStream))) {
            final StringBuilder sb = new StringBuilder();
            final ParagraphCounter paragraphCounter = new ParagraphCounter();
            while ((paragraphCounter.line = br.readLine()) != null) {
                if (reachedEndOfParagrah(paragraphCounter.line, paragraphCounter.currentHashCount)) {
                    createParagraphAndUpdateCounters(paragraphsBuilder, sb, paragraphCounter);
                } else {
                    sb.append(paragraphCounter.line).append(System.lineSeparator());
                }
            }
            createParagraphLine(paragraphsBuilder, paragraphCounter.currentTag, sb);
        }
        return paragraphsBuilder.build();
    }

    private static void createParagraphAndUpdateCounters(Paragraphs.ParagraphsBuilder paragraphsBuilder, StringBuilder sb, ParagraphCounter paragraphCounter) {
        createParagraphLine(paragraphsBuilder, paragraphCounter.currentTag, sb);
        paragraphCounter.currentTag = paragraphCounter.line;
        paragraphCounter.currentHashCount = counHashTags(paragraphCounter.line);
    }

    private static boolean reachedEndOfParagrah(String line, int currentHashCount) {
        return line.startsWith("#") && (currentHashCount >= counHashTags(line) || currentHashCount == 0);
    }

    private static void createParagraphLine(Paragraphs.ParagraphsBuilder paragraphsBuilder, String currentTag, StringBuilder sb) {
        if (Objects.nonNull(currentTag)) {
            paragraphsBuilder.withTagParagraph(currentTag, sb.toString().stripTrailing());
        }
        sb.delete(0, sb.length());
    }

    private static class ParagraphCounter {
        String line = null;
        String currentTag = null;
        int currentHashCount = 0;
    }

}
