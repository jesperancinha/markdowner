package org.jesperancinha.parser.projectsigner.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.nio.file.Path;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSignerOptions {

    @Option(names = {"-t", "--template-location"}, paramLabel = "Template location", description = "Location of the signin template", required = true)
    private String templateLocation;

    @Parameters(paramLabel = "Start tags", description = "Start of paragraph replace. This will remove all paragraphs with these names. It only applies to rules with '#' title markdown notation")
    private String[] tagNames;

    @Option(names = {"-d", "--root-directory"}, paramLabel = "Root directory", description = "Where to start searching for sub Readme.md files and/or empty projects")
    private String rootDirectory;

    @Option(names = {"-ne", "--no-empty"}, paramLabel = "No Empty", description = "If set, it does not create empty signed Readme.md files", defaultValue = "false")
    private boolean noEmpty;

    public Path getRootDirectory() {
        return Path.of(rootDirectory);
    }

    public Path getTemplateLocation() {
        return Path.of(templateLocation);
    }
}
