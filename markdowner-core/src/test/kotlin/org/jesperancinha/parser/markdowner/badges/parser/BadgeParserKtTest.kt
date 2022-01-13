package org.jesperancinha.parser.markdowner.badges.parser

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldHaveLength
import org.junit.jupiter.api.Test

class BadgeParserKtTest {
    @Test
    fun `should parse emoji yucca`() {
        val testRegex = Regex("[".plus(BadgeParser.FULL_REGEX).plus("]"))
        val testString = "yucca 🌴 yucca"

        testString.shouldHaveLength(14)
        testString.replace(testRegex, "").shouldHaveLength(2)
    }

    @Test
    fun `should parse emoji coffee cup`() {
        val testRegex = Regex("[".plus(BadgeParser.FULL_REGEX).plus("]"))
        val testString = "coffee ☕️ coffee"

        testString.shouldHaveLength(16)
        testString.replace(testRegex, "").shouldHaveLength(2)
    }

    @Test
    fun `should parse construction`() {
        val testRegex = Regex("[".plus(BadgeParser.FULL_REGEX).plus("]"))
        val testString = "construction 🚧️ construction"

        testString.shouldHaveLength(29)
        testString.replace(testRegex, "").shouldHaveLength(3)
    }

    @Test
    fun `should parse emoji coin cup`() {
        val testRegex = Regex("[".plus(BadgeParser.FULL_REGEX).plus("]"))
        val testString = "coin 🪙 coin"

        testString.shouldHaveLength(12)
        testString.replace(testRegex, "").shouldHaveLength(2)
    }

    @Test
    fun `should parse emoji guitar cup`() {
        val testRegex = Regex("[".plus(BadgeParser.FULL_REGEX).plus("]"))
        val testString = "guitar 🎸 guitar"

        testString.shouldHaveLength(16)
        testString.replace(testRegex, "").shouldHaveLength(2)
    }

    @Test
    fun `should parse emoji pen`() {
        val testRegex = Regex("[".plus(BadgeParser.FULL_REGEX).plus("]"))
        val testString = "pen \uD83D\uDD8B pen"

        testString.shouldHaveLength(10)
        testString.replace(testRegex, "").shouldHaveLength(2)
    }

    @Test
    fun `should parse emoji generic badges example 1`() {
        val testText =
            "[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Coffee%20Cups%20Kalah%20☕️%20&color=informational)](https://github.com/jesperancinha/mancalaje)"
        val parse = BadgeParser.parse(testText)

        parse[parse.keys.first()]?.badgeHashMap?.filter { it.key.pattern().contains("Generic badge") }?.values?.first()
            .shouldNotBeNull()
    }

    @Test
    fun `should parse emoji generic badges example 2`() {
        val testText =
            "[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=ISBN%20Stacks%20\uD83D\uDCDA%20&color=informational)](https://github.com/jesperancinha/isbn-stacks)"
        val parse = BadgeParser.parse(testText)

        parse[parse.keys.first()]?.badgeHashMap?.filter { it.key.pattern().contains("Generic badge") }?.values?.first()
            .shouldNotBeNull()
    }

    @Test
    fun `should parse emoji generic badges example 3`() {
        val testText =
            "[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=City%20Library%20Management%20\uD83C\uDFE2&color=informational)](https://github.com/jesperancinha/advanced-library-management)"
        val parse = BadgeParser.parse(testText)

        parse[parse.keys.first()]?.badgeHashMap?.filter { it.key.pattern().contains("Generic badge") }?.values?.first()
            .shouldNotBeNull()
    }

    @Test
    fun `should parse emoji generic badges example 4`() {
        val testText =
            "[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Project%%20Signer%20\uD83D\uDD8B&color=informational)](https://github.com/jesperancinha/project-signer)"
        val parse = BadgeParser.parse(testText)

        parse[parse.keys.first()]?.badgeHashMap?.filter { it.key.pattern().contains("Generic badge") }?.values?.first()
            .shouldNotBeNull()
    }

    @Test
    fun `should parse emoji generic badges example 5`() {
        val testText =
            "[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=From%20Paris%20to%20Berlin%20\uD83D\uDEE3&color=informational)](https://github.com/jesperancinha/from-paris-to-berlin-circuit-breaker)"
        val parse = BadgeParser.parse(testText)

        parse[parse.keys.first()]?.badgeHashMap?.filter { it.key.pattern().contains("Generic badge") }?.values?.first()
            .shouldNotBeNull()
    }

    @Test
    fun `should parse emoji generic badges example 6`() {
        val testText =
            "[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Concert%20Demos%20\uD83C\uDFB8%20&color=informational)](https://github.com/jesperancinha/concert-demos-root)"
        val parse = BadgeParser.parse(testText)

        parse[parse.keys.first()]?.badgeHashMap?.filter { it.key.pattern().contains("Generic badge") }?.values?.first()
            .shouldNotBeNull()
    }

    @Test
    fun `should parse emoji generic badges example 7 (color is not informational)`() {
        val testText =
            "[![Status badge](https://img.shields.io/static/v1.svg?label=Status&message=Under%20Construction%20\uD83D\uDEA7&color=purple)](https://github.com/jesperancinha/portuguese-recipes)\n"
        val parse = BadgeParser.parse(testText)

        val parseStatusBadges = parse.entries.flatMap { entry ->
            entry.value.badgeHashMap.filter {
                it.key.pattern().contains("Status badge")
            }.values
        }
        parseStatusBadges.shouldHaveSize(1)
        parseStatusBadges.first().shouldBeNull()
    }

    @Test
    fun `should parse emoji generic badges example 8 (color is informational)`() {
        val testText =
            "[![Status badge](https://img.shields.io/static/v1.svg?label=Status&message=Under%20Construction%20\uD83D\uDEA7&color=informational)](https://github.com/jesperancinha/portuguese-recipes)\n"
        val parse = BadgeParser.parse(testText)

        val parseStatusBadges = parse.entries.flatMap { entry ->
            entry.value.badgeHashMap.filter {
                it.key.pattern().contains("Status badge")
            }.values
        }
        parseStatusBadges.shouldHaveSize(1)
        parseStatusBadges.first().shouldNotBeNull()
    }

    @Test
    fun `should parse normal generic badges example 1`() {
        val testText =
            "[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Project%20Signer&color=informational)](https://github.com/jesperancinha/project-signer)"
        val parse = BadgeParser.parse(testText)

        parse[parse.keys.first()]?.badgeHashMap?.filter { it.key.pattern().contains("Generic badge") }?.values?.first()
            .shouldNotBeNull()
    }
}