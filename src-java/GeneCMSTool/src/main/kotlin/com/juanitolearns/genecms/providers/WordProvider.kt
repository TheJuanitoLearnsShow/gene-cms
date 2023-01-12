package com.juanitolearns.genecms.providers

import org.zwobble.mammoth.DocumentConverter
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.pathString

object WordProvider {
    fun convertFile(inputFilePath: String, outputFilePath: String) {
        val converter =  DocumentConverter()
            .addStyleMap("p[style-name='Code'] => pre:separator('\\n')")
        val result = converter.convertToHtml( File(inputFilePath));
        val html = result.value; // The generated HTML
        val warnings = result.warnings; // Any warnings during conversion
        val outputFile = File(outputFilePath)
        outputFile.writeText (html)
    }
    fun outputToFolder(inputFilePath: String, outputBaseFolder: String) {
        val inputPath = Path(inputFilePath)
        val filename = inputPath.nameWithoutExtension
        val orgFolder = filename.substringBefore(' ')
        val finalFileName = "Page " + filename.substringAfter(' ')

        val outputFolder = Path(outputBaseFolder)
            .resolve(orgFolder)
            .resolve(finalFileName)

        val outputFolderFileEntry = File(outputFolder.pathString)
        if (!outputFolderFileEntry.exists()) {
            outputFolderFileEntry.mkdirs()
        }
        return convertFile(inputFilePath, outputFolder.resolve("main.html").pathString)
    }
}