package org.jesperancinha.parser.markdowner.filter

import lombok.Builder
import lombok.Getter

@Builder
@Getter
class PackageInfo {
    private val fileFilterChain: FileFilterChain? = null
    private val projectName: String? = null
}