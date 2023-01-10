package org.jesperancinha.parser.markdowner.model

data class Paragraph(
    val tag: String? = null,
    val text: String? = null
) {
    override fun toString(): String = "$tag${System.lineSeparator()}${text}${System.lineSeparator()}"
}