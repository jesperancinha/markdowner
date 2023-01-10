package org.jesperancinha.parser.markdowner.helper

import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.io.IOException

class TemplateParserHelperTest {
    @Test
    @Throws(IOException::class)
    fun testFindAllParagraphs() {
        val resourceAsStream = javaClass.getResourceAsStream("/Readme.md")
        resourceAsStream.shouldNotBeNull()
        val allParagraphs = TemplateParserHelper.findAllParagraphs(resourceAsStream)
        allParagraphs.shouldNotBeNull()
        allParagraphs.getParagraphCount() shouldBe 2
        val license = allParagraphs.getParagraphByTag("License")
        val aboutMe = allParagraphs.getParagraphByTag("About me")
        license.shouldNotBeNull()
        aboutMe.shouldNotBeNull()
        license.tag shouldBe LICENSE_TAG
        aboutMe.tag shouldBe ABOUT_ME_TAG_STRIPPED
        license.text shouldBe "This is one\nOne"
        aboutMe.text shouldBe "This is two\nTwo"
    }

    @Test
    @Throws(IOException::class)
    fun testFindAllParagraphsEmojis() {
        val resourceAsStream = javaClass.getResourceAsStream("/ReameEmojis.md")
        resourceAsStream.shouldNotBeNull()
        val allParagraphs = TemplateParserHelper.findAllParagraphs(resourceAsStream)
        allParagraphs.shouldNotBeNull()
        allParagraphs.getParagraphCount() shouldBe 2
        val license = allParagraphs.getParagraphByTag("License")
        val aboutMe = allParagraphs.getParagraphByTag("About me")
        license.shouldNotBeNull()
        aboutMe.shouldNotBeNull()
        license.tag shouldBe LICENSE_TAG
        aboutMe.tag shouldBe ABOUT_ME_TAG
        license.text shouldBe "This is one\nOne"
        aboutMe.text shouldBe "This is two\nTwo"
    }

    @Test
    @Throws(IOException::class)
    fun testFindAllParagraphsInnerParagraphs() {
        val resourceAsStream = javaClass.getResourceAsStream("/Readme-inner-paragraphs.md")
        resourceAsStream.shouldNotBeNull()
        val allParagraphs = TemplateParserHelper.findAllParagraphs(resourceAsStream)
        allParagraphs.shouldNotBeNull()
        allParagraphs.getParagraphCount() shouldBe 2
        val license = allParagraphs.getParagraphByTag("License")
        val aboutMe = allParagraphs.getParagraphByTag("About me")
        license.shouldNotBeNull()
        aboutMe.shouldNotBeNull()
        license.tag shouldBe LICENSE_TAG
        aboutMe.tag shouldBe ABOUT_ME_TAG_STRIPPED
        license.text shouldBe "This is one\nOne\n### Inner text\nThis is inner text"
        aboutMe.text shouldBe "This is two\nTwo"
    }

    companion object {
        private const val LICENSE_TAG = "## License"
        private const val ABOUT_ME_TAG = "## About me \uD83D\uDC68\uD83C\uDFFD\u200D\uD83D\uDCBB\uD83D\uDE80"
        private const val ABOUT_ME_TAG_STRIPPED = "## About me"
    }
}