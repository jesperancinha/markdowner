package org.jesperancinha.parser.markdowner.filter

import lombok.extern.slf4j.Slf4j
import org.apache.logging.log4j.util.Strings
import org.w3c.dom.Document
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import java.io.FileReader
import java.io.IOException
import java.nio.file.Path
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathExpressionException
import javax.xml.xpath.XPathFactory

/**
 * Filter to check if folder contains a Maven project and keeps the project name in memory
 */
@Slf4j
class MavenFilter : ProjectFilter<Path?>() {
    override fun test(path: Path?): Boolean {
        val maybeMavenBuild = path.getFileName().toString() == POM_XML
        try {
            if (maybeMavenBuild) {
                return checkIfPomProjectFileHasName(path)
            }
        } catch (e: Exception) {
            MavenFilter.log.trace("Not a valid Maven file", e)
        }
        return false
    }

    @Throws(
        ParserConfigurationException::class,
        SAXException::class,
        IOException::class,
        XPathExpressionException::class
    )
    private fun checkIfPomProjectFileHasName(path: Path?): Boolean {
        val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val xmlDocument = documentBuilder.parse(InputSource(FileReader(path.toFile())))
        MavenFilter.log.trace("Message is valid XML.")
        var textContent = getString(xmlDocument, "/project/name")
        if (Strings.isEmpty(textContent)) {
            textContent = getString(xmlDocument, "/project/artifactId")
        }
        lastProjectName = textContent
        return !Strings.isEmpty(textContent)
    }

    @Throws(XPathExpressionException::class)
    private fun getString(xmlDocument: Document?, expression: String?): String? {
        val xPath = XPathFactory.newInstance().newXPath()
        val nodeList = xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET) as NodeList
        return if (nodeList.length == 0) {
            null
        } else nodeList.item(0).textContent
    }

    override fun lastProjectName(): String? {
        return lastProjectName
    }

    companion object {
        private val POM_XML: String? = "pom.xml"
    }
}