package org.jesperancinha.parser.markdowner.helper;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TagHelperTest {

    @Test
    public void testSanitizeTag() {
        final String result = TagHelper.sanitizeTag("### Title1 ##");
        assertThat(result).isEqualTo("Title1 ##");
    }

    @Test
    public void testSanitizeTagAllHashes() {
        final String result = TagHelper.sanitizeTag("#####");
        assertThat(result).isEmpty();
    }

    @Test
    public void testCounHashTags() {
        final int result = TagHelper.counHashTags("### okoko #");
        assertThat(result).isEqualTo(3);
    }
}