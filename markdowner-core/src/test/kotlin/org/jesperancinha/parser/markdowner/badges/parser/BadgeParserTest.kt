package org.jesperancinha.parser.markdowner.badges.parser

import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import org.apache.commons.io.IOUtils
import org.junit.jupiter.api.Test
import java.io.IOException
import java.nio.charset.Charset

class BadgeParserTest {
    @Test
    @Throws(IOException::class)
    fun testBadgeParser() {
        val badgeGroups = BadgeParser.parse(
            IOUtils.resourceToString("/badges/Readme.md", Charset.defaultCharset())
        )
        badgeGroups.shouldNotBeNull()
    }

    @Test
    fun `should run badge setting parser`(): Unit = run {
        val badgeSettingGroups = BadgeParser.parseSettings()
        badgeSettingGroups.shouldNotBeNull()
        badgeSettingGroups.shouldHaveSize(8)
    }

    @Test
    fun `should test badge parser`(): Unit = run { BadgeParser.badgeTypes.shouldNotBeNull() }

    @Test
    fun `should test badge settings parser`(): Unit = run { BadgeParser.badgeSettingGroups.shouldNotBeNull() }
}