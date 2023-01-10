package org.jesperancinha.parser.markdowner.filter

import java.nio.file.Path
import java.util.*

/**
 * Following a Chain of Responsibility Design pattern, this chain builds an chained object, which checks for the existence (as a example) of the following project types:
 *
 *
 * MAVEN, NPM, GRADLE, SBT
 *
 *
 * You can configure your own hierarchy with your own types and filters
 *
 *
 * And returns the first find. The algorithm is shortened by levels. This means that in one specific folder, it will prioritize the finding, being Maven the first type and lastly the Simple Build Tool
 * The first find is return in a [PackageInfo] object. It contains the Readme type found and the title of the the parsed object.
 * Each chain is created by specifying a [FileFilterChain] next chain object and a [ProjectFilter] filter.
 *
 *
 * See [FileFilterChain.findHighest] for more info.
 */
class FileFilterChain(
    private val nextFileFilterChain: FileFilterChain?,
    private val projectFilter: ProjectFilter<Path>
) {
    /**
     * Finds the highest element in the hierarchy found in the specified folder.
     * The deepness of the search will be limited by an element of [PackageInfo] given as an argument
     *
     * @param packageInfo Limiting search depth [PackageInfo]
     * @param path        Folder [Path] to classify the new to formed readme file
     * @return The package info found [PackageInfo]. Null if unknown.
     */
    fun findHighest(packageInfo: PackageInfo?, path: Path): PackageInfo? {
        if (Objects.nonNull(packageInfo) && packageInfo?.fileFilterChain === this) {
            return packageInfo
        }
        if (projectFilter.test(path)) {
            return PackageInfo(
                projectName = projectFilter.lastProjectName(),
                fileFilterChain = this
            )
        }
        return if (Objects.nonNull(nextFileFilterChain)) {
            nextFileFilterChain?.findHighest(packageInfo, path)
        } else packageInfo
    }

    companion object {
        /**
         * Creates a default chain to discover the project type and thus generate the correct project title for the newly formed Readme.md file
         *
         * @return A filter chain [FileFilterChain]
         */
        fun createDefaultChain(): FileFilterChain = FileFilterChain(
            projectFilter = MavenFilter(),
            nextFileFilterChain = createNPMChain()
        )


        private fun createNPMChain(): FileFilterChain = FileFilterChain(
            projectFilter = NPMFilter(),
            nextFileFilterChain = createGradleChain()
        )


        private fun createGradleChain(): FileFilterChain = FileFilterChain(
            projectFilter = GradleFilter(),
            nextFileFilterChain = createSBTChain()
        )

        private fun createSBTChain(): FileFilterChain = FileFilterChain(
            projectFilter = SBTFilter(),
            nextFileFilterChain = null
        )

    }
}