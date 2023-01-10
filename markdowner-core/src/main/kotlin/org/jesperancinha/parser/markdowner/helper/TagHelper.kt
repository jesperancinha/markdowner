package org.jesperancinha.parser.markdowner.helper

import java.nio.CharBuffer
import java.util.*

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