package writers

import models.*
import org.w3c.dom.Document
import java.io.File
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import kotlin.reflect.KClass

fun resolver(aspect: Aspect, modelRef: String = ""): IWriter = when (aspect) {
    is Structure -> StructureWriter.fromPrinciple(aspect, modelRef)
    is Editor -> EditorWriter.fromPrinciple(aspect, modelRef)
    else -> throw IllegalArgumentException("things!")
}

class ConceptWriter(private val principle: Concept, private val languageWriter: LanguageWriter) {
    fun write() {
        val languageName = principle.parent?.name ?: ""
        val documents = principle.aspects.map {
            val writer = resolver(it, languageWriter.getAspectUUID(it))
            val aspectName = it::class.simpleName?.lowercase() ?: throw java.lang.IllegalArgumentException("What")
            val documentName = writer.documentName

            "$languageName.$aspectName/$documentName" to writer.writeToDocument()
        }
        val tf = TransformerFactory.newInstance()
        tf.setAttribute("indent-number", 2)
        val transformer: Transformer = tf.newTransformer()

        // ==== Start: Pretty print
        // https://stackoverflow.com/questions/139076/how-to-pretty-print-xml-from-java
        // transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        // ==== End: Pretty print

        documents.forEach {
            val file = File("C:\\users\\nagla\\testdocs\\${it.first}")
            file.parentFile.mkdirs()
            transformer.transform(DOMSource(it.second), StreamResult(file))
        }
    }
}

class LanguageWriter(private val principle: Language){
    fun write() {
        principle.concepts.forEach { ConceptWriter(it, this).write() }

        val tf = TransformerFactory.newInstance()
        tf.setAttribute("indent-number", 2)
        val transformer: Transformer = tf.newTransformer()

        generateModelDocs().forEach {
            val file = File("C:\\users\\nagla\\testdocs\\${it.first}")
            file.parentFile.mkdirs()
            transformer.transform(DOMSource(it.second), StreamResult(file))
        }
    }

    private val typeMap = mutableMapOf<String, String>(
        "editor" to "r:46c8f1ad-1064-4661-839d-2e666a9514a0(CalculatorLanguage.editor)",
        "structure" to "r:a492b1f5-4c1d-4815-89d7-2276a2726b5a(CalculatorLanguage.structure)"
    )
    fun getAspectUUID(aspect: Aspect): String {
        val kt = aspect::class.simpleName?.lowercase() ?: throw IllegalArgumentException("things")
        if(kt !in typeMap){
            typeMap[kt] = "r:${UUID.randomUUID()}(${principle.name}.${kt})"
        }
        return typeMap[kt]!!
    }

    private fun generateModelDocs() = typeMap.map { (k,v) ->
        val template = this::class.java.classLoader.getResource("model.$k.template")?.file
        "${principle.name}.$k/.model" to DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(template).apply {
            documentElement.setAttribute("ref", v)
        }
    }
}