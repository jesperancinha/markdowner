package org.jesperancinha.parser.markdowner.filter

import com.google.gson.Gson
import com.google.gson.JsonElement
import org.slf4j.LoggerFactory
import java.io.FileReader
import java.nio.file.Path
import java.util.*

/**
 * Filter to check if folder contains a Node Package Manager project and keeps the project name in memory
 */
class NPMFilter : ProjectFilter<Path>() {
    override fun test(path: Path): Boolean {
        val maybeNPMBuild = path.fileName.toString() == PACKAGE_JSON
        try {
            if (maybeNPMBuild) {
                val jsonElement = Gson().getAdapter(JsonElement::class.java).fromJson(FileReader(path.toFile()))
                val name = jsonElement.asJsonObject[NAME]
                if (Objects.nonNull(name)) {
                    lastProjectName = name.asString
                    return true
                }
            }
        } catch (e: Exception) {
            logger.trace("Not a valid JSON!", e)
        }
        return false
    }

    override fun lastProjectName(): String? {
        return lastProjectName
    }

    companion object {
        private val NAME: String = "name"
        private val PACKAGE_JSON: String = "package.json"
        private val logger = LoggerFactory.getLogger(NPMFilter::class.java)
    }
}