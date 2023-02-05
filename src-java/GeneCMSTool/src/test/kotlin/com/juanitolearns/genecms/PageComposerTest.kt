package com.juanitolearns.genecms

import com.juanitolearns.genecms.providers.WordProvider
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.io.path.Path
import kotlin.io.path.pathString

class PageComposerTest {
    private val blogContentsFolderPath = Path( System.getenv("PersonalBlogContents") ?: "/")
    @Test
    fun composePages() {
        // TODO: create fun for word provider to walk folder
        WordProvider.outputToFolder(
            blogContentsFolderPath.resolve("Input/Blog/2023-01-10 Add Sql Server Database Projects To CI.docx").pathString,
            blogContentsFolderPath.resolve("Output/Blog").pathString
        )
        // TODO: create static assets provider
        PageComposer().composePages(blogContentsFolderPath.resolve( "Templates/doc.html").pathString,
            blogContentsFolderPath.resolve("Output/").pathString,
            blogContentsFolderPath.resolve("Dist/").pathString

            )
    }
}