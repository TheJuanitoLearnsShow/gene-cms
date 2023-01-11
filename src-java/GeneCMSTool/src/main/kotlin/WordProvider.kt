import org.zwobble.mammoth.DocumentConverter
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.name
import kotlin.io.path.pathString

object WordProvider {
    fun ConvertFile(inputFilePath: String, outputFilePath: String) {
        val converter =  DocumentConverter()
        val result = converter.convertToHtml( File(inputFilePath));
        val html = result.value; // The generated HTML
        val warnings = result.warnings; // Any warnings during conversion
        val outputFile = File(outputFilePath)
        outputFile.writeText (html)
    }
    fun OutputToFolder(inputFilePath: String, outputBaseFolder: String) {
        val inputPath = Path(inputFilePath)
        val filename = inputPath.fileName.pathString
        val orgFolder = filename.substringBefore(' ')
        val finalFileName = "Page " + filename.substringAfter(' ')

        val outputFolder = Path(outputBaseFolder)
            .resolve(orgFolder)
            .resolve(finalFileName)

        val outputFolderFileEntry = File(outputFolder.pathString)
        if (!outputFolderFileEntry.exists()) {
            outputFolderFileEntry.mkdirs()
        }
        return ConvertFile(inputFilePath, outputFolder.resolve("main.html").pathString)
    }
}