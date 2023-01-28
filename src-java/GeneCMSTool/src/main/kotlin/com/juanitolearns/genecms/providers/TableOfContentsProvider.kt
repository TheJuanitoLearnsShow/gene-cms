package com.juanitolearns.genecms.providers

import kotlinx.html.TagConsumer
import kotlinx.html.a
import kotlinx.html.li
import kotlinx.html.stream.appendHTML
import kotlinx.html.stream.createHTML
import kotlinx.html.ul
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter


object TableOfContentsProvider {


    private fun addToChildren(currToc: TableOfContents, pathPart: String): TableOfContents {
        val tocEntry = (currToc.children.firstOrNull { t -> t.heading == pathPart })

        return if (tocEntry == null) {
            val newTocEntry = TableOfContents(pathPart, mutableListOf(), currToc)
            currToc.children.add(newTocEntry)
            newTocEntry
        } else
            tocEntry;
    }

    private fun addToToc(toc: TableOfContents, path: String): TableOfContents {
        path.split('/')
            .filter { p -> p != "" }
            .fold(toc) { currToc, p -> addToChildren(currToc, p) }
        return toc
    }

    fun mapToToc(inputFolderPath: String): TableOfContents {
        val inputFolder = File(inputFolderPath)
        val tocRoot = TableOfContents(inputFolder.nameWithoutExtension, mutableListOf(), null)
        inputFolder.walk()
            .filter { p -> p.name.startsWith("Page ") }
            .forEach { p -> addToToc(tocRoot, p.path.replace(inputFolderPath, "")) }
        return tocRoot

    }
    private fun tocEntryToHtml(entry: TableOfContents, writer: TagConsumer<PrintWriter>) {
        writer.li {
            if (entry.isPage) {
                a(entry.path) {
                    text(entry.displayName)
                }
            } else {
                text(entry.displayName)
            }
            writer.ul {
                entry.children.forEach { c ->
                    tocEntryToHtml(c, writer)
                }
            }
        }
    }
    private fun outputToHtml(toc: TableOfContents, outputFileName: String){
        val writer = File(outputFileName).printWriter()
        val htmlWriter = writer.appendHTML()
        htmlWriter.ul {
            tocEntryToHtml(toc, htmlWriter)
        }
        writer.close()
    }
    fun mapToHtmlFile(inputFolderPath: String, outputFileName: String) {
        val tocRoot = mapToToc(inputFolderPath)
        outputToHtml(tocRoot, outputFileName)
    }
    fun mapToHtmlString(toc: TableOfContents) : String {
        val strWriter = StringWriter()
        val writer = PrintWriter(strWriter).appendHTML()
                tocEntryToHtml(toc, writer)
        strWriter.flush()
        return strWriter.toString()
    }
}