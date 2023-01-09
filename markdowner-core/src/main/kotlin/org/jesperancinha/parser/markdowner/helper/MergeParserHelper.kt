package org.jesperancinha.parser.markdowner.helper

import org.jesperancinha.parser.markdowner.model.Paragraph
import org.jesperancinha.parser.markdowner.model.Paragraphs
import java.util.stream.Collectors

object MergeParserHelper {
    /**
     * Receives a complete markdown text and a [Paragraphs] instance and adds all paragraphs in the stipulated order to the end of the text
     *
     * @param readmeMd A complete String representation of a markdown text
     * @param footer   A [Paragraphs] instance which will add all paragraphs to the end of the markdown text
     * @return The complete String merge between a [String] text and a [Paragraphs] instances
     */
    fun mergeDocumentWithFooterTemplate(readmeMd: String, footer: Paragraphs): String {
        val paragraphsBuildList = footer.tags
            .stream()
            .map { tag: String -> footer.getParagraphByTag(tag) }
            .map { obj: Paragraph -> obj.toString() }
            .collect(Collectors.toList())
        return readmeMd + System.lineSeparator() + System.lineSeparator() + java.lang.String.join(
            System.lineSeparator(),
            paragraphsBuildList
        )
    }
}