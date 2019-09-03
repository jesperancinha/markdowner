package org.jesperancinha.projectsigner.filter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

@Slf4j
public class NPMFilter implements ProjectFilter<File> {
    @Override
    public boolean test(Path path) {
        try {
            JsonElement jsonElement = new Gson().getAdapter(JsonElement.class).fromJson(new FileReader(path.toFile()));
            JsonElement name = jsonElement.getAsJsonObject().get("name");
            return !Objects.isNull(name);
        } catch (IOException e) {
            log.trace("Not a valid JSON!", e);
            return false;
        }

    }
}
