package writers

import models.Aspect
import models.Concept
import models.aspects.Editor
import org.w3c.dom.Document
import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory

data class DocumentWithName(val name:String, val document: Document, val aspect: String)
interface IWriter {
    fun write() : DocumentWithName
}

class ConceptWriter(private val principle: Concept, document: Document): ElementFactory(document), IWriter{

    override fun generateChildren(): List<Element> {
        val builder = object : ElementFactory(document) {}
        return with(principle) { properties + children + references + interfaces}.map(builder::createFromNode)
    }

    private fun resolver(aspect: Aspect) = when(aspect) {
        is Editor ->  EditorWriter.fromPrinciple(aspect, principle.name)
        else -> throw Exception("help")
    }

    override fun write(): DocumentWithName {
        writeForImports()
        document.documentElement.appendChild(createFromNode(principle))
        return DocumentWithName("${principle.name}.mpsr", document, "structure")
    }

    fun writeForAspects() = principle.aspects.map { resolver(it).write() }
    private fun writeForImports(){
        ElementBuilder.build(document, "imports"){
            principle.imports.forEach{ model ->
                addChildNode("import"){
                    attribute("index", model.index)
                    attribute("implicit", "true")
                    attribute("ref", "r:${model.guid}(${model.packageName})")
                }
            }
        }.apply { document.documentElement.appendChild(build()) }
    }

    companion object Builder {
        fun fromPrinciple(principle: Concept): ConceptWriter {
            val template = this::class.java.classLoader.getResource("structure.template")?.file
            val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(template)
            return ConceptWriter(principle, document)
        }
    }
}