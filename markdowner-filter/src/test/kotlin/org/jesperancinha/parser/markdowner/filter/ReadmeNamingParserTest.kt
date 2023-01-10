package org.jesperancinha.parser.markdowner.filter

import org.apache.commons.io.IOUtils
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.io.IOException
import java.net.URISyntaxException
import java.nio.charset.Charset
import java.nio.file.Path

class ReadmeNamingParserTest {
    private val templatePath = Path.of("/.")
    @Test
    @Throws(IOException::class)
    fun testBuildReadmeSamePath() {
        val path = Path.of("/.")
        val inputStream = ReadmeNamingParser(
            templateLocation = templatePath,
            fileFilterChain = FileFilterChain.createDefaultChain())
            .buildReadmeStream(path)
        Assertions.assertThat(inputStream).isNull()
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamNothing() {
        val path = Path.of(javaClass.getResource("/directory2NoReadme/noProject2").toURI())
        val inputStream = ReadmeNamingParser(templateLocation(templatePath)
            .fileFilterChain(FileFilterChain.Companion.createDefaultChain()).build().buildReadmeStream(path)
        Assertions.assertThat(inputStream).isNull()
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamMixMavenAndNPMFlagOn() {
        val path = Path.of(javaClass.getResource("/directory2NoReadme/project3MavenAndNPM").toURI())
        val inputStream = ReadmeNamingParser.builder().isNoEmpty(true).templateLocation(templatePath)
            .fileFilterChain(FileFilterChain.Companion.createDefaultChain()).build().buildReadmeStream(path)
        Assertions.assertThat(inputStream).isNull()
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamMixMavenAndNPM() {
        val path = Path.of(javaClass.getResource("/directory2NoReadme/project3MavenAndNPM").toURI())
        val inputStream = ReadmeNamingParser.builder().templateLocation(templatePath)
            .fileFilterChain(FileFilterChain.Companion.createDefaultChain()).build().buildReadmeStream(path)
        Assertions.assertThat(inputStream).isNotNull
        val result = IOUtils.toString(inputStream, Charset.defaultCharset())
        Assertions.assertThat(result).isEqualTo("# This is a test project")
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamMavenName() {
        val path = Path.of(javaClass.getResource("/directory2NoReadme/project1Maven").toURI())
        val inputStream = ReadmeNamingParser.builder().templateLocation(templatePath)
            .fileFilterChain(FileFilterChain.Companion.createDefaultChain()).build().buildReadmeStream(path)
        Assertions.assertThat(inputStream).isNotNull
        val result = IOUtils.toString(inputStream, Charset.defaultCharset())
        Assertions.assertThat(result).isEqualTo("# This is a test project")
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamMavenNoName() {
        val path = Path.of(javaClass.getResource("/directory2NoReadme/project1MavenNoName").toURI())
        val inputStream = ReadmeNamingParser.builder().templateLocation(templatePath)
            .fileFilterChain(FileFilterChain.Companion.createDefaultChain()).build().buildReadmeStream(path)
        Assertions.assertThat(inputStream).isNotNull
        val result = IOUtils.toString(inputStream, Charset.defaultCharset())
        Assertions.assertThat(result).isEqualTo("# ProjectMavenArtifact")
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamNPM() {
        val path = Path.of(javaClass.getResource("/directory2NoReadme/project2NPM").toURI())
        val inputStream = ReadmeNamingParser.builder().templateLocation(templatePath)
            .fileFilterChain(FileFilterChain.Companion.createDefaultChain()).build().buildReadmeStream(path)
        Assertions.assertThat(inputStream).isNotNull
        val result = IOUtils.toString(inputStream, Charset.defaultCharset())
        Assertions.assertThat(result).isEqualTo("# npm-project")
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamGradle() {
        val path = Path.of(javaClass.getResource("/directory2NoReadme/project4Gradle").toURI())
        val inputStream = ReadmeNamingParser.builder().templateLocation(templatePath)
            .fileFilterChain(FileFilterChain.Companion.createDefaultChain()).build().buildReadmeStream(path)
        Assertions.assertThat(inputStream).isNotNull
        val result = IOUtils.toString(inputStream, Charset.defaultCharset())
        Assertions.assertThat(result).isEqualTo("# project4Gradle")
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamSBT() {
        val path = Path.of(javaClass.getResource("/directory2NoReadme/project5Sbt").toURI())
        val inputStream = ReadmeNamingParser.builder().templateLocation(templatePath)
            .fileFilterChain(FileFilterChain.Companion.createDefaultChain()).build().buildReadmeStream(path)
        Assertions.assertThat(inputStream).isNotNull
        val result = IOUtils.toString(inputStream, Charset.defaultCharset())
        Assertions.assertThat(result).isEqualTo("# sbt-project")
    }
}