package org.jesperancinha.parser.markdowner.parser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.jesperancinha.parser.markdowner.model.FileFilterChain;
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
        if (isItATemplatePath(path)) {
            return null;
        }
        final File readmeFile = getReadmePath(path);
        if (readmeFile.exists()) {
            return new FileInputStream(readmeFile);
        }
        final PackageInfo packageInfo = findPackageInfo(path);
        if (noPackageInfo(packageInfo) || isNoEmpty) {
            return null;
        }
        return new ByteArrayInputStream("# ".concat(packageInfo.getProjectName()).getBytes());
    }

    /**
     * If no package has been found
     *
     * @param packageInfo The package info
     * @return If packageInfo is not null
     */
    private boolean noPackageInfo(PackageInfo packageInfo) {
        return Objects.isNull(packageInfo);
    }

    /**
     * Gets the Readme.me from the reference directory path
     *
     * @param path The directory path
     * @return The directory path with the added Readme.md file
     */
    private File getReadmePath(Path path) {
        final Path readmePath = path.resolve("Readme.md");
        return readmePath.toFile();
    }

    /**
     * If the path is the actual template. We do not want to change the template itself
     *
     * @param path The path to test
     * @return true if the template path matches the given path, otherwise false
     */
    private boolean isItATemplatePath(Path path) {
        return path.toAbsolutePath().toString()
                .equals(templateLocation.toAbsolutePath().toString());
    }

    /**
     * Discovers the package info based on all the chain elements created during chain creation.
     * The default configuration of this chain is MAVEN, NPM, GRADLE and SBT.
     *
     * @param path The Path to be tested
     * @return The created package info with the project name and the automated packaging system type {@link PackageInfo}
     * @throws IOException If an input/output exception has occurred
     */
    private PackageInfo findPackageInfo(Path path) throws IOException {
        PackageInfo highestLevel;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            highestLevel = iterateAllPathsInDirectoryStream(stream);
        }
        return highestLevel;
    }

    /**
     * Iterates through all files in the given Directory stream
     *
     * @param stream Directory stream {@link DirectoryStream}
     * @return The created package info with the project name and the automated packaging system type {@link PackageInfo}
     */
    private PackageInfo iterateAllPathsInDirectoryStream(DirectoryStream<Path> stream) {
        PackageInfo highestLevel = null;
        for (Path newPath : stream) {
            if (!Files.isDirectory(newPath)) {
                highestLevel = fileFilterChain.findHighest(highestLevel, newPath);
            }
        }
        return highestLevel;
    }
}
