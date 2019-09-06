package org.jesperancinha.parser.projectsigner.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptions;
import org.junit.jupiter.api.Test;

public class OptionsServiceImplIT {

    private static final String TEMPLATE_LOCATION = "../project-signer-templates/Readme.md";
    private static final String ROOT_LOCATION = "../..";
    private static final String LICENSE = "License";
    private static final String ABOUT_ME = "About me";

    private OptionsServiceImpl optionsService = new OptionsServiceImpl();

    @Test
    public void testProcessOptions() {
        final String[] args = new String[]{
            "-t",
            TEMPLATE_LOCATION,
            LICENSE,
            ABOUT_ME,
            "-d",
            ROOT_LOCATION
        };

        final ProjectSignerOptions projectSignerOptions = optionsService.processOptions(args);

        assertThat(projectSignerOptions.getTemplateLocation().toString()).isEqualTo(TEMPLATE_LOCATION);
        assertThat(projectSignerOptions.getRootDirectory().toString()).isEqualTo(ROOT_LOCATION);
        assertThat(projectSignerOptions.getTagNames()).contains(LICENSE, ABOUT_ME);
        assertThat(projectSignerOptions.isNoEmpty()).isFalse();
    }

    @Test
    public void testProcessOptionsNE() {
        final String[] args = new String[]{
            "-t",
            TEMPLATE_LOCATION,
            LICENSE,
            ABOUT_ME,
            "-d",
            ROOT_LOCATION,
            "-ne"
        };

        final ProjectSignerOptions projectSignerOptions = optionsService.processOptions(args);

        assertThat(projectSignerOptions.getTemplateLocation().toString()).isEqualTo(TEMPLATE_LOCATION);
        assertThat(projectSignerOptions.getRootDirectory().toString()).isEqualTo(ROOT_LOCATION);
        assertThat(projectSignerOptions.getTagNames()).contains(LICENSE, ABOUT_ME);
        assertThat(projectSignerOptions.isNoEmpty()).isTrue();
    }

    @Test
    public void testProcessLongOptions() {
        final String[] args = new String[]{
            "--template-location",
            TEMPLATE_LOCATION,
            LICENSE,
            ABOUT_ME,
            "--root-directory",
            ROOT_LOCATION
        };

        final ProjectSignerOptions projectSignerOptions = optionsService.processOptions(args);

        assertThat(projectSignerOptions.getTemplateLocation().toString()).isEqualTo(TEMPLATE_LOCATION);
        assertThat(projectSignerOptions.getRootDirectory().toString()).isEqualTo(ROOT_LOCATION);
        assertThat(projectSignerOptions.getTagNames()).contains(LICENSE, ABOUT_ME);
        assertThat(projectSignerOptions.isNoEmpty()).isFalse();
    }

    @Test
    public void testProcessLongOptionsNE() {
        final String[] args = new String[]{
            "--template-location",
            TEMPLATE_LOCATION,
            LICENSE,
            ABOUT_ME,
            "--root-directory",
            ROOT_LOCATION,
            "--no-empty"
        };

        final ProjectSignerOptions projectSignerOptions = optionsService.processOptions(args);

        assertThat(projectSignerOptions.getTemplateLocation().toString()).isEqualTo(TEMPLATE_LOCATION);
        assertThat(projectSignerOptions.getRootDirectory().toString()).isEqualTo(ROOT_LOCATION);
        assertThat(projectSignerOptions.getTagNames()).contains(LICENSE, ABOUT_ME);
        assertThat(projectSignerOptions.isNoEmpty()).isTrue();
    }
}