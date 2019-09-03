package org.jesperancinha.projectsigner.inteface;

import org.jesperancinha.projectsigner.model.Paragraphs;

public interface MergeService {
    String mergeDocumentWithFooterTemplate(String readmeMd, Paragraphs footer);
}
