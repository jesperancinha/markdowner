package org.jesperancinha.projectsigner.service;

import org.jesperancinha.parser.model.Paragraph;
import org.jesperancinha.parser.model.Paragraphs;
import org.jesperancinha.projectsigner.inteface.TemplateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TemplateServiceImplTest {

    @InjectMocks
    private TemplateService templateService = new TemplateServiceImpl();

    @Test
    void findAllParagraphs() throws IOException {
        final InputStream resourceAsStream = getClass().getResourceAsStream("/Readme.md");

        final Paragraphs allParagraphs = templateService.findAllParagraphs(resourceAsStream);

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