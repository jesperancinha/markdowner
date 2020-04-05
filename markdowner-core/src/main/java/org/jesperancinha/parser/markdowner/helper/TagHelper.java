package org.jesperancinha.parser.markdowner.helper;

import java.nio.CharBuffer;
import java.util.Arrays;

public class TagHelper {

    /**
     * Strips the first # characters from a string and removes eventually leading spaces
     *
     * @param tag Tag {@link String} to be sanitized
     * @return The sanitized tag {@link String}
     */
    public static String sanitizeTag(final String tag) {
        final String preSanitizedTag = removeStartingHashTags(tag);
        CharBuffer charBuff = CharBuffer.allocate(preSanitizedTag.length());
        int finalSize = 0;
        for (int i = 0; i < preSanitizedTag.length(); i++) {
            char charCandidate = preSanitizedTag.charAt(i);
            if (charCandidate < 256) {
                charBuff = charBuff.append(charCandidate);
                finalSize++;
            }
        }
        return new String(Arrays.copyOfRange(charBuff.array(), 0, finalSize)).trim();
    }

    private static String removeStartingHashTags(String tag) {
        int i = 0;
        for (char c : tag.toCharArray()) {
            if (c != '#') {
                return tag.substring(i).trim();
            }
            i++;
        }
        return "";
    }

    /**
     * Counts the number of leading hash characters in a {@link String}
     *
     * @param tag Tag {@link String} to analyse
     * @return Number of hash (#) characters
     */
    public static int counHashTags(final String tag) {
        int count = 0;
        for (char c : tag.toCharArray()) {
            if (c == '#') {
                count++;
            } else {
                return count;
            }
        }
        return count;
    }


}
