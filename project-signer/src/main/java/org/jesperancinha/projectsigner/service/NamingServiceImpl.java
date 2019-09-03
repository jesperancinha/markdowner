package org.jesperancinha.projectsigner.service;

import org.jesperancinha.projectsigner.filter.FileFilterChain;
import org.jesperancinha.projectsigner.inteface.NamingService;
import org.jesperancinha.projectsigner.model.PackageInfo;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Service
public class NamingServiceImpl implements NamingService {

    private FileFilterChain fileFilterChain;

    public NamingServiceImpl(final FileFilterChain fileFilterChain) {
        this.fileFilterChain = fileFilterChain;
    }

    @Override
    public InputStream buildReadmeStream(Path path) throws IOException {
        final Path readmePath = path.resolve("Readme.md");
        final File readmeFile = readmePath.toFile();
        if (readmeFile.exists()) {
            return new FileInputStream(readmeFile);
        }
        final PackageInfo packageInfo = findProjectType(path);
        if (Objects.isNull(packageInfo)) {
            return null;
        }
        return new ByteArrayInputStream("# ".concat(packageInfo.getProjectName()).getBytes());
    }

    private PackageInfo findProjectType(Path path) throws IOException {
        PackageInfo highestLevel = null;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path newPath : stream) {
                if (!Files.isDirectory(newPath)) {
                    highestLevel = fileFilterChain.findHighest(highestLevel, newPath);
                }
            }
        }
        return highestLevel;
    }
}