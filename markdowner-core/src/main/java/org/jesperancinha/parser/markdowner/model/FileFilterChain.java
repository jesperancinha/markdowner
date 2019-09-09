package org.jesperancinha.parser.markdowner.model;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.parser.markdowner.filter.GradleFilter;
import org.jesperancinha.parser.markdowner.filter.MavenFilter;
import org.jesperancinha.parser.markdowner.filter.NPMFilter;
import org.jesperancinha.parser.markdowner.filter.ProjectFilter;
import org.jesperancinha.parser.markdowner.filter.SBTFilter;

import java.nio.file.Path;
import java.util.Objects;

/**
 * Following a Chain of Responsibility Design pattern, this chain builds an chained object, which checks for the existence (as a example) of the following project types:
 * <p>
 * MAVEN, NPM, GRADLE, SBT
 * <p>
 * You can configure your own hierarchy with your own types and filters
 * <p>
 * And returns the first find. The algorithm is shortened by levels. This means that in one specific folder, it will prioritize the finding, being Maven the first type and lastly the Simple Build Tool
 * The first find is return in a {@link PackageInfo} object. It contains the Readme type found and the title of the the parsed object.
 * Each chain is created by specifying a {@link FileFilterChain} next chain object and a {@link ProjectFilter} filter.
 * <p>
 * See {@link FileFilterChain#findHighest(PackageInfo, Path)} for more info.
 */
@Slf4j
@Builder
public class FileFilterChain {

    private final FileFilterChain nextFileFilterChain;

    private final ProjectFilter<Path> projectFilter;

    /**
     * Finds the highest element in the hierarchy found in the specified folder.
     * The deepness of the search will be limited by an element of {@link PackageInfo} given as an argument
     *
     * @param packageInfo Limiting search depth {@link PackageInfo}
     * @param path        Folder {@link Path} to classify the new to formed readme file
     * @return The package info found {@link PackageInfo}. Null if unknown.
     */
    public PackageInfo findHighest(PackageInfo packageInfo, Path path) {

        if (Objects.nonNull(packageInfo) && packageInfo.getFileFilterChain() == this) {
            return packageInfo;
        }

        if (projectFilter.test(path)) {
            return PackageInfo.builder().projectName(projectFilter.lastProjectName()).fileFilterChain(this).build();
        }

        if (Objects.nonNull(nextFileFilterChain)) {
            return nextFileFilterChain.findHighest(packageInfo, path);
        }
        return packageInfo;
    }


    /**
     * Creates a default chain to discover the project type and thus generate the correct project title for the newly formed Readme.md file
     *
     * @return A filter chain {@link FileFilterChain}
     */
    public static FileFilterChain createDefaultChain() {
        return FileFilterChain.builder().projectFilter(new MavenFilter())
                .nextFileFilterChain(
                        createNPMChain()
                ).build();
    }

    private static FileFilterChain createNPMChain() {
        return FileFilterChain.builder().projectFilter(new NPMFilter())
                .nextFileFilterChain(
                        createGradleChain()
                ).build();
    }

    private static FileFilterChain createGradleChain() {
        return FileFilterChain.builder().projectFilter(new GradleFilter())
                .nextFileFilterChain(
                        createSBTChain()
                ).build();
    }

    private static FileFilterChain createSBTChain() {
        return FileFilterChain.builder().projectFilter(new SBTFilter())
                .nextFileFilterChain(null)
                .build();
    }
}
