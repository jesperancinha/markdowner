package org.jesperancinha.parser.markdowner.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.jesperancinha.parser.markdowner.parser.ProjectFilter;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Filter to check if folder contains a Maven project and keeps the project name in memory
 */
@Slf4j
public class MavenFilter extends ProjectFilter<Path> {

    private static final String POM_XML = "pom.xml";

    @Override
    public boolean test(Path path) {
        boolean maybeMavenBuild = path.getFileName().toString().equals(POM_XML);
        try {
            if (maybeMavenBuild) {
                return checkIfPomProjectFileHasName(path);
            }
        } catch (Exception e) {
            log.trace("Not a valid Maven file", e);
        }
        return false;
    }

    private boolean checkIfPomProjectFileHasName(Path path) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        final DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        final Document xmlDocument = documentBuilder.parse(new InputSource(new FileReader(path.toFile())));
        log.trace("Message is valid XML.");
        String textContent = getString(xmlDocument, "/project/name");
        if (Strings.isEmpty(textContent)) {
            textContent = getString(xmlDocument, "/project/artifactId");
        }
        this.lastProjectName = textContent;
        return !Strings.isEmpty(textContent);
    }

    private String getString(Document xmlDocument, String expression) throws XPathExpressionException {
        final XPath xPath = XPathFactory.newInstance().newXPath();
        final NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
        if (nodeList.getLength() == 0) {
            return null;
        }
        return nodeList.item(0).getTextContent();
    }

    @Override
    public String lastProjectName() {
        return lastProjectName;
    }
}
