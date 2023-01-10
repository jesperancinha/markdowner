package org.jesperancinha.parser.markdowner.helper

import io.kotest.matchers.shouldBe
import org.jesperancinha.parser.markdowner.model.Paragraphs
import org.junit.jupiter.api.Test

class MergeParserHelperTest {
    @Test
    fun mergeDocumentWithFooterTemplate() {
        val testReadme = "Readme text!"
        val paragraphsBuilder = Paragraphs()
        paragraphsBuilder.withTagParagraph("## tag", "This is a test paragraph")
        paragraphsBuilder.withTagParagraph("## ta", "This is a test2 paragraph")
        val testParagraphs = paragraphsBuilder
        val result = MergeParserHelper.mergeDocumentWithFooterTemplate(testReadme, testParagraphs)
        result shouldBe "Readme text!\n\n## tag\nThis is a test paragraph\n\n## ta\nThis is a test2 paragraph\n"
    }

    @Test
    fun mergeDocumentWithFooterTemplateDifferentOrder() {
        val testReadme = "Readme text!"
        val paragraphsBuilder = Paragraphs()
        paragraphsBuilder.withTagParagraph("## ta", "This is a test2 paragraph")
        paragraphsBuilder.withTagParagraph("## tag", "This is a test paragraph")
        val testParagraphs = paragraphsBuilder
        val result = MergeParserHelper.mergeDocumentWithFooterTemplate(testReadme, testParagraphs)
        result shouldBe "Readme text!\n\n## ta\nThis is a test2 paragraph\n\n## tag\nThis is a test paragraph\n"
    }
}