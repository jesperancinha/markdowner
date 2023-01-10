package org.jesperancinha.parser.markdowner.filter

import org.apache.logging.log4j.util.Strings
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.nio.file.Path

/**
 * Filter to check if folder contains a Simple Build Tool project and keeps the project name in memory
 */
class SBTFilter : ProjectFilter<Path>() {
    override fun test(path: Path): Boolean {
        val maybeSBTBuild = path.fileName.toString() == "build.sbt"
        if (maybeSBTBuild) {
            try {
                BufferedReader(FileReader(path.toFile())).use { bufferedReader ->
                    var line: String
                    while (bufferedReader.readLine().also { line = it } != null) {
                        if (hasName(line)) {
                            return true
                        }
                    }
                }
            } catch (e: IOException) {
                logger.trace("Not SBT format!")
            }
        }
        return false
    }

    override fun lastProjectName(): String? {
        return lastProjectName
    }

    private fun hasName(line: String): Boolean {
        val split: Array<String> = line.split("\\s*:=\\s*".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return if (split.size == 2) {
            isNameProperty(split)
        } else false
    }

    private fun isNameProperty(split: Array<String>): Boolean {
        val left = split.get(0).trim { it <= ' ' }
        return if (left == NAME) {
            isValueAString(split.get(1))
        } else false
    }

    private fun isValueAString(s: String): Boolean {
        val right = s.trim { it <= ' ' }
        if (Strings.isNotEmpty(right)) {
            lastProjectName = right.substring(1, right.length - 1)
            return true
        }
        return false
    }

    companion object {
        private val NAME: String = "name"
        val logger = LoggerFactory.getLogger(SBTFilter::class.java)
    }
}