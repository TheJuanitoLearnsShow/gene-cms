import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class CmsToolTest {

    @BeforeEach
    fun setUp() {
    }

    @Test
    fun convertFile() {
        WordProvider.OutputToFolder(
            "/Users/juanito/Library/CloudStorage/OneDrive-Personal/sources/PersonalBlogContents/Input/blog-entries/Add Sql Server Database Projects To CI.docx",
            "/Users/juanito/Library/CloudStorage/OneDrive-Personal/sources/PersonalBlogContents/Output/blog-entries"
        )
    }
}