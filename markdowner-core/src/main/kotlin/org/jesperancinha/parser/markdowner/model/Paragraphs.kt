package org.jesperancinha.parser.markdowner.model

import lombok.AccessLevel
import lombok.AllArgsConstructor
import lombok.Getter
import org.jesperancinha.parser.markdowner.helper.TagHelper

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class Paragraphs {
    private val allParagraphs: Map<String, Paragraph>? = null
    private val tags: List<String>? = null
    fun getParagraphCount(): Int {
        return allParagraphs!!.size
    }

    fun getParagraphByTag(tag: String): Paragraph? {
        return allParagraphs!![tag]
    }

    class ParagraphsBuilder {
        private val allParagraphs: MutableMap<String?, Paragraph> = HashMap()
        private val tags: MutableList<String?> = ArrayList()
        fun withTagParagraph(tag: String?, paragraph: String?): ParagraphsBuilder {
            val sanitizedTag = TagHelper.sanitizeTag(tag)
            allParagraphs[sanitizedTag] = Paragraph.builder().tag(tag).text(paragraph).build()
            tags.add(sanitizedTag)
            return this
        }

        fun build(): Paragraphs {
            return Paragraphs(allParagraphs, tags)
        }
    }
}