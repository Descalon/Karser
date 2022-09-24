package models

import utils.DataTypeMap
import utils.Indices
import utils.toMPSIDNumber

data class ConceptProperty(val key: String, val value: String, val parent: Structure): IModel, INode{
    override val id: Int
        get() = parent.getIdForModel(this)

    override val conceptInstance: String =
        Indices.Structure.PropertyDeclaration.ConceptIndex
    override val role: String =
        Indices.Structure.AbstractConceptDeclaration.PropertyDeclaration

    override val defaultProperties: Map<String, String>
        get() = mapOf(
            Indices.Structure.PropertyDeclaration.Id to (key + value).toMPSIDNumber(),
            Indices.Core.INamedConceptIndex.ConceptIndex to key
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
        get() = parent.getIdForModel(this)
    override val conceptInstance: String
        get() = ""
    override val role: String
        get() = ""
}
data class EditorConstant(val value: String, override val parent: Editor) : IEditorComponent
data class PropertyReferenceEditor(val reference: ConceptProperty, override val parent: Editor) : IEditorComponent
data class ListDeclarationEditor(val reference: String, override val parent: Editor) : IEditorComponent
data class ChildPropertyReferenceEditor(val reference: String, val childProperty: String, override val parent: Editor) : IEditorComponent
data class ChildIncludeEditor(val reference: String, override val parent: Editor): IEditorComponent
class NewLine(override val parent: Editor) : IEditorComponent

enum class CollectionLayout {
    NONE,
    VERTICAL,
    HORIZONTAL
}
