package org.jesperancinha.parser.markdowner.filter

import com.google.gson.Gson
import com.google.gson.JsonElement
import lombok.extern.slf4j.Slf4j
import java.io.FileReader
import java.nio.file.Path
import java.util.*

/**
 * Filter to check if folder contains a Node Package Manager project and keeps the project name in memory
 */
@Slf4j
class NPMFilter : ProjectFilter<Path?>() {
    override fun test(path: Path?): Boolean {
        val maybeNPMBuild = path.getFileName().toString() == PACKAGE_JSON
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
            NPMFilter.log.trace("Not a valid JSON!", e)
        }
        return false
    }

    override fun lastProjectName(): String? {
        return lastProjectName
    }

    companion object {
        private val NAME: String? = "name"
        private val PACKAGE_JSON: String? = "package.json"
    }
}