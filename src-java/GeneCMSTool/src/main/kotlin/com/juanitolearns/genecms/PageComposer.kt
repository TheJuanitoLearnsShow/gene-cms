package com.juanitolearns.genecms

import com.juanitolearns.genecms.providers.TableOfContents
import com.juanitolearns.genecms.providers.TableOfContentsProvider
import kotlinx.html.TABLE
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.pathString

class PageComposer {

    private fun composeTemplate(htmlTemplate:String, toc: String): String {
        val doc = Jsoup.parse(htmlTemplate)
        doc.getElementById("toc")?.html(toc)
        return doc.outerHtml()
    }
    private fun composePage(htmlTemplate:String, pageFolder: File): String {
        val doc = Jsoup.parse(htmlTemplate)
        pageFolder.walk().filter {f -> f.name != "index.html" && !f.isDirectory}.forEach {
                f ->
            addContent(doc, f.nameWithoutExtension, f.readText())
         }
        return doc.outerHtml()
    }
    private fun pageFolderToHtml(htmlTemplate:String, toc: TableOfContents, inputBaseFolderPath: File, distBaseFolderPath: File) {

        val outputFolderFileEntry = File(distBaseFolderPath.resolve(toc.path.substringAfter('/')).path)
        if (!outputFolderFileEntry.exists()) {
            outputFolderFileEntry.mkdirs()
        }

        val inputPageFolderPath = inputBaseFolderPath.resolve(toc.inputPath.substringAfter('/'))
        outputFolderFileEntry.resolve("index.html").writeText(composePage(htmlTemplate,inputPageFolderPath))
    }
    fun composePages(htmlTemplateFilePath:String, inputFolderPath: String, distFolderPath: String
                     ) {
        val htmlTemplate = File(htmlTemplateFilePath).readText()
        val toc = TableOfContentsProvider.mapToToc(inputFolderPath)
//        val pageFolders = File(inputFolderPath).walk().filter {
//            f -> f.isDirectory && f.name.startsWith("Page ")
//        }
        val pageEntries = toc.collectPages()
//        val tocRootDisplay = toc.cloneEntry("blog-entries", "Blog Entries")?.collectPages() ?: Lis()
        val finalTemplate = composeTemplate(htmlTemplate, TableOfContentsProvider.mapToHtmlString(toc))
        pageEntries.forEach { tocEntry -> pageFolderToHtml(finalTemplate,  tocEntry,
            File(inputFolderPath), File(distFolderPath))}
    }

    private fun addContent(doc: Document, htmlId: String, html: String) {
        doc.getElementById(htmlId)?.html(html)
    }
}