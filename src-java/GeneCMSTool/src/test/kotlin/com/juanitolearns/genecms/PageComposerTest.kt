package com.juanitolearns.genecms

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.io.path.Path
import kotlin.io.path.pathString

class PageComposerTest {
    private val blogContentsFolderPath = Path( System.getenv("PersonalBlogContents") ?: "/")
    @Test
    fun composePages() {
        PageComposer().composePages(blogContentsFolderPath.resolve( "Templates/doc.html").pathString,
            blogContentsFolderPath.resolve("Output/").pathString,
            blogContentsFolderPath.resolve("Dist/").pathString

            )
    }
}