package org.jesperancinha.projectsigner.service;

import org.jesperancinha.projectsigner.model.Paragraph;
import org.jesperancinha.projectsigner.model.Paragraphs;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.lineSeparator;

@Service
public class MergeService {
    public String mergeDocumentWithFooterTemplate(String readmeMd, Paragraphs footer) {
        List<String> paragraphsBuildList = footer.getTags()
                .stream()
                .map(footer::getParagraphByTag)
                .map(Paragraph::toString)
                .collect(Collectors.toList());
       return readmeMd.concat(lineSeparator()).concat(lineSeparator()).concat(String.join(lineSeparator(), paragraphsBuildList));
    }
}
