package org.jesperancinha.parser.markdowner.filter

import java.io.*
import java.nio.file.DirectoryStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

class ReadmeNamingParser(
    private val fileFilterChain: FileFilterChain? = null,
    private val templateLocation: Path,
    private val isNoEmpty: Boolean = false
) {
    /**
     * Builds a stream of a Readme marked down texts taking a [Path] as a reference.
     * If a Readme.md file already exists, it will return a Stream with it's content.
     * If a Readme.md File does not exist then it will create a new stream with the project title as the first header.
     *
     *
     * Special algorithms will determine the name of the project automatically
     *
     * @param path The path where to search and determine the right text for the markdown Readme file
     * @return The calculated Input text stream
     * @throws IOException Any IO Exception that may occur
     */
    @Throws(IOException::class)
    fun buildReadmeStream(path: Path): InputStream? {
        if (isItATemplatePath(path)) {
            return null
        }
        val readmeFile = getReadmePath(path)
        if (readmeFile?.exists() == true) {
            return FileInputStream(readmeFile)
        }
        val packageInfo = findPackageInfo(path)
        return if (noPackageInfo(packageInfo) || isNoEmpty) {
            null
        } else ByteArrayInputStream(("# " + packageInfo?.projectName).toByteArray())
    }

    /**
     * If no package has been found
     *
     * @param packageInfo The package info
     * @return If packageInfo is not null
     */
    private fun noPackageInfo(packageInfo: PackageInfo?): Boolean {
        return Objects.isNull(packageInfo)
    }

    /**
     * Gets the Readme.me from the reference directory path
     *
     * @param path The directory path
     * @return The directory path with the added Readme.md file
     */
    private fun getReadmePath(path: Path): File? {
        val readmePath = path.resolve("Readme.md")
        return readmePath.toFile()
    }

    /**
     * If the path is the actual template. We do not want to change the template itself
     *
     * @param path The path to test
     * @return true if the template path matches the given path, otherwise false
     */
    private fun isItATemplatePath(path: Path): Boolean {
        return (path.toAbsolutePath().toString()
                == templateLocation.toAbsolutePath().toString())
    }

    /**
     * Discovers the package info based on all the chain elements created during chain creation.
     * The default configuration of this chain is MAVEN, NPM, GRADLE and SBT.
     *
     * @param path The Path to be tested
     * @return The created package info with the project name and the automated packaging system type [PackageInfo]
     * @throws IOException If an input/output exception has occurred
     */
    @Throws(IOException::class)
    private fun findPackageInfo(path: Path): PackageInfo? {
        var highestLevel: PackageInfo?
        Files.newDirectoryStream(path).use { stream -> highestLevel = iterateAllPathsInDirectoryStream(stream) }
        return highestLevel
    }

    /**
     * Iterates through all files in the given Directory stream
     *
     * @param stream Directory stream [DirectoryStream]
     * @return The created package info with the project name and the automated packaging system type [PackageInfo]
     */
    private fun iterateAllPathsInDirectoryStream(stream: DirectoryStream<Path>): PackageInfo? {
        var highestLevel: PackageInfo? = null
        for (newPath in stream) {
            if (!Files.isDirectory(newPath)) {
                highestLevel = fileFilterChain?.findHighest(highestLevel, newPath)
            }
        }
        return highestLevel
    }
}