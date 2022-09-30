package writers

import org.w3c.dom.Document

interface IWriter {
    val documentName: String
    fun writeToDocument(): Document
}