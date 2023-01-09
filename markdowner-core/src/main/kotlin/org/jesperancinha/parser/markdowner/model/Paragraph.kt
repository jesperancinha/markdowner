package org.jesperancinha.parser.markdowner.model

import lombok.Builder
import lombok.Getter

@Getter
@Builder
class Paragraph {
    private val tag: String? = null
    private val text: String? = null
    override fun toString(): String {
        return tag!!
        +System.lineSeparator() + text + System.lineSeparator()
    }
}