package writers

import models.Editor
import models.INode
import org.w3c.dom.Document
import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory

class EditorWriter(private val principle: Editor, private val document: Document): IWriter, ElementFactory(document){
    override val documentName: String
        get() = "${principle.parent.name}_Editor.mpsr"

    override fun generateChildren(): List<Element> {
        val childFactory = object: ElementFactory(document){}
        return principle.components.map(childFactory::createFromNode)
    }

    override fun writeToDocument() = document.apply {
        documentElement.appendChild(createFromNode(principle))
        documentElement.normalize()
    }

    companion object Builder {
        fun fromPrinciple(principle: Editor): EditorWriter {
            val template = this::class.java.classLoader.getResource("editor.template")?.file
            val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(template)
            return EditorWriter(principle, document)
        }
    }
}