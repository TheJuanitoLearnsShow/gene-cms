package com.juanitolearns.genecms.providers

import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.invariantSeparatorsPathString
import kotlin.io.path.walk


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

    data class TableOfContents(val heading: String, val children: MutableList<TableOfContents>)

    private fun addToChildren(currToc: TableOfContents, pathPart: String): TableOfContents {
        val tocEntry = (currToc.children.firstOrNull { t -> t.heading == pathPart })

        return if (tocEntry == null) {
            val newTocEntry = TableOfContents(pathPart, mutableListOf())
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

    fun outputToFolder(inputFolderPath: String, outputFileName: String): TableOfContents {
        val inputFolder = File(inputFolderPath)
        val tocRoot = TableOfContents(inputFolder.nameWithoutExtension, mutableListOf())
        inputFolder.walk()
            .filter { p -> p.name == "index.html" }
            .forEach { p -> addToToc(tocRoot, p.parent.replace(inputFolderPath, "")) }
        return tocRoot

    }
}