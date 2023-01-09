package org.jesperancinha.parser.markdowner.badges.parser;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;

public class BadgeParser3Test {

    @Test
    public void testBadgeParser() throws IOException {
        final var badgeGroups =
                BadgeParser.parse(
                        IOUtils.resourceToString("/badges/Readme3.md", Charset.defaultCharset()));

        assertThat(badgeGroups).isNotNull();
    }

    @Test
    public void testBadgeSettingsParserr() {
        final var badgeSettingGroups =
                BadgeParser.parseSettings();

        assertThat(badgeSettingGroups).hasSize(8);
    }
}