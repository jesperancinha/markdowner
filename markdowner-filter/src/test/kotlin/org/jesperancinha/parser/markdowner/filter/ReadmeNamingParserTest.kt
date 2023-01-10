package org.jesperancinha.parser.markdowner.filter

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.apache.commons.io.IOUtils
import org.junit.jupiter.api.Test
import java.io.IOException
import java.net.URISyntaxException
import java.nio.charset.Charset.*
import java.nio.file.Path

class ReadmeNamingParserTest {
    private val templatePath = Path.of("/.")

    @Test
    @Throws(IOException::class)
    fun testBuildReadmeSamePath() {
        val path = Path.of("/.")
        val inputStream = ReadmeNamingParser(
            templateLocation = templatePath,
            fileFilterChain = FileFilterChain.createDefaultChain()
        )
            .buildReadmeStream(path)
        inputStream.shouldBeNull()
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamNothing() {
        val noProject = javaClass.getResource("/directory2NoReadme/noProject2")
        noProject.shouldNotBeNull()
        val path = Path.of(noProject.toURI())
        val inputStream = ReadmeNamingParser(
            templateLocation = templatePath,
            fileFilterChain = FileFilterChain.createDefaultChain()
        ).buildReadmeStream(path)
        inputStream.shouldBeNull()
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamMixMavenAndNPMFlagOn() {
        val projectMavenAndNpm = javaClass.getResource("/directory2NoReadme/project3MavenAndNPM")
        projectMavenAndNpm.shouldNotBeNull()
        val path = Path.of(projectMavenAndNpm.toURI())
        val inputStream = ReadmeNamingParser(
            isNoEmpty = true,
            templateLocation = templatePath,
            fileFilterChain = FileFilterChain.createDefaultChain()
        ).buildReadmeStream(path)
        inputStream.shouldBeNull()
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamMixMavenAndNPM() {
        val projectMavenAndNpm = javaClass.getResource("/directory2NoReadme/project3MavenAndNPM")
        projectMavenAndNpm.shouldNotBeNull()
        val path = Path.of(projectMavenAndNpm.toURI())
        val inputStream = ReadmeNamingParser(
            templateLocation = templatePath,
            fileFilterChain = FileFilterChain.createDefaultChain()
        ).buildReadmeStream(path)
        inputStream.shouldNotBeNull()
        val result = IOUtils.toString(inputStream, defaultCharset())
        result shouldBe "# This is a test project"
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamMavenName() {
        val projectMaven = javaClass.getResource("/directory2NoReadme/project1Maven")
        projectMaven.shouldNotBeNull()
        val path = Path.of(projectMaven.toURI())
        val inputStream = ReadmeNamingParser(
            templateLocation = templatePath,
            fileFilterChain = FileFilterChain.createDefaultChain()
        ).buildReadmeStream(path)
        inputStream.shouldNotBeNull()
        val result = IOUtils.toString(inputStream, defaultCharset())
        result shouldBe "# This is a test project"
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamMavenNoName() {
        val projectMavenNoName = javaClass.getResource("/directory2NoReadme/project1MavenNoName")
        val path = Path.of(projectMavenNoName.toURI())
        val inputStream = ReadmeNamingParser(
            templateLocation = templatePath,
            fileFilterChain = FileFilterChain.createDefaultChain()
        ).buildReadmeStream(path)
        inputStream.shouldNotBeNull()
        val result = IOUtils.toString(inputStream, defaultCharset())
        result shouldBe "# ProjectMavenArtifact"
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamNPM() {
        val projectNpm = javaClass.getResource("/directory2NoReadme/project2NPM")
        projectNpm.shouldNotBeNull()
        val path = Path.of(projectNpm.toURI())
        val inputStream = ReadmeNamingParser(
            templateLocation = templatePath,
            fileFilterChain = FileFilterChain.Companion.createDefaultChain()
        ).buildReadmeStream(path)
        inputStream.shouldNotBeNull()
        val result = IOUtils.toString(inputStream, defaultCharset())
        result shouldBe "# npm-project"
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamGradle() {
        val projectGradle = javaClass.getResource("/directory2NoReadme/project4Gradle")
        projectGradle.shouldNotBeNull()
        val path = Path.of(projectGradle.toURI())
        val inputStream = ReadmeNamingParser(
            templateLocation = templatePath,
            fileFilterChain = FileFilterChain.createDefaultChain()
        ).buildReadmeStream(path)
        inputStream.shouldNotBeNull()
        val result = IOUtils.toString(inputStream, defaultCharset())
        result shouldBe "# project4Gradle"
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamSBT() {
        val projectSbt = javaClass.getResource("/directory2NoReadme/project5Sbt")
        projectSbt.shouldNotBeNull()
        val path = Path.of(projectSbt.toURI())
        val inputStream = ReadmeNamingParser(
            templateLocation = templatePath,
            fileFilterChain = FileFilterChain.createDefaultChain()
        ).buildReadmeStream(path)
        inputStream.shouldNotBeNull()
        val result = IOUtils.toString(inputStream, defaultCharset())
        result shouldBe "# sbt-project"
    }
}