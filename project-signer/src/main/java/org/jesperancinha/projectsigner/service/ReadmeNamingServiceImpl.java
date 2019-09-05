package org.jesperancinha.projectsigner.service;

import org.jesperancinha.parser.ReadmeNamingParser;
import org.jesperancinha.parser.filter.FileFilterChain;
import org.jesperancinha.projectsigner.inteface.OptionsService;
import org.jesperancinha.projectsigner.inteface.ReadmeNamingService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * A service responsible for assuring the existence of Readme files in all recognizable projects: Maven, Node package manager, Gradle and Simple build tool.
 */
@Service
public class ReadmeNamingServiceImpl implements ReadmeNamingService {

    private OptionsService optionsService;
    private FileFilterChain fileFilterChain;

    public ReadmeNamingServiceImpl(final FileFilterChain fileFilterChain,
                                   final OptionsService optionsService) {
        this.fileFilterChain = fileFilterChain;
        this.optionsService = optionsService;
    }

    /**
     * Builds a stream of a Readme marked down texts taking a {@link Path} as a reference.
     * If a Readme.md file already exists, it will return a Stream with it's content.
     * If a Readme.md File does not exist then it will create a new stream with the project title as the first header.
     * <p>
     * Special algorithms will determine the name of the project automatically
     *
     * @param path The path where to search and determine the right text for the markdown Readme file
     * @return The calculated Input text stream
     * @throws IOException Any IO Exception that may occur
     */
    @Override
    public InputStream buildReadmeStream(Path path) throws IOException {
        final ReadmeNamingParser.ReadmeNamingParserBuilder commonNamingParser = ReadmeNamingParser.builder()
                .templateLocation(optionsService.getTemplateLocation())
                .isNoEmpty(optionsService.isNoEmpty());
        return commonNamingParser
                .fileFilterChain(fileFilterChain)
                .build()
                .buildReadmeStream(path);
    }

}