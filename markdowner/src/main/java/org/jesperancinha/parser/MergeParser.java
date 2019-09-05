package org.jesperancinha.parser;

import org.jesperancinha.parser.model.Paragraph;
import org.jesperancinha.parser.model.Paragraphs;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.lineSeparator;

public class MergeParser {

    /**
     * Receives a complete markdown text and a {@link Paragraphs} instance and adds all paragraphs in the stipulated order to the end of the text
     *
     * @param readmeMd A complete String representation of a markdown text
     * @param footer   A {@link Paragraphs} instance which will add all paragraphs to the end of the markdown text
     * @return The complete String merge between a {@link String} text and a {@link Paragraphs} instances
     */
    public static String mergeDocumentWithFooterTemplate(String readmeMd, Paragraphs footer) {
        List<String> paragraphsBuildList = footer.getTags()
                .stream()
                .map(footer::getParagraphByTag)
                .map(Paragraph::toString)
                .collect(Collectors.toList());
        return readmeMd.concat(lineSeparator()).concat(lineSeparator()).concat(String.join(lineSeparator(), paragraphsBuildList));
    }
}
