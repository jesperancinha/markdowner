package org.jesperancinha.projectsigner.service;

import org.jesperancinha.projectsigner.inteface.NamerService;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Path;

@Service
public class NamerServiceImpl implements NamerService {

    @Override
    public InputStream buildReadmeStream(Path path) {
        return null;
    }
}
