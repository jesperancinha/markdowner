package org.jesperancinha.projectsigner.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.jesperancinha.projectsigner.model.Paragraph;
import org.jesperancinha.projectsigner.model.Paragraphs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;

@ExtendWith(MockitoExtension.class)
public class TemplateServiceTest {

    @InjectMocks
    private TemplateService templateService;

    @Test
    void findAllParagraphs() throws IOException {
        final InputStream resourceAsStream = getClass().getResourceAsStream("/Readme.md");

        final Paragraphs allParagraphs = templateService.findAllParagraphs(resourceAsStream);

        assertThat(allParagraphs.getParagraphCount()).isEqualTo(2);
        final Paragraph license = allParagraphs.getParagraphByTag("License");
        final Paragraph aboutMe = allParagraphs.getParagraphByTag("About me");
        assertThat(license).isNotNull();
        assertThat(aboutMe).isNotNull();
        assertThat(license.getText()).isEqualTo("This is one\nOne\n");
        assertThat(aboutMe.getText()).isEqualTo("This is two\nTwo\n");
        assertThat(allParagraphs.getParagraphCount()).isEqualTo(2);
    }
}