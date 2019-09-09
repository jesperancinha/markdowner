package org.jesperancinha.parser.markdowner.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StandardUtilsTest {

    @Test
    public void testSanitizeTag() {
        final String result = StandardUtils.sanitizeTag("### Title1 ##");
        assertThat(result).isEqualTo("Title1 ##");
    }

    @Test
    public void testSanitizeTagAllHashes() {
        final String result = StandardUtils.sanitizeTag("#####");
        assertThat(result).isEmpty();
    }

    @Test
    public void testCounHashTags() {
        final int result = StandardUtils.counHashTags("### okoko #");
        assertThat(result).isEqualTo(3);
    }
}