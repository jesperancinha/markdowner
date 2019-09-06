package org.jesperancinha.parser.markdowner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.jesperancinha.parser.markdowner.filter.FileFilterChain;
import org.jesperancinha.parser.markdowner.model.PackageInfo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Builder
@AllArgsConstructor
public class ReadmeNamingParser {

    private final FileFilterChain fileFilterChain;

    private final Path templateLocation;

    private final boolean isNoEmpty;

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
    public InputStream buildReadmeStream(Path path) throws IOException {
        if (path.toAbsolutePath().toString()
                .equals(templateLocation.toAbsolutePath().toString())) {
            return null;
        }
        final Path readmePath = path.resolve("Readme.md");
        final File readmeFile = readmePath.toFile();
        if (readmeFile.exists()) {
            return new FileInputStream(readmeFile);
        }
        final PackageInfo packageInfo = findProjectType(path);
        if (Objects.isNull(packageInfo)) {
            return null;
        }

        if (isNoEmpty) {
            return null;
        }
        return new ByteArrayInputStream("# ".concat(packageInfo.getProjectName()).getBytes());
    }

    private PackageInfo findProjectType(Path path) throws IOException {
        PackageInfo highestLevel = null;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path newPath : stream) {
                if (!Files.isDirectory(newPath)) {
                    highestLevel = fileFilterChain.findHighest(highestLevel, newPath);
                }
            }
        }
        return highestLevel;
    }
}
