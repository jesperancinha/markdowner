package org.jesperancinha.parser.markdowner.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Filter to check if folder contains a Simple Build Tool project and keeps the project name in memory
 */
@Slf4j
public class SBTFilter extends ProjectFilter<Path> {

    private static final String NAME = "name";

    @Override
    public boolean test(Path path) {
        final boolean maybeSBTBuild = path.getFileName().toString().equals("build.sbt");
        if (maybeSBTBuild) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (hasName(line)) {
                        return true;
                    }
                }
            } catch (IOException e) {
                log.trace("Not SBT format!");
            }
        }
        return false;
    }

    @Override
    public String lastProjectName() {
        return this.lastProjectName;
    }

    private boolean hasName(String line) {
        final String[] split = line.split("\\s*:=\\s*");
        if (split.length == 2) {
            return isNameProperty(split);
        }
        return false;
    }

    private boolean isNameProperty(String[] split) {
        final String left = split[0].trim();
        if (left.equals(NAME)) {
            return isValueAString(split[1]);
        }
        return false;
    }

    private boolean isValueAString(String s) {
        final String right = s.trim();
        if (Strings.isNotEmpty(right)) {
            this.lastProjectName = right.substring(1, right.length() - 1);
            return true;
        }
        return false;
    }
}
