package org.jesperancinha.parser.markdowner.helper

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

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
    fun readDataSprippedOfTags(readmeInputStream: InputStream?, vararg tags: String?): String {
        val sb = StringBuilder()
        BufferedReader(InputStreamReader(readmeInputStream)).use { br ->
            val allTags = Arrays.asList(*tags)
            var line: String
            var currentMinHashTags = 0
            while (br.readLine().also { line = it } != null) {
                if (line.startsWith("#")) {
                    currentMinHashTags = calculateCurrentMinHashTags(line, currentMinHashTags, allTags)
                }
                if (currentMinHashTags == 0 && (!line.startsWith("#") || !allTags.contains(TagHelper.sanitizeTag(line)))) {
                    sb.append(line)
                    sb.append(System.lineSeparator())
                }
            }
        }
        return sb.toString().stripTrailing()
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

    private fun getNewMinHashTagsCountAfterNoMatch(line: String, newMinHashTagsCount: Int): Int {
        var newMinHashTagsCount = newMinHashTagsCount
        val hashCount = TagHelper.counHashTags(line)
        if (hashCount <= newMinHashTagsCount) {
            newMinHashTagsCount = 0
        }
        return newMinHashTagsCount
    }

    private fun getNewMinHashTagsCountAfterMatch(line: String, currentHashTagsCount: Int): Int {
        var currentHashTagsCount = currentHashTagsCount
        val hashCount = TagHelper.counHashTags(line)
        currentHashTagsCount = if (hashCount < currentHashTagsCount) {
            0
        } else {
            hashCount
        }
        return currentHashTagsCount
    }
}