package org.jesperancinha.parser.markdowner.badges.parser

import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldHaveLength
import org.junit.jupiter.api.Test

class BadgeParserKtTest {
    @Test
    fun `should parse emoji coffee cup`() {
        val testRegex = Regex("[".plus(BadgeParser.FULL_REGEX).plus("]"))
        val testString = "coffee ☕️ coffee"

        testString.shouldHaveLength(16)
        testString.replace(testRegex,"") .shouldHaveLength(0)
    }

    @Test
    fun `should parse emoji generic badges`() {
        val testText =
            "[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Coffee%20Cups%20Kalah%20☕️%20&color=informational)](https://github.com/jesperancinha/mancalaje)"
        val parse = BadgeParser.parse(testText)

        parse[parse.keys.first()]?.badgeHashMap?.filter { it.key.pattern().contains("Generic badge") }?.values?.first()
            .shouldNotBeNull()
    }

    @Test
    fun `should parse normal generic badges`() {
        val testText =
            "[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Project%20Signer&color=informational)](https://github.com/jesperancinha/project-signer)"
        val parse = BadgeParser.parse(testText)

        parse[parse.keys.first()]?.badgeHashMap?.filter { it.key.pattern().contains("Generic badge") }?.values?.first()
            .shouldNotBeNull()
    }
}