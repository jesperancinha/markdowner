package org.jesperancinha.parser.markdowner.helper

import org.assertj.core.api.Assertions
import org.jesperancinha.parser.markdowner.model.Paragraphs.ParagraphsBuilder
import org.junit.jupiter.api.Test

class MergeParserHelperTest {
    @Test
    fun mergeDocumentWithFooterTemplate() {
        val testReadme = "Readme text!"
        val paragraphsBuilder = ParagraphsBuilder()
        paragraphsBuilder.withTagParagraph("## tag", "This is a test paragraph")
        paragraphsBuilder.withTagParagraph("## ta", "This is a test2 paragraph")
        val testParagraphs = paragraphsBuilder.build()
        val result = MergeParserHelper.mergeDocumentWithFooterTemplate(testReadme, testParagraphs)
        Assertions.assertThat(result)
            .isEqualTo("Readme text!\n\n## tag\nThis is a test paragraph\n\n## ta\nThis is a test2 paragraph\n")
    }

    @Test
    fun mergeDocumentWithFooterTemplateDifferentOrder() {
        val testReadme = "Readme text!"
        val paragraphsBuilder = ParagraphsBuilder()
        paragraphsBuilder.withTagParagraph("## ta", "This is a test2 paragraph")
        paragraphsBuilder.withTagParagraph("## tag", "This is a test paragraph")
        val testParagraphs = paragraphsBuilder.build()
        val result = MergeParserHelper.mergeDocumentWithFooterTemplate(testReadme, testParagraphs)
        Assertions.assertThat(result)
            .isEqualTo("Readme text!\n\n## ta\nThis is a test2 paragraph\n\n## tag\nThis is a test paragraph\n")
    }
}