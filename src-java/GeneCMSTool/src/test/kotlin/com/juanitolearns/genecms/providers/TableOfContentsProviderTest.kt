package com.juanitolearns.genecms.providers

import org.junit.jupiter.api.Test

import kotlin.io.path.Path
import kotlin.io.path.pathString

class TableOfContentsProviderTest {
    private val blogContentsFolderPath = Path( System.getenv("PersonalBlogContents") ?: "/")
    @Test
    fun outputToFolder() {
        TableOfContentsProvider.mapToHtmlFile(
            blogContentsFolderPath.resolve("Output/blog-entries").pathString,
            blogContentsFolderPath.resolve("Output/blog-entries/toc.html").pathString)
    }
}