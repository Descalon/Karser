package models

import utils.DataTypeMap
import utils.Indices
import utils.toMPSIDNumber

data class ConceptProperty internal constructor(val key: String, val value: String, val parent: Structure): IModel, INode{
    override val id: String
        get() = "P${parent.getIdForModel(this)}"

    override val conceptInstance: String =
        Indices.Structure.PropertyDeclaration.ConceptIndex
    override val role: String =
        Indices.Structure.AbstractConceptDeclaration.PropertyDeclaration

    override val defaultProperties: Map<String, String>
        get() = mapOf(
            Indices.Structure.PropertyDeclaration.Id to (key + value).toMPSIDNumber(),
            Indices.Core.INamedConcept.Name to key
        )
    override val defaultReferences: List<Ref>
        get() = listOf(
            Ref(Indices.Structure.PropertyDeclaration.DataType, "${Indices.Imports.JetbrainsStructure}:${DataTypeMap[value]}", value)
        )
}

typealias ConceptProperties = MutableList<ConceptProperty>

interface IEditorComponent : IModel, INode {
    val parent: Editor

    override val id
        get() = "Ecp${parent.getIdForModel(this)}"
    override val conceptInstance: String
        get() = ""
    override val role: String
        get() = ""
}
data class EditorConstant internal constructor(val value: String, override val parent: Editor) : IEditorComponent{
    override val conceptInstance: String
        get() = Indices.Editor.CellModelConstant.ConceptIndex
    override val role: String
        get() = Indices.Editor.CellModelCollection.ChildCellModel

    override val defaultProperties: Map<String, String>
        get() = mapOf(
            Indices.Editor.CellModelConstant.Text to value
        )
}
data class PropertyReferenceEditor internal constructor(val reference: ConceptProperty, override val parent: Editor) : IEditorComponent {
    override val conceptInstance: String
        get() = Indices.Editor.CellModelProperty.ConceptIndex
    override val role: String
        get() = Indices.Editor.CellModelCollection.ChildCellModel

    override val defaultReferences: List<Ref>
        get() = listOf(
            Ref(Indices.Editor.CellModelWithRole.RelationDeclaration, "${Indices.Imports.CurrentStructure}:${reference.id}", reference.key)
        )
}
data class ListDeclarationEditor internal constructor(val reference: String, override val parent: Editor) : IEditorComponent
data class ChildPropertyReferenceEditor internal constructor(val reference: String, val childProperty: String, override val parent: Editor) : IEditorComponent
data class ChildIncludeEditor internal constructor(val reference: String, override val parent: Editor): IEditorComponent
class NewLine internal constructor(override val parent: Editor) : IEditorComponent

enum class CollectionLayout {
    NONE,
    VERTICAL,
    HORIZONTAL
}
