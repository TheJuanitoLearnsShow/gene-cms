package com.juanitolearns.genecms

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import kotlin.io.path.Path

object PageComposer {
    private fun composePage(htmlTemplate:String, pageFolder: File): String {
        val doc = Jsoup.parse(htmlTemplate)
        pageFolder.walk().filter {f -> f.name != "index.html" && !f.isDirectory}.forEach {
                f ->
            addContent(doc, f.nameWithoutExtension, f.readText())
         }
        return doc.outerHtml()
    }
    private fun pageFolderToHtml(htmlTemplate:String, folderPath: File) {
        val finalOutputFile = folderPath.resolve("index.html")
        finalOutputFile.writeText(composePage(htmlTemplate,folderPath))
    }
    fun composePages(htmlTemplateFilePath:String, inputFolderPath: String) {
        val htmlTemplate = File(htmlTemplateFilePath).readText()
        val pageFolders = File(inputFolderPath).walk().filter {
            f -> f.isDirectory && f.name.startsWith("Page ")
        }
        pageFolders.forEach { pageFolder -> pageFolderToHtml(htmlTemplate, pageFolder)}
    }

    private fun addContent(doc: Document, htmlId: String, html: String) {
        doc.getElementById(htmlId)?.html(html)
    }
}