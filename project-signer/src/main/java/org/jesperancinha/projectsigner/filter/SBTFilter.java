package org.jesperancinha.projectsigner.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

@Slf4j
public class SBTFilter implements ProjectFilter<Path> {

    private static final String NAME = "name";
    
    private String lastProjectName;

    @Override
    public boolean test(Path path) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split("\\s*:=\\s*");
                if (split.length == 2) {
                    String left = split[0].trim();
                    if (left.equals(NAME)) {
                        String right = split[1].trim();
                        if (Strings.isNotEmpty(right)) {
                            this.lastProjectName = right.substring(1, right.length() - 1);
                            return true;
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.trace("Not SBT format!");
        }
        return false;
    }

    @Override
    public String lastProjectName() {
        return this.lastProjectName;
    }
}
