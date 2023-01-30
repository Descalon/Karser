package writers

import models.Language
import java.io.File
import java.util.*
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class LanguageWriter(private val principle:Language) {
    private val typeMap = mutableMapOf(
        "editor" to "r:46c8f1ad-1064-4661-839d-2e666a9514a0(CalculatorLanguage.editor)",
        "structure" to "r:a492b1f5-4c1d-4815-89d7-2276a2726b5a(CalculatorLanguage.structure)"
    )
    private fun getAspectUUID(kt: String): String {
        if(kt !in typeMap){
            typeMap[kt] = "r:${UUID.randomUUID()}(${principle.name}.$kt)"
        }
        return typeMap[kt]!!
    }

    fun createDocuments() = principle.concepts.map { concept ->
        val writer = ConceptWriter.fromPrinciple(concept)
        (writer.writeForAspects() + listOf(writer.write())).onEach {
            it.document.documentElement.setAttribute("ref", getAspectUUID(it.aspect))
        }
    }.flatten()

    fun save(rootFolder: String){
        val tf = TransformerFactory.newInstance()
        tf.setAttribute("indent-number", 2)
        val transformer: Transformer = tf.newTransformer()

        // ==== Start: Pretty print
        // https://stackoverflow.com/questions/139076/how-to-pretty-print-xml-from-java
        // transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        // ==== End: Pretty print

        createDocuments().forEach {
            val file = File("$rootFolder/${principle.name}.${it.aspect}/${it.name}")
            file.parentFile.mkdirs()
            transformer.transform(DOMSource(it.document), StreamResult(file))
        }
    }
}