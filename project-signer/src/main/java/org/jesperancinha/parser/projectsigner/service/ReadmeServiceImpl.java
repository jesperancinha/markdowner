package org.jesperancinha.parser.projectsigner.service;

import org.jesperancinha.parser.markdowner.ReadmeParserHelper;
import org.jesperancinha.parser.projectsigner.inteface.ReadmeService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

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
        return ReadmeParserHelper.readDataSprippedOfTags(readmeInputStream, tags);
    }
}
