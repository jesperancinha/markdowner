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

@Service
public class ReadmeServiceImpl implements ReadmeService {

    @Override
    public String readDataSprippedOfTags(final InputStream templateInputStream, String... tags) throws IOException {
        final StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(templateInputStream))) {

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
        return sb.toString();
    }

    private int calculateCurrentMinHashTags(String line, int currentMinHashTags, List<String> allTags) {
        if (allTags.contains(sanitizeTag(line))) {
            int hashCount = counHashTags(line);
            if (hashCount <= currentMinHashTags) {
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
