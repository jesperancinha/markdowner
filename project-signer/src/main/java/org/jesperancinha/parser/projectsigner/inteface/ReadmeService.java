package org.jesperancinha.parser.projectsigner.inteface;

import java.io.IOException;
import java.io.InputStream;

public interface ReadmeService {
    String readDataSprippedOfTags(InputStream templateInputStream, String... tags) throws IOException;
}
