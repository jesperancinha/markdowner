package org.jesperancinha.parser.markdowner.helper

import org.jesperancinha.parser.markdowner.model.Paragraphs

object MergeParserHelper {
    /**
     * Receives a complete Markdown text and a [Paragraphs] instance and adds all paragraphs in the stipulated order to the end of the text
     *
     * @param readmeMd A complete String representation of a Markdown text
     * @param footer   A [Paragraphs] instance which will add all paragraphs to the end of the Markdown text
     * @return The complete String merge between a [String] text and a [Paragraphs] instances
     */
    fun mergeDocumentWithFooterTemplate(readmeMd: String, footer: Paragraphs) =
        readmeMd + System.lineSeparator() + System.lineSeparator() + java.lang.String.join(
            System.lineSeparator(),
            footer.tags
                .map { tag: String -> footer.getParagraphByTag(tag).toString() }
        )
}