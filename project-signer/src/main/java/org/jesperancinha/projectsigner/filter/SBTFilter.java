package org.jesperancinha.projectsigner.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

@Slf4j
public class SBTFilter implements ProjectFilter<File> {
    @Override
    public boolean test(Path path) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split("\\s*:=\\s*");
                if (split.length == 2) {
                    String left = split[0].trim();
                    if (left.equals("name")) {
                        String right = split[1].trim();
                        if (Strings.isNotEmpty(right)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (IOException e) {
            log.trace("Not SBT format!");
            return false;
        }
    }
}
