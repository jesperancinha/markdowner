package org.jesperancinha.parser.markdowner.helper

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import org.junit.jupiter.api.Test

class TagHelperTest {
    @Test
    fun `should sanitize tag`() {
        val result = TagHelper.sanitizeTag("### Title1 ##")
        result shouldBe "Title1 ##"
    }

    @Test
    fun `should sanitize all hashes to empty`() {
        val result = TagHelper.sanitizeTag("#####")
        result.shouldBeEmpty()
    }

    @Test
    fun `should count hashtags correctly 3`() {
        val result = TagHelper.counHashTags("### okoko #")
        result shouldBe 3
    }
}