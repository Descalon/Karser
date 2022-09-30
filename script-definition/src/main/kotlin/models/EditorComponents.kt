package models

import utils.Indices
typealias Provider = IEditorComponentCollection

interface IEditorComponent : IModel, INode {
    val parent: Provider

    override val id
        get() = "m${parent.getIdForModel(this)}"
    override val conceptInstance: String
        get() = ""
    override val role: String
        get() = ""

    fun getEditor(): Editor = when(parent) {
        is EditorCellModelCollection -> (parent as EditorCellModelCollection).getEditor()
        else -> parent as Editor
    }
}
class EditorCellModelCollection(override val parent: Provider, val layout: CollectionLayout = CollectionLayout.INDENT): IEditorComponent, IEditorComponentCollection {
    override val components: MutableList<IEditorComponent> = mutableListOf()
}

data class EditorConstant internal constructor(val value: String, override val parent: Provider) : IEditorComponent{
    override val conceptInstance: String
        get() = Indices.Editor.CellModelConstant.ConceptIndex
    override val role: String
        get() = Indices.Editor.CellModelCollection.ChildCellModel

    override val defaultProperties: Map<String, String>
        get() = mapOf(
            Indices.Editor.CellModelConstant.Text to value
        )
}
data class PropertyReferenceEditor internal constructor(val reference: ConceptProperty, override val parent: Provider) : IEditorComponent {
    override val conceptInstance: String
        get() = Indices.Editor.CellModelProperty.ConceptIndex
    override val role: String
        get() = Indices.Editor.CellModelCollection.ChildCellModel

    override val defaultReferences: List<Ref>
        get() = listOf(
            Ref(Indices.Editor.CellModelWithRole.RelationDeclaration, "${Indices.Imports.CurrentStructure}:${reference.id}", reference.key)
        )
}
data class ListDeclarationEditor internal constructor(val reference: String, override val parent: Provider) : IEditorComponent
data class ChildPropertyReferenceEditor internal constructor(val reference: String, val childProperty: String, override val parent: Provider) : IEditorComponent
data class ChildIncludeEditor internal constructor(val reference: String, override val parent: Provider): IEditorComponent
class NewLine internal constructor(override val parent: Provider) : IEditorComponent

enum class CollectionLayout {
    NONE,
    VERTICAL,
    HORIZONTAL,
    INDENT
}