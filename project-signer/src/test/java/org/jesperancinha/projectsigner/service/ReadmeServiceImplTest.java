package org.jesperancinha.projectsigner.service;

import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.projectsigner.inteface.ReadmeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith(SpringExtension.class)
class ReadmeServiceImplTest {

    private static final String DIRECTORY_0_README_MD = "/directory1/Readme.md";
    private static final String DIRECTORY_1_README_MD = "/directory1/subDirectory1/Readme.md";
    private static final String DIRECTORY_1_SPECIAL_CASE_1 = "/directory1/specialCase1/Readme.md";

    @InjectMocks
    private ReadmeService readmeService = new ReadmeServiceImpl();

    @Test
    void testReadDataSprippedOfTagsFile0Label1() throws IOException {
        final InputStream resourceAsStream = getClass().getResourceAsStream(DIRECTORY_0_README_MD);

        final String label1 = readmeService.readDataSprippedOfTags(resourceAsStream, "label1");

        assertThat(label1).isEqualTo("# label3");
    }

    @Test
    void testReadDataSprippedOfTagsFile0Label2() throws IOException {
        final InputStream resourceAsStream = getClass().getResourceAsStream(DIRECTORY_0_README_MD);

        final String label2 = readmeService.readDataSprippedOfTags(resourceAsStream, "label2");

        assertThat(label2).isEqualTo("## label1\n\n# label3");
    }

    @Test
    void testReadDataSprippedOfTagsFile0Label3() throws IOException {
        final InputStream resourceAsStream = getClass().getResourceAsStream(DIRECTORY_0_README_MD);

        final String label3 = readmeService.readDataSprippedOfTags(resourceAsStream, "label3");

        assertThat(label3).isEqualTo("## label1\n\n### label2");
    }


    @Test
    void testReadDataSprippedOfTagsFile1Label1() throws IOException {
        final InputStream resourceAsStream = getClass().getResourceAsStream(DIRECTORY_1_README_MD);

        final String label1 = readmeService.readDataSprippedOfTags(resourceAsStream, "label1");

        assertThat(label1).isEqualTo("# label2\n\n# label3");
    }

    @Test
    void testReadDataSprippedOfTagsFile1Label2() throws IOException {
        final InputStream resourceAsStream = getClass().getResourceAsStream(DIRECTORY_1_README_MD);

        final String label2 = readmeService.readDataSprippedOfTags(resourceAsStream, "label2");

        assertThat(label2).isEqualTo("# label1\n\n# label3");
    }

    @Test
    void testReadDataSprippedOfTagsFile1Label3() throws IOException {
        final InputStream resourceAsStream = getClass().getResourceAsStream(DIRECTORY_1_README_MD);

        final String label3 = readmeService.readDataSprippedOfTags(resourceAsStream, "label3");

        assertThat(label3).isEqualTo("# label1\n\n# label2");
    }

    @Test
    void testReadDataSprippedOfTagsSpecialCase1() throws IOException {
        final InputStream resourceAsStream = getClass().getResourceAsStream(DIRECTORY_1_SPECIAL_CASE_1);

        final String label3 = readmeService.readDataSprippedOfTags(resourceAsStream, "License", "About me");

        assertThat(label3).isEqualTo("# Mancala JE");
    }

}