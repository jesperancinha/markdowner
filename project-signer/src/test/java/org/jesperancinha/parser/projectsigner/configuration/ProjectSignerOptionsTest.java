package org.jesperancinha.parser.projectsigner.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

public class ProjectSignerOptionsTest {

    private static final String TEST_LOCATION = "location";
    private static final String TEST_LABEL_1 = "label1";
    private static final String TEST_LABEL_2 = "label2";
    private static final String TEST_ROOT = "root";

    @Test
    public void parseOptions() {
        final String[] args = {"-t", TEST_LOCATION, TEST_LABEL_1, TEST_LABEL_2, "-d", TEST_ROOT};
        final ProjectSignerOptions projectSignerOptions = new ProjectSignerOptions();
        new CommandLine(projectSignerOptions).parseArgs(args);

        assertThat(projectSignerOptions.getTemplateLocation().toString()).isEqualTo(TEST_LOCATION);
        assertThat(projectSignerOptions.getTagNames()).containsOnly(TEST_LABEL_1, TEST_LABEL_2);
        assertThat(projectSignerOptions.getRootDirectory().toString()).isEqualTo(TEST_ROOT);
        assertThat(projectSignerOptions.isNoEmpty()).isFalse();
    }

    @Test
    public void parseOptionsWithNoEmpty() {
        final String[] args = {"-t", TEST_LOCATION, TEST_LABEL_1, TEST_LABEL_2, "-d", TEST_ROOT, "-ne"};
        final ProjectSignerOptions projectSignerOptions = new ProjectSignerOptions();
        new CommandLine(projectSignerOptions).parseArgs(args);

        assertThat(projectSignerOptions.getTemplateLocation().toString()).isEqualTo(TEST_LOCATION);
        assertThat(projectSignerOptions.getTagNames()).containsOnly(TEST_LABEL_1, TEST_LABEL_2);
        assertThat(projectSignerOptions.getRootDirectory().toString()).isEqualTo(TEST_ROOT);
        assertThat(projectSignerOptions.isNoEmpty()).isTrue();
    }

}