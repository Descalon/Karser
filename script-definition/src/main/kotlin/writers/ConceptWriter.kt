package writers

import models.Concept
import models.Language
import utils.resolver
import java.io.File
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class ConceptWriter(private val principle: Concept) {
    fun write(){
        val documents = principle.aspects.map { resolver(it).write() }

        val transformer: Transformer = TransformerFactory.newInstance().newTransformer()

        // ==== Start: Pretty print
        // https://stackoverflow.com/questions/139076/how-to-pretty-print-xml-from-java
        // transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        // transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
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