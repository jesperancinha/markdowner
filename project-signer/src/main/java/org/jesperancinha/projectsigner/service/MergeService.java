package org.jesperancinha.projectsigner.service;

import org.jesperancinha.projectsigner.model.Paragraphs;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MergeService {
    public String mergeDocumentWithFooterTemplate(String readmeMd, Paragraphs footer) {
        return readmeMd.concat(footer.getAllParagraphs().entrySet().stream().map(stringParagraphEntry ->
            stringParagraphEntry.getKey()
                .concat(stringParagraphEntry.getValue().getTag())
                .concat(System.lineSeparator())
                .concat(stringParagraphEntry.getValue().getText())
                .concat(System.lineSeparator())
        ).collect(Collectors.joining()));
    }
}
