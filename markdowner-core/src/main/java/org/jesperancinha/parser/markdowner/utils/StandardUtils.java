package org.jesperancinha.parser.markdowner.utils;

public class StandardUtils {

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

    public static int counHashTags(final String line) {
        int count = 0;
        for (char c : line.toCharArray()) {
            if (c == '#') {
                count++;
            } else {
                return count;
            }
        }
        return count;
    }


}
