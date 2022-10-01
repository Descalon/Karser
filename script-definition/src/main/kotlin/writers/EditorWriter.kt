package writers

import models.Editor
import models.EditorCellModelCollection
import models.IEditorComponent
import models.INode
import org.w3c.dom.Document
import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory

class CollectionWriter(private val principle: EditorCellModelCollection, private val document: Document): ElementFactory(document) {
    fun resolver(component: IEditorComponent) = when(component) {
        is EditorCellModelCollection -> CollectionWriter(component, document)
        else -> object : ElementFactory(document){}
    }
    override fun generateChildren() = principle.components.map {
        resolver(it).createFromNode(it)
    }

}
class EditorWriter(private val principle: Editor, private val document: Document): IWriter, ElementFactory(document){

    override val documentName: String
        get() = "${principle.parent.name}_Editor.mpsr"

    override fun generateChildren() =
        listOf(CollectionWriter(principle.collection, document).createFromNode(principle.collection))

    override fun writeToDocument() = document.apply {
        val imports = document.getElementsByTagName("imports").item(0)
        writeImports(imports as Element)
        documentElement.appendChild(createFromNode(principle))
        documentElement.normalize()
    }

    private fun writeImports(imports: Element) = imports.apply {
        //val structureImport = document.createElement("import")
        //structureImport.setAttribute("index", "sindx")
        //structureImport.setAttribute("ref", getModelRef(principle.parent.structure))
        //structureImport.setAttribute("implicit", "true")
        //appendChild(structureImport)
    }

    companion object Builder {
        fun fromPrinciple(principle: Editor, modelRef: String = ""): EditorWriter {
            val template = this::class.java.classLoader.getResource("editor.template")?.file
            val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(template).apply {
                documentElement.setAttribute("ref", modelRef)
            }
            return EditorWriter(principle, document)
        }
    }
}