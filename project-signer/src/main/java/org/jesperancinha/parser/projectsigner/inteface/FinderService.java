package org.jesperancinha.parser.projectsigner.inteface;

import java.io.IOException;
import java.nio.file.Path;

public interface FinderService {
    void iterateThroughFilesAndFolders(Path rootPath) throws IOException;
}
