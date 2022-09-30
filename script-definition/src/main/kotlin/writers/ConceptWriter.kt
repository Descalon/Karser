package writers

import models.*
import java.io.File
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

fun resolver(aspect: Aspect): IWriter = when (aspect) {
    is Structure -> StructureWriter.fromPrinciple(aspect)
    is Editor -> EditorWriter.fromPrinciple(aspect)
    else -> throw IllegalArgumentException("things!")
}

class ConceptWriter(private val principle: Concept) {
    fun write() {
        val documents = principle.aspects.map {
            val writer = resolver(it)
            writer.documentName to writer.writeToDocument()
        }
        val tf = TransformerFactory.newInstance()
        tf.setAttribute("indent-number", 2)
        val transformer: Transformer = tf.newTransformer()

        // ==== Start: Pretty print
        // https://stackoverflow.com/questions/139076/how-to-pretty-print-xml-from-java
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        // ==== End: Pretty print

        documents.forEach {
            transformer.transform(DOMSource(it.second), StreamResult(File("C:\\users\\nagla\\testdocs\\${it.first}")))
        }
    }
}

class LanguageWriter(private val principle: Language) {
    fun write() {
        principle.concepts.forEach { ConceptWriter(it).write() }
    }
}