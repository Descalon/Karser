package writers

import models.Concept
import models.ConceptProperty
import org.w3c.dom.Document
import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory

class StructureWriter(private val principle: Concept):IWriter {
    private val document: Document
    private val model: Element
    override val documentName: String
        get() = "${principle.name}.mpsr"

    init {
        val template = this::class.java.classLoader.getResource("structure.template")?.file
        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(template)
        model = document.documentElement
    }

    override fun write(): Pair<String,Document>{
        model.appendChild(generateNode())
        model.normalize()
        return (documentName to document)
    }
    private fun generateNode(): Element {
        val node = document.createElement("node")
        node.setAttribute("concept", ConceptDeclarationIndex)
        node.setAttribute("id", "c${principle.id}")

        node.appendChild(property(document) {
            role(ConceptIdIndex)
            value(principle.name.toMPSIDNumber())
        })
        node.appendChild(property(document) {
            role(INamedConceptNameIndex)
            value(principle.name)
        })
        node.appendChild(ref(document) {
            role(ConceptDeclarationExtendsIndex)
            to("$CoreImportIndex:${DataTypeMap["BaseConcept"]}")
        })
        if(principle.structure.isRoot) {
            node.appendChild(property(document) {
                role(IsRootableIndex)
                value("true")
            })
        }
        principle.structure.properties.forEach { node.appendChild(generatePropertyNode(it)) }
        return node
    }
    private fun generatePropertyNode(property: ConceptProperty): Element {
        val(key,value,id) = property
        val node = this.document.createElement("node")
        node.setAttribute("concept", PropertyDeclarationConceptIndex)
        node.setAttribute("id", "p${id}")
        node.setAttribute("role", AbstractConceptDeclarationPropertyDeclarationIndex)
        node.appendChild(property(document) {
            role(PropertyDeclarationIdIndex)
            value((key + value).toMPSIDNumber())
        })
        node.appendChild(property(document) {
            role(INamedConceptNameIndex)
            value(key)
        })
        node.appendChild(ref(document) {
            role(PropertyDeclarationDataTypeIndex)
            to("$CoreImportIndex:${
                DataTypeMap[value]}")
            resolve(value)
        })
        return node
    }
}