package org.jesperancinha.projectsigner.service;

import org.jesperancinha.projectsigner.filter.FileFilterChain;
import org.jesperancinha.projectsigner.inteface.OptionsService;
import org.jesperancinha.projectsigner.inteface.ReadmeNamingService;
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
public class ReadmeNamingServiceImpl implements ReadmeNamingService {

    private OptionsService optionsService;
    private FileFilterChain fileFilterChain;

    public ReadmeNamingServiceImpl(final FileFilterChain fileFilterChain,
                                   final OptionsService optionsService) {
        this.fileFilterChain = fileFilterChain;
        this.optionsService = optionsService;
    }

    @Override
    public InputStream buildReadmeStream(Path path) throws IOException {
        if(path.equals(optionsService.getProjectSignerOptions().getTemplateLocation())){
            return null;
        }
        final Path readmePath = path.resolve("Readme.md");
        final File readmeFile = readmePath.toFile();
        if (readmeFile.exists()) {
            return new FileInputStream(readmeFile);
        }
        final PackageInfo packageInfo = findProjectType(path);
        if (Objects.isNull(packageInfo)) {
            return null;
        }

        if (optionsService.getProjectSignerOptions().isNoEmpty()) {
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