package org.jesperancinha.parser.markdowner.badges.parser

import org.apache.commons.io.IOUtils
import org.assertj.core.api.Assertions
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
        Assertions.assertThat(badgeGroups).isNotNull
    }

    @Test
    fun testBadgeSettingsParserr() {
        val badgeSettingGroups = BadgeParser.parseSettings()
        Assertions.assertThat(badgeSettingGroups).hasSize(8)
    }
}