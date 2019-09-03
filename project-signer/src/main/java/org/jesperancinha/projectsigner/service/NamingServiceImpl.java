package org.jesperancinha.projectsigner.service;

import org.jesperancinha.projectsigner.filter.FileFilterChain;
import org.jesperancinha.projectsigner.inteface.NamingService;
import org.jesperancinha.projectsigner.model.ProjectType;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class NamingServiceImpl implements NamingService {

    private FileFilterChain fileFilterChain;

    public NamingServiceImpl(final FileFilterChain fileFilterChain){
        this.fileFilterChain = fileFilterChain;
    }

    @Override
    public InputStream buildReadmeStream(Path path) throws IOException {
        Path readmePath = path.resolve("Readme.md");
        File readmeFile = readmePath.toFile();
        if (readmeFile.exists()) {
            return new FileInputStream(readmeFile);
        }

        ProjectType projectType = findProjectType(path);

        return null;
    }

    private ProjectType findProjectType(Path path) throws IOException {
        File directory = path.toFile();
        ProjectType highestLevel = ProjectType.SBT;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path newPath : stream) {
                if (!Files.isDirectory(path)) {
//                    highestLevel =  fileFilterChain.findHighest(highestLevel, path);
                }
            }
        }
        return ProjectType.MAVEN;
    }
}