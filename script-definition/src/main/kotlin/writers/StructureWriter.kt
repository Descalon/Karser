package writers

import models.INode
import models.Structure
import org.w3c.dom.Document
import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory

class StructureWriter(private val principle: Structure, private val document: Document): IWriter, ElementFactory(document){
    override val documentName: String
        get() = "${principle.parent.name}.mpsr"

    override fun generateChildren(): List<Element> {
        val childFactory = object: ElementFactory(document){}
        fun List<INode>.mapList() = this.map { childFactory.createFromNode(it)}
        return with(principle){ properties + children + references }.mapList()
    }

    override fun writeToDocument() = document.apply {
        documentElement.appendChild(createFromNode(principle))
        documentElement.normalize()
    }

    companion object Builder {
        fun fromPrinciple(principle: Structure): StructureWriter {
            val template = this::class.java.classLoader.getResource("structure.template")?.file
            val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(template)
            return StructureWriter(principle,document)
        }
    }
}