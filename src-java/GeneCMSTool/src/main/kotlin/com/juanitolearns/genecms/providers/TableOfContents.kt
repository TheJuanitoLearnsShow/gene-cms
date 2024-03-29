package com.juanitolearns.genecms.providers

data class TableOfContents(val heading: String, val children: MutableList<TableOfContents>,
                           val parent: TableOfContents?) {
    val path: String
        get() {
            return if (parent == null) {
                ""
            } else {
                parent.path + '/' + displayName
            }
        }
    val inputPath: String
        get() {
            return if (parent == null) {
                ""
            } else {
                parent.inputPath + '/' + heading
            }
        }
    val isPage: Boolean
        get() {
            return heading.startsWith("Page ")
        }

    val displayName: String
        get() {
            return if (isPage) heading.substringAfter(' ') else heading;
        }

    fun collectPages(): List<TableOfContents> {
        val result: MutableList<TableOfContents> = mutableListOf()
        if (isPage) {
            result.add(this)
        }
        for (list in children) {
            result.addAll(list.collectPages())
        }
        return result
    }
    fun cloneEntry(heading: String, newHeading: String): TableOfContents? {
        val result: MutableList<TableOfContents> = mutableListOf()
        if (this.heading == heading) {
            return TableOfContents(newHeading, this.children, null)
        }
        for (child in children) {
            val foundInChild = child.cloneEntry(heading, newHeading)
            if (foundInChild != null) return foundInChild
        }
        return null
    }
}