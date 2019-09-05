package org.jesperancinha.projectsigner.inteface;

import org.jesperancinha.parser.model.Paragraphs;

import java.io.IOException;
import java.io.InputStream;

public interface TemplateService {
    Paragraphs findAllParagraphs(InputStream templateInputStream) throws IOException;
}
