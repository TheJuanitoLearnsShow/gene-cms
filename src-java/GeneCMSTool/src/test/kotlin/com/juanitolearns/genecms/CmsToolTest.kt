package com.juanitolearns.genecms

import com.juanitolearns.genecms.providers.WordProvider
import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.io.path.pathString

class CmsToolTest {

    private val blogContentsFolderPath = Path( System.getenv("PersonalBlogContents") ?: "/")
    @Test
    fun convertFile() {
        WordProvider.outputToFolder(
            blogContentsFolderPath.resolve("Input/blog-entries/2023-01-10 Add Sql Server Database Projects To CI.docx").pathString,
            blogContentsFolderPath.resolve("Output/blog-entries").pathString
        )
    }
}