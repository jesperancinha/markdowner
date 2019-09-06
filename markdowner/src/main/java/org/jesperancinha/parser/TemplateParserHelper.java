package org.jesperancinha.parser;

import org.jesperancinha.parser.model.Paragraphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TemplateParserHelper {

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
            String line;
            String currentTag = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    createParagraphLine(paragraphsBuilder, currentTag, sb);
                    sb.delete(0, sb.length());
                    currentTag = line;
                } else {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
            }
            createParagraphLine(paragraphsBuilder, currentTag, sb);
        }
        return paragraphsBuilder.build();
    }

    private static void createParagraphLine(Paragraphs.ParagraphsBuilder paragraphsBuilder, String currentTag, StringBuilder sb) {
        if (Objects.nonNull(currentTag)) {
            paragraphsBuilder.withTagParagraph(currentTag, sb.toString().stripTrailing());
        }
    }

}
