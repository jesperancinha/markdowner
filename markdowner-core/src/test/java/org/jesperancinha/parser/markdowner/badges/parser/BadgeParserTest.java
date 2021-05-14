package org.jesperancinha.parser.markdowner.badges.parser;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BadgeParserTest {

    @Test
    public void testBadgeParser() {
        final var badgeGroups =
                BadgeParser.parse(getClass().getResourceAsStream("/badges/Readme.md"));

        assertThat(badgeGroups).isNotNull();
    }

    @Test
    public void testBadgeSettingsParserr() {
        final var badgeSettingGroups =
                BadgeParser.parseSettings();

        assertThat(badgeSettingGroups).hasSize(5);
    }
}