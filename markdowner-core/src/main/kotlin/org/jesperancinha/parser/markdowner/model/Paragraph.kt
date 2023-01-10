package org.jesperancinha.parser.markdowner.model

data class Paragraph(
    val tag: String? = null,
    internal val text: String? = null
) {
    override fun toString(): String {
        return tag!!+System.lineSeparator() + text + System.lineSeparator()
    }
}