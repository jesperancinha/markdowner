package org.jesperancinha.projectsigner.filter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

@Slf4j
public class NPMFilter implements ProjectFilter<Path> {

    private static final String NAME = "name";

    private String lastProjectName;

    @Override
    public boolean test(Path path) {
        try {
            JsonElement jsonElement = new Gson().getAdapter(JsonElement.class).fromJson(new FileReader(path.toFile()));
            JsonElement name = jsonElement.getAsJsonObject().get(NAME);
            if (Objects.nonNull(name)) {
                this.lastProjectName = name.getAsString();
                return true;
            }
        } catch (IOException e) {
            log.trace("Not a valid JSON!", e);
        }
        return false;

    }

    @Override
    public String lastProjectName() {
        return this.lastProjectName;
    }
}
