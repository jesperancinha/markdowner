package org.jesperancinha.parser.markdowner.helper

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.io.IOException

class TemplateParserHelperTest {
    @Test
    @Throws(IOException::class)
    fun testFindAllParagraphs() {
        val resourceAsStream = javaClass.getResourceAsStream("/Readme.md")
        val allParagraphs = TemplateParserHelper.findAllParagraphs(resourceAsStream)
        Assertions.assertThat(allParagraphs.paragraphCount).isEqualTo(2)
        val license = allParagraphs.getParagraphByTag("License")
        val aboutMe = allParagraphs.getParagraphByTag("About me")
        Assertions.assertThat(license).isNotNull
        Assertions.assertThat(aboutMe).isNotNull
        Assertions.assertThat(license.tag).isEqualTo(LICENSE_TAG)
        Assertions.assertThat(aboutMe.tag).isEqualTo(ABOUT_ME_TAG)
        Assertions.assertThat(license.text).isEqualTo("This is one\nOne")
        Assertions.assertThat(aboutMe.text).isEqualTo("This is two\nTwo")
        Assertions.assertThat(allParagraphs.paragraphCount).isEqualTo(2)
    }

    @Test
    @Throws(IOException::class)
    fun testFindAllParagraphsEmojis() {
        val resourceAsStream = javaClass.getResourceAsStream("/ReameEmojis.md")
        val allParagraphs = TemplateParserHelper.findAllParagraphs(resourceAsStream)
        Assertions.assertThat(allParagraphs.paragraphCount).isEqualTo(2)
        val license = allParagraphs.getParagraphByTag("License")
        val aboutMe = allParagraphs.getParagraphByTag("About me")
        Assertions.assertThat(license).isNotNull
        Assertions.assertThat(aboutMe).isNotNull
        Assertions.assertThat(license.tag).isEqualTo(LICENSE_TAG)
        //        assertThat(aboutMe.getTag()).isEqualTo("## About me \uD83D\uDC68\uD83C\uDFFD\u200D\uD83D\uDCBB\uD83D\uDE80");
        Assertions.assertThat(license.text).isEqualTo("This is one\nOne")
        Assertions.assertThat(aboutMe.text).isEqualTo("This is two\nTwo")
        Assertions.assertThat(allParagraphs.paragraphCount).isEqualTo(2)
    }

    @Test
    @Throws(IOException::class)
    fun testFindAllParagraphsInnerParagraphs() {
        val resourceAsStream = javaClass.getResourceAsStream("/Readme-inner-paragraphs.md")
        val allParagraphs = TemplateParserHelper.findAllParagraphs(resourceAsStream)
        Assertions.assertThat(allParagraphs.paragraphCount).isEqualTo(2)
        val license = allParagraphs.getParagraphByTag("License")
        val aboutMe = allParagraphs.getParagraphByTag("About me")
        Assertions.assertThat(license).isNotNull
        Assertions.assertThat(aboutMe).isNotNull
        Assertions.assertThat(license.tag).isEqualTo(LICENSE_TAG)
        Assertions.assertThat(aboutMe.tag).isEqualTo(ABOUT_ME_TAG)
        Assertions.assertThat(license.text).isEqualTo("This is one\nOne\n### Inner text\nThis is inner text")
        Assertions.assertThat(aboutMe.text).isEqualTo("This is two\nTwo")
        Assertions.assertThat(allParagraphs.paragraphCount).isEqualTo(2)
    }

    companion object {
        private const val LICENSE_TAG = "## License"
        private const val ABOUT_ME_TAG = "## About me"
    }
}