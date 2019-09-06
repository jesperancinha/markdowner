package org.jesperancinha.parser.markdowner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jesperancinha.parser.markdowner.TemplateParserHelper.findAllParagraphs;

import org.jesperancinha.parser.markdowner.model.Paragraph;
import org.jesperancinha.parser.markdowner.model.Paragraphs;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

class TemplateParserHelperTest {


    @Test
    public void testFindAllParagraphs() throws IOException {
        final InputStream resourceAsStream = getClass().getResourceAsStream("/Readme.md");

        final Paragraphs allParagraphs = findAllParagraphs(resourceAsStream);

        assertThat(allParagraphs.getParagraphCount()).isEqualTo(2);
        final Paragraph license = allParagraphs.getParagraphByTag("License");
        final Paragraph aboutMe = allParagraphs.getParagraphByTag("About me");
        assertThat(license).isNotNull();
        assertThat(aboutMe).isNotNull();
        assertThat(license.getText()).isEqualTo("This is one\nOne");
        assertThat(aboutMe.getText()).isEqualTo("This is two\nTwo");
        assertThat(allParagraphs.getParagraphCount()).isEqualTo(2);
    }
}