package writers

import models.Aspect
import models.INode
import org.w3c.dom.Document
import org.w3c.dom.Element
import utils.element
import javax.xml.parsers.DocumentBuilderFactory

interface IWriter {
    val documentName: String
    fun write(): Pair<String,Document>
}
abstract class AspectWriter<T>(val principle: T): IWriter where T: Aspect, T: INode {
    private val document: Document
    private val model: Element

    init {
        val name = principle::class.simpleName!!.lowercase()
        val template = this::class.java.classLoader.getResource("$name.template")?.file
        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(template)
        model = document.documentElement
    }
    override fun write(): Pair<String, Document> {
        model.appendChild(createFromNode())
        model.normalize()
        return (documentName to document)
    }
    protected abstract fun generateChildren(): List<Element>
    private fun createFromNode(): Element = element(document) {
        attribute("concept", principle.conceptInstance)
        attribute("id", "${principle.id}")
        if(principle.role.isNotEmpty())
            attribute("role", principle.role)

        principle.defaultProperties.forEach { (k, v) ->
            property {
                attribute(k,v)
            }
        }
        principle.defaultReferences.forEach {
            property {
                attribute("to", it.to)
                attribute("role", it.role)
                if(it.resolve.isNotEmpty())
                    attribute("resolve", it.resolve)
            }
        }
        generateChildren().forEach {
            child(it)
        }
    }
}
