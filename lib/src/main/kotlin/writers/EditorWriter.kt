package writers

import models.INode
import models.aspects.CollectionLayout
import models.aspects.Editor
import models.aspects.IEditorComponent
import models.aspects.editor.components.ComponentCollection
import models.aspects.editor.components.LayoutNode
import org.w3c.dom.Document
import org.w3c.dom.Element
import utils.Indices
import javax.xml.parsers.DocumentBuilderFactory

class EditorWriter(private val principle: Editor, private val conceptName: String, document: Document): ElementFactory(document), IWriter {
    override fun generateChildren(): List<Element> {
        val factory = object : EditorComponentFactory(principle.component, document){}
        return listOf(factory.createFromNode(principle.component))
    }
    override fun write(): DocumentWithName {
        document.documentElement.appendChild(createFromNode(principle))
        return DocumentWithName("${conceptName}_Editor.mpsr", document, "editor")
    }
    companion object Builder {
        fun fromPrinciple(principle: Editor, name: String): EditorWriter {
            val template = this::class.java.classLoader.getResource("editor.template")?.file
            val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(template)
            return EditorWriter(principle, name, document)
        }
    }

    open class EditorComponentFactory(private val principle: IEditorComponent, document: Document): ElementFactory(document) {
        override fun generateChildren(): List<Element> = principle.components.map {
            val factory = object : EditorComponentFactory(it, document){}
            factory.createFromNode(it)
        }
    }
}