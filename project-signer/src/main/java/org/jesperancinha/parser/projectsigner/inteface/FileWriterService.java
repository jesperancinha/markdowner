package org.jesperancinha.parser.projectsigner.inteface;

import java.io.IOException;
import java.nio.file.Path;

public interface FileWriterService {

    void exportReadmeFile(Path path, String text) throws IOException;
}
