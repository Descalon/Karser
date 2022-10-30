package writers

import models.INode
import models.Structure
import org.w3c.dom.Document
import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory

class StructureWriter(private val principle: Structure,  document: Document): IWriter, ElementFactory(document){
    override val documentName: String
        get() = "${principle.parent.name}.mpsr"

    override fun generateChildren(): List<Element> {
        val childFactory = object: ElementFactory(document){}
        val referenceFactory = ReferenceElementFactory(document)
        val childReferenceFactory = ChildReferenceElementFactory(document)
        val p = principle.properties.map { childFactory.createFromNode(it) }
        val r = principle.references.map { referenceFactory.createFromNode(it) }
        val c = principle.children.map { childReferenceFactory.createFromNode(it) }
        return p + r + c
    }

    override fun writeToDocument() = document.apply {
        documentElement.appendChild(createFromNode(principle))
        documentElement.normalize()
    }

    companion object Builder {
        fun fromPrinciple(principle: Structure, modelRef: String = ""): StructureWriter {
            val template = this::class.java.classLoader.getResource("structure.template")?.file
            val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(template).apply {
                documentElement.setAttribute("ref", modelRef)
            }
            return StructureWriter(principle, document)
        }
    }
}