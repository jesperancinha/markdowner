package org.jesperancinha.projectsigner.service;

import org.jesperancinha.projectsigner.inteface.FileWriterService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void exportReadmeFile(Path path, String text) throws IOException {
        final File f = new File(path.toFile(), "Readme.md");
        final FileWriter fileWriter = new FileWriter(f);
        fileWriter.write(text);
        fileWriter.flush();
        fileWriter.close();
    }
}
