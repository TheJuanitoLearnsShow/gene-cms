import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CmsToolTest {

    @Test
    fun convertFile() {
        WordProvider.outputToFolder(
            "/Users/juanito/Library/CloudStorage/OneDrive-Personal/sources/PersonalBlogContents/Input/blog-entries/2023-01-10 Add Sql Server Database Projects To CI.docx",
            "/Users/juanito/Library/CloudStorage/OneDrive-Personal/sources/PersonalBlogContents/Output/blog-entries"
        )
    }
}