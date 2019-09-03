package org.jesperancinha.projectsigner.filter;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.Objects;

@Slf4j
public class MavenFilter implements ProjectFilter<Path> {

    @Override
    public boolean test(Path path) {
        try {
            final DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            final Document xmlDocument = documentBuilder.parse(new InputSource(new FileReader(path.toFile())));
            log.trace("Message is valid XML.");
            final XPath xPath = XPathFactory.newInstance().newXPath();
            final String expression = "/project/artifactId";
            final NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
            final String textContent = nodeList.item(0).getTextContent();
            return Objects.nonNull(textContent);
        } catch (Exception e) {
            log.trace("Not a valid XML", e);
            return false;
        }
    }
}
