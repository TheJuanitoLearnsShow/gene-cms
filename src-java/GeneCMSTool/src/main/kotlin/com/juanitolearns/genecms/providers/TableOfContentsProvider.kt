package com.juanitolearns.genecms.providers

import java.io.File
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import java.io.PrintWriter

object TableOfContentsProvider {

    data class FileNode(val name: String, val children: MutableList<FileNode>)

    fun createFileTree(root: File): FileNode {
        val rootNode = FileNode(root.name, mutableListOf())
        val queue = mutableListOf(Pair(root, rootNode))

        while (queue.isNotEmpty()) {
            val (file, parentNode) = queue.removeAt(0)
            file.listFiles()?.forEach { childFile ->
                val childNode = FileNode(childFile.name, mutableListOf())
                parentNode.children.add(childNode)
                if (childFile.isDirectory) {
                    queue.add(Pair(childFile, childNode))
                }
            }
        }

        return rootNode
    }

    data class TableOfContents(val heading: String, val children: MutableList<TableOfContents>,
                               val parent: TableOfContents?) {
        val path: String
            get() {
                return (parent?.path ?: "") + '/' + displayName
            }

        val isPage: Boolean
            get() {
                return heading.startsWith("Page ")
            }

        val displayName: String
            get() {
                return if (isPage) heading.substringAfter(' ') else heading;
            }
    }


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

    fun outputToFolder(inputFolderPath: String): TableOfContents {
        val inputFolder = File(inputFolderPath)
        val tocRoot = TableOfContents(inputFolder.nameWithoutExtension, mutableListOf(), null)
        inputFolder.walk()
            .filter { p -> p.name == "index.html" }
            .forEach { p -> addToToc(tocRoot, p.parent.replace(inputFolderPath, "")) }
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
            entry.children.forEach {
                c -> tocEntryToHtml(c, writer)
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
    fun outputToFolder(inputFolderPath: String, outputFileName: String) {
        val tocRoot = outputToFolder(inputFolderPath)
        outputToHtml(tocRoot, outputFileName)

    }
}