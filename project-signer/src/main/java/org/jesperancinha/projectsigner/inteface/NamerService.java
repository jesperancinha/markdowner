package org.jesperancinha.projectsigner.inteface;

import java.io.InputStream;
import java.nio.file.Path;

public interface NamerService {
    InputStream buildReadmeStream(Path path);
}
