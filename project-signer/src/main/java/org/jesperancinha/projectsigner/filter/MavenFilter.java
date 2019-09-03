package org.jesperancinha.projectsigner.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.FileReader;
import java.nio.file.Path;

@Slf4j
public class MavenFilter implements ProjectFilter<Path> {

    private String lastProjectName;

    @Override
    public boolean test(Path path) {
        try {
            final DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            final Document xmlDocument = documentBuilder.parse(new InputSource(new FileReader(path.toFile())));
            log.trace("Message is valid XML.");
            String textContent = getString(xmlDocument, "/project/name");
            if (Strings.isEmpty(textContent)) {
                textContent = getString(xmlDocument, "/project/artifactId");
            }
            this.lastProjectName = textContent;
            return !Strings.isEmpty(textContent);
        } catch (Exception e) {
            log.trace("Not a valid Maven file", e);
            return false;
        }
    }

    private String getString(Document xmlDocument, String expression) throws XPathExpressionException {
        final XPath xPath = XPathFactory.newInstance().newXPath();
        final NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
        if(nodeList.getLength() == 0){
            return null;
        }
        return nodeList.item(0).getTextContent();
    }

    @Override
    public String lastProjectName() {
        return lastProjectName;
    }
}
