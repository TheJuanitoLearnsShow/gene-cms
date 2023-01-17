package com.juanitolearns.genecms.providers

import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.invariantSeparatorsPathString
import kotlin.io.path.walk


object TableOfContentsProvider {

fun outputToFolder(inputFolderPath: String, outputFileName: String) {
    val pagesToIndex = File(inputFolderPath).walk()
        .filter {p -> p.name == "index.html"}
        .map { p -> p.name.split(Path.invariantSeparatorsPathString) }
        .groupBy {  }

}
}