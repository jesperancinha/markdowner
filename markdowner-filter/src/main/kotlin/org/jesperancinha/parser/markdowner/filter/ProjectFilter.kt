package org.jesperancinha.parser.markdowner.filter

abstract class ProjectFilter<P> {
    protected var lastProjectName: String? = null
    abstract fun test(path: P?): Boolean
    abstract fun lastProjectName(): String?
}