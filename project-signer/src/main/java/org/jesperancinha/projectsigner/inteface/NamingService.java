package org.jesperancinha.projectsigner.inteface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public interface NamingService {
    InputStream buildReadmeStream(Path path) throws IOException;
}
