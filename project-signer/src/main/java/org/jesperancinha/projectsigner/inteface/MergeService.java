package org.jesperancinha.projectsigner.inteface;

import org.jesperancinha.parser.model.Paragraphs;

public interface MergeService {
    String mergeDocumentWithFooterTemplate(String readmeMd, Paragraphs footer);
}
