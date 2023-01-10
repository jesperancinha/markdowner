package org.jesperancinha.parser.markdowner.model

import org.jesperancinha.parser.markdowner.helper.TagHelper

open class Paragraphs(
    private val allParagraphs: MutableMap<String, Paragraph> = mutableMapOf(),
    val tags: MutableList<String> = mutableListOf()
) {
    fun getParagraphCount(): Int = allParagraphs.size

    fun getParagraphByTag(tag: String): Paragraph? = allParagraphs[tag]

    fun withTagParagraph(tag: String?, paragraph: String?) = run {
        val sanitizedTag = TagHelper.sanitizeTag(tag)
        allParagraphs[sanitizedTag] = Paragraph(tag = tag, text = paragraph)
        tags.add(sanitizedTag)
    }
}