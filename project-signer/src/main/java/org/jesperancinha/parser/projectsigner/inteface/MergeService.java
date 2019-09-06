package org.jesperancinha.parser.projectsigner.inteface;

import org.jesperancinha.parser.markdowner.model.Paragraphs;

public interface MergeService {
    String mergeDocumentWithFooterTemplate(String readmeMd, Paragraphs footer);
}
