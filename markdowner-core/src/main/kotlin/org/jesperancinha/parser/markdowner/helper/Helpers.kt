package org.jesperancinha.parser.markdowner.helper

import org.jesperancinha.parser.markdowner.model.Paragraphs
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.CharBuffer
import java.util.*

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

object ReadmeParserHelper {
    /**
     * Reads an input marked down string and returns the exact same text without the specified cardinal tags and their content.
     *
     *
     * This means any content of # tags
     *
     * @param readmeInputStream An input stream of a markdown text
     * @param tags              All tags of the paragraphs to be removed
     * @return The filtered String
     * @throws IOException Any IO Exception thrown
     */
    @Throws(IOException::class)
    fun readDataSprippedOfTags(readmeInputStream: InputStream, vararg tags: String?): String {
        val sb = StringBuilder()
        BufferedReader(InputStreamReader(readmeInputStream)).use { br ->
            val allTags = listOf(*tags)
            var line: String?
            var currentMinHashTags = 0
            while (br.readLine().also { line = it } != null) {
                if (line?.startsWith("#") == true) {
                    line?.let{ currentMinHashTags = calculateCurrentMinHashTags(it, currentMinHashTags, allTags)}
                }
                if (currentMinHashTags == 0 && (line?.startsWith("#") != true || !allTags.contains(TagHelper.sanitizeTag(line)))) {
                    sb.append(line)
                    sb.append(System.lineSeparator())
                }
            }
        }
        return sb.toString().trim()
    }

    private fun calculateCurrentMinHashTags(line: String, currentMinHashTags: Int, allTags: List<String?>): Int {
        var newMinHashTagsCount = currentMinHashTags
        newMinHashTagsCount = if (allTags.contains(TagHelper.sanitizeTag(line))) {
            getNewMinHashTagsCountAfterMatch(line, newMinHashTagsCount)
        } else {
            getNewMinHashTagsCountAfterNoMatch(line, newMinHashTagsCount)
        }
        return newMinHashTagsCount
    }

    private fun getNewMinHashTagsCountAfterNoMatch(line: String, newMinHashTagsCount: Int) =
        TagHelper.counHashTags(line)
            .let { hashCount ->
                if (hashCount <= newMinHashTagsCount) 0 else newMinHashTagsCount
            }

    private fun getNewMinHashTagsCountAfterMatch(line: String, currentHashTagsCount: Int) =
        TagHelper.counHashTags(line)
            .let { hashCount ->
                if (hashCount < currentHashTagsCount) 0 else hashCount
            }


}

object TagHelper {
    /**
     * Strips the first # characters from a string and removes eventually leading spaces
     *
     * @param tag Tag [String] to be sanitized
     * @return The sanitized tag [String]
     */
    fun sanitizeTag(tag: String?): String {
        val preSanitizedTag = removeStartingHashTags(tag)
        var charBuff = CharBuffer.allocate(preSanitizedTag.length)
        var finalSize = 0
        for (i in 0 until preSanitizedTag.length) {
            val charCandidate = preSanitizedTag[i]
            if (charCandidate.code < 256) {
                charBuff = charBuff.append(charCandidate)
                finalSize++
            }
        }
        return String(Arrays.copyOfRange(charBuff.array(), 0, finalSize)).trim { it <= ' ' }
    }

    private fun removeStartingHashTags(tag: String?): String {
        var i = 0
        for (c in tag!!.toCharArray()) {
            if (c != '#') {
                return tag.substring(i).trim { it <= ' ' }
            }
            i++
        }
        return ""
    }

    /**
     * Counts the number of leading hash characters in a [String]
     *
     * @param tag Tag [String] to analyse
     * @return Number of hash (#) characters
     */
    fun counHashTags(tag: String?): Int {
        var count = 0
        for (c in tag!!.toCharArray()) {
            if (c == '#') {
                count++
            } else {
                return count
            }
        }
        return count
    }
}

object TemplateParserHelper {
    /**
     * Receives an input Markdown text stream nd parses its content to a Paragraphs object see [Paragraphs]
     *
     * @param templateInputStream An input Markdown text stream
     * @return A [Paragraphs] parsed object
     * @throws IOException Any kind of IO Exception
     */
    @Throws(IOException::class)
    fun findAllParagraphs(templateInputStream: InputStream): Paragraphs {
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