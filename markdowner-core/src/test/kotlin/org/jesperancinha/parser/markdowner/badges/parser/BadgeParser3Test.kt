package org.jesperancinha.parser.markdowner.badges.parser

import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import org.apache.commons.io.IOUtils
import org.junit.jupiter.api.Test
import java.io.IOException
import java.nio.charset.Charset

class BadgeParser3Test {
    @Test
    @Throws(IOException::class)
    fun testBadgeParser() {
        val badgeGroups = BadgeParser.parse(
            IOUtils.resourceToString("/badges/Readme3.md", Charset.defaultCharset())
        )
        badgeGroups.shouldNotBeNull()
    }

    @Test
    fun testBadgeSettingsParser() {
        val badgeSettingGroups = BadgeParser.parseSettings()
        badgeSettingGroups.shouldNotBeNull()
        badgeSettingGroups.shouldHaveSize(8)
    }
}