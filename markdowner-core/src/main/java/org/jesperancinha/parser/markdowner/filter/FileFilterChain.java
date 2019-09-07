package org.jesperancinha.parser.markdowner.filter;

import static org.jesperancinha.parser.markdowner.model.ProjectType.GRADLE;
import static org.jesperancinha.parser.markdowner.model.ProjectType.MAVEN;
import static org.jesperancinha.parser.markdowner.model.ProjectType.NPM;
import static org.jesperancinha.parser.markdowner.model.ProjectType.SBT;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.parser.markdowner.model.PackageInfo;
import org.jesperancinha.parser.markdowner.model.ProjectType;

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
 * Each chain is created by specifying a {@link FileFilterChain} next chain object, a {@link ProjectFilter} filter and a {@link ProjectType} to define its type
 * <p>
 * See {@link FileFilterChain#findHighest(PackageInfo, Path)} for more info.
 */
@Slf4j
@Builder
public class FileFilterChain {

    private final FileFilterChain nextFileFilterChain;

    private final ProjectFilter<Path> projectFilter;

    private final ProjectType projectType;

    /**
     * Finds the highest element in the hierarchy ({@link ProjectType} found in the specified folder.
     * The deepness of the search will be limited by an element of {@link PackageInfo} given as an argument
     *
     * @param packageInfo Limiting search depth {@link PackageInfo}
     * @param path        Folder {@link Path} to classify the new to formed readme file
     * @return The package info found {@link PackageInfo}. Null if unknown.
     */
    public PackageInfo findHighest(PackageInfo packageInfo, Path path) {

        if (Objects.nonNull(packageInfo) && packageInfo.getProjectType() == projectType) {
            return packageInfo;
        }

        if (projectFilter.test(path)) {
            return PackageInfo.builder().projectName(projectFilter.lastProjectName()).projectType(projectType).build();
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
        return FileFilterChain.builder().projectType(MAVEN).projectFilter(new MavenFilter())
            .nextFileFilterChain(
                createNPMChain()
            ).build();
    }

    private static FileFilterChain createNPMChain() {
        return FileFilterChain.builder().projectType(NPM).projectFilter(new NPMFilter())
            .nextFileFilterChain(
                createGradleChain()
            ).build();
    }

    private static FileFilterChain createGradleChain() {
        return FileFilterChain.builder().projectType(GRADLE).projectFilter(new GradleFilter())
            .nextFileFilterChain(
                createSBTChain()
            ).build();
    }

    private static FileFilterChain createSBTChain() {
        return FileFilterChain.builder().projectType(SBT).projectFilter(new SBTFilter())
            .nextFileFilterChain(null)
            .build();
    }
}
