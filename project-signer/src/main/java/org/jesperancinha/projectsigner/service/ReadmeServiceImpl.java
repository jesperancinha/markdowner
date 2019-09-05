package org.jesperancinha.projectsigner.service;

import org.jesperancinha.projectsigner.inteface.ReadmeService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.lineSeparator;
import static org.jesperancinha.projectsigner.utils.StandardUtils.counHashTags;
import static org.jesperancinha.projectsigner.utils.StandardUtils.sanitizeTag;

/**
 * A Readme service to read and manipulate markdown files
 */
@Service
public class ReadmeServiceImpl implements ReadmeService {

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
    @Override
    public String readDataSprippedOfTags(final InputStream readmeInputStream, String... tags) throws IOException {
        final StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(readmeInputStream))) {

            final List<String> allTags = Arrays.asList(tags);
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

    private int calculateCurrentMinHashTags(String line, int currentMinHashTags, List<String> allTags) {
        if (allTags.contains(sanitizeTag(line))) {
            int hashCount = counHashTags(line);
            if (hashCount < currentMinHashTags) {
                currentMinHashTags = 0;
            } else {
                currentMinHashTags = hashCount;
            }
        } else {
            int hashCount = counHashTags(line);
            if (hashCount <= currentMinHashTags) {
                currentMinHashTags = 0;
            }
        }
        return currentMinHashTags;
    }
}
