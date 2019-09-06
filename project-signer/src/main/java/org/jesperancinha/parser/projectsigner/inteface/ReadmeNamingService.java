package org.jesperancinha.parser.projectsigner.inteface;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public interface ReadmeNamingService {
    InputStream buildReadmeStream(Path path) throws IOException;
}
