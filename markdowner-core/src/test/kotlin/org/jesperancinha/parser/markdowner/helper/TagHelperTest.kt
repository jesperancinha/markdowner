package org.jesperancinha.parser.markdowner.helper

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class TagHelperTest {
    @Test
    fun testSanitizeTag() {
        val result = TagHelper.sanitizeTag("### Title1 ##")
        Assertions.assertThat(result).isEqualTo("Title1 ##")
    }

    @Test
    fun testSanitizeTagAllHashes() {
        val result = TagHelper.sanitizeTag("#####")
        Assertions.assertThat(result).isEmpty()
    }

    @Test
    fun testCounHashTags() {
        val result = TagHelper.counHashTags("### okoko #")
        Assertions.assertThat(result).isEqualTo(3)
    }
}