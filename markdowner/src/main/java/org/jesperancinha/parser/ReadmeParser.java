package org.jesperancinha.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static java.lang.System.lineSeparator;
import static java.util.Arrays.asList;
import static org.jesperancinha.parser.utils.StandardUtils.counHashTags;
import static org.jesperancinha.parser.utils.StandardUtils.sanitizeTag;

public class ReadmeParser {

    /**
     * Reads an input marked down string and returns the exact same text without the specified cardinal tags and their content.
     * <p>
     * This means any content of # tags
     *
     * @param readmeInputStream An input stream of a markdown text
     * @param tags              All tags of the paragraphs to be removed
     * @return The filtered String
     * @throws IOException Any IO Exception thrown
     */
    public static String readDataSprippedOfTags(final InputStream readmeInputStream, String... tags) throws IOException {
        final StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(readmeInputStream))) {

            final List<String> allTags = asList(tags);
            String line;
            int currentMinHashTags = 0;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    currentMinHashTags = calculateCurrentMinHashTags(line, currentMinHashTags, allTags);
                }
                if (currentMinHashTags == 0 && (!line.startsWith("#") || !allTags.contains(sanitizeTag(line)))) {
                    sb.append(line);
                    sb.append(lineSeparator());
                }
            }
        }
        return sb.toString().stripTrailing();
    }

    private static int calculateCurrentMinHashTags(String line, int currentMinHashTags, List<String> allTags) {
        int newMinHashTagsCount = currentMinHashTags;
        if (allTags.contains(sanitizeTag(line))) {
            int hashCount = counHashTags(line);
            if (hashCount < newMinHashTagsCount) {
                newMinHashTagsCount = 0;
            } else {
                newMinHashTagsCount = hashCount;
            }
        } else {
            int hashCount = counHashTags(line);
            if (hashCount <= newMinHashTagsCount) {
                newMinHashTagsCount = 0;
            }
        }
        return newMinHashTagsCount;
    }
}
