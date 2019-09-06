package org.jesperancinha.parser.projectsigner.inteface;

import org.jesperancinha.parser.markdowner.model.Paragraphs;

import java.io.IOException;
import java.io.InputStream;

public interface TemplateService {
    Paragraphs findAllParagraphs(InputStream templateInputStream) throws IOException;
}
