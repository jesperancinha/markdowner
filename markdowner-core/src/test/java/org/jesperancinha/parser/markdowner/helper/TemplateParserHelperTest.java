package org.jesperancinha.parser.markdowner.helper;

import org.jesperancinha.parser.markdowner.parser.Paragraph;
import org.jesperancinha.parser.markdowner.parser.Paragraphs;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jesperancinha.parser.markdowner.helper.TemplateParserHelper.findAllParagraphs;

public class TemplateParserHelperTest {

    private static final String LICENSE_TAG = "## License";
    private static final String ABOUT_ME_TAG = "## About me";

    @Test
    public void testFindAllParagraphs() throws IOException {
        final InputStream resourceAsStream = getClass().getResourceAsStream("/Readme.md");

        final Paragraphs allParagraphs = findAllParagraphs(resourceAsStream);

        assertThat(allParagraphs.getParagraphCount()).isEqualTo(2);
        final Paragraph license = allParagraphs.getParagraphByTag("License");
        final Paragraph aboutMe = allParagraphs.getParagraphByTag("About me");
        assertThat(license).isNotNull();
        assertThat(aboutMe).isNotNull();
        assertThat(license.getTag()).isEqualTo(LICENSE_TAG);
        assertThat(aboutMe.getTag()).isEqualTo(ABOUT_ME_TAG);
        assertThat(license.getText()).isEqualTo("This is one\nOne");
        assertThat(aboutMe.getText()).isEqualTo("This is two\nTwo");
        assertThat(allParagraphs.getParagraphCount()).isEqualTo(2);
    }

    @Test
    public void testFindAllParagraphsInnerParagraphs() throws IOException {
        final InputStream resourceAsStream = getClass().getResourceAsStream("/Readme-inner-paragraphs.md");

        final Paragraphs allParagraphs = findAllParagraphs(resourceAsStream);

        assertThat(allParagraphs.getParagraphCount()).isEqualTo(2);
        final Paragraph license = allParagraphs.getParagraphByTag("License");
        final Paragraph aboutMe = allParagraphs.getParagraphByTag("About me");
        assertThat(license).isNotNull();
        assertThat(aboutMe).isNotNull();
        assertThat(license.getTag()).isEqualTo(LICENSE_TAG);
        assertThat(aboutMe.getTag()).isEqualTo(ABOUT_ME_TAG);
        assertThat(license.getText()).isEqualTo("This is one\nOne\n### Inner text\nThis is inner text");
        assertThat(aboutMe.getText()).isEqualTo("This is two\nTwo");
        assertThat(allParagraphs.getParagraphCount()).isEqualTo(2);
    }
}