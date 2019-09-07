package org.jesperancinha.parser.markdowner.utils;

public class StandardUtils {

    /**
     * Strips the first # characters from a string and removes eventually leading spaces
     *
     * @param tag Tag {@link String} to be sanitized
     * @return The sanitized tag {@link String}
     */
    public static String sanitizeTag(final String tag) {
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
