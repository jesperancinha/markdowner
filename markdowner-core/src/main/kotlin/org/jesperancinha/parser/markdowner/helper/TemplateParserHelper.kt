package org.jesperancinha.parser.markdowner.helper

import org.jesperancinha.parser.markdowner.model.Paragraphs
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

object TemplateParserHelper {
    /**
     * Receives an input Markdown text stream nd parses its content to a Paragraphs object see [Paragraphs]
     *
     * @param templateInputStream An input Markdown text stream
     * @return A [Paragraphs] parsed object
     * @throws IOException Any kind of IO Exception
     */
    @Throws(IOException::class)
    fun findAllParagraphs(templateInputStream: InputStream): Paragraphs? {
        val paragraphs = Paragraphs()
        BufferedReader(InputStreamReader(templateInputStream)).use { br ->
            val sb = StringBuilder()
            val paragraphCounter = ParagraphCounter()
            while (br.readLine().also { paragraphCounter.line = it } != null) {
                if (reachedEndOfParagrah(paragraphCounter.line, paragraphCounter.currentHashCount)) {
                    createParagraphAndUpdateCounters(paragraphs, sb, paragraphCounter)
                } else {
                    sb.append(paragraphCounter.line).append(System.lineSeparator())
                }
            }
            createParagraphLine(paragraphs, paragraphCounter.currentTag, sb)
        }
        return paragraphs
    }

    private fun createParagraphAndUpdateCounters(
        paragraphsBuilder: Paragraphs,
        sb: StringBuilder,
        paragraphCounter: ParagraphCounter
    ) {
        createParagraphLine(paragraphsBuilder, paragraphCounter.currentTag, sb)
        paragraphCounter.currentTag = paragraphCounter.line
        paragraphCounter.currentHashCount = TagHelper.counHashTags(paragraphCounter.line)
    }

    private fun reachedEndOfParagrah(line: String?, currentHashCount: Int): Boolean {
        return line!!.startsWith("#") && (currentHashCount >= TagHelper.counHashTags(line) || currentHashCount == 0)
    }

    private fun createParagraphLine(paragraphsBuilder: Paragraphs, currentTag: String?, sb: StringBuilder) {
        if (Objects.nonNull(currentTag)) {
            paragraphsBuilder.withTagParagraph(currentTag, sb.toString().stripTrailing())
        }
        sb.delete(0, sb.length)
    }

    private class ParagraphCounter {
        var line: String? = null
        var currentTag: String? = null
        var currentHashCount = 0
    }
}