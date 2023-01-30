package models.aspects.editor.components

import models.Concept
import models.aspects.CollectionLayout
import models.aspects.IEditorComponent
import utils.Indices

class ComponentCollection(override val id: String, val layout: CollectionLayout, override val components: List<IEditorComponent>):
    IEditorComponent {
    override val conceptInstance: String
        get() = Indices.Editor.CellModelCollection.ConceptIndex
    override val conceptRole: String
        get() = Indices.Editor.BaseEditorComponent.CellModel
}

class StringConstant(override val id: String, private val value: String, override val components: List<IEditorComponent>):
    IEditorComponent {
    override val conceptInstance: String
        get() = Indices.Editor.CellModelConstant.ConceptIndex
    override val conceptRole: String
        get() = Indices.Editor.CellModelCollection.ChildCellModel

    override val defaultProperties: Map<String, String>
        get() = mapOf(
            Indices.Editor.CellModelConstant.Text to value
        )
}

class PropertyReference(override val id: String, val role: Concept.Property, override val components: List<IEditorComponent>) :
    IEditorComponent {
    override val conceptInstance: String
        get() = Indices.Editor.CellModelProperty.ConceptIndex
    override val conceptRole: String
        get() = Indices.Editor.CellModelCollection.ChildCellModel

    override val defaultReferences: List<Map<String, String>>
        get() = listOf(
            mapOf(
                "role" to Indices.Editor.CellModelWithRole.RelationDeclaration,
                "to" to "sidx:${role.id}"
            )
        )
}

class ChildRefNode(override val id: String, val linkDeclaration: Concept.ChildReference, override val components: List<IEditorComponent>):
    IEditorComponent {
    override val conceptInstance: String
        get() = "foo"
    override val conceptRole: String
        get() = "foo"
}

class Ref(override val id: String, val linkDeclaration: Concept.Reference, val role: String, override val components: List<IEditorComponent>):
    IEditorComponent {
    override val conceptInstance: String
        get() = "foo"
    override val conceptRole: String
        get() = "foo"
}

interface IEditorStyleComponent : IEditorComponent {
    override val components: List<IEditorComponent>
        get() = listOf()
}
class IndentLayoutNewLineStyleClassItem(override val id: String) : IEditorStyleComponent {
    override val conceptInstance: String
        get() = "foo"
    override val conceptRole: String
        get() = "foo"
}

class IndentLayoutNewLineChildrenStyleClassItem(override val id: String) : IEditorStyleComponent {
    override val conceptInstance: String
        get() = "foo"
    override val conceptRole: String
        get() = "foo"
}
