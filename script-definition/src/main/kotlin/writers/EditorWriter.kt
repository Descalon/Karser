package writers

import models.ConceptProperty
import models.Editor
import models.PropertyReferenceEditor
import org.w3c.dom.Document
import org.w3c.dom.Element

class EditorWriter(principle: Editor) : AspectWriter<Editor>(principle){
    override val documentName: String
        get() = "${principle.parent.name}_Editor.mpsr"

    override fun write(): Pair<String, Document> {
        model.appendChild(generateNode())
        return (documentName to document)
    }
    private fun generateNode(): Element {
        val node = document.createElement("node")
        node.setAttribute("concept", ConceptEditorDeclarationIndex)
        node.setAttribute("id", "${principle.parent.name}Editor")
        node.appendChild(ref(document){
            role(AbstractComponentConceptDeclarationIndex)
            to("$StructureImportIndex:c${principle.parent.id}")
            resolve(principle.parent.name)
        })
        principle.components.forEach { when(it) {
            is PropertyReferenceEditor -> node.appendChild(generatePropertyReferenceNode(it.reference))
        } }
        return node
    }

    private fun generatePropertyReferenceNode(property: ConceptProperty): Element {
        val id = property.id
        val node = document.createElement("node")
        node.setAttribute("concept", "3F0A7n")
        node.setAttribute("id", "PropertyEditor${property.key}")
        node.setAttribute("role", EditorCellModelIndex)
        node.appendChild(ref(document){
            role(CellModelWRoleRelationDeclarationIndex)
            to("$StructureImportIndex:p$id")
            resolve(property.key)
        })
        return node
    }
}