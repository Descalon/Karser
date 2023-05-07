package models.aspects.editor.components

import models.Concept
import models.ConceptProperty
import models.aspects.CollectionLayout
import models.aspects.IEditorComponent
import utils.Indices

private const val CHILD_CELLMODEL = Indices.Editor.CellModelCollection.ChildCellModel


class ComponentCollection(
    override val nodeID: String,
    private val layout: CollectionLayout,
    private val componentList: List<IEditorComponent>
) :
    IEditorComponent {
    override val conceptInstance: String
        get() = Indices.Editor.CellModelCollection.ConceptIndex
    override val conceptRole: String
        get() = Indices.Editor.BaseEditorComponent.CellModel
    override val components: List<IEditorComponent>
        get() = listOf(LayoutNode("$nodeID-layout", layout)) + componentList
}

class LayoutNode(
    override val nodeID: String,
    private val layout: CollectionLayout,
    override val conceptRole: String = Indices.Editor.CellModelCollection.CellLayout
) : IEditorComponent {
    override val components: List<IEditorComponent>
        get() = listOf();
    override val conceptInstance: String
        get() = when (layout) {
            CollectionLayout.INDENT -> Indices.Editor.CellLayoutIndent.ConceptIndex
            CollectionLayout.VERTICAL -> Indices.Editor.CellLayoutVertical.ConceptIndex
            CollectionLayout.HORIZONTAL -> Indices.Editor.CellLayoutHorizontal.ConceptIndex
        }
}

class StringConstant(
    override val nodeID: String,
    private val value: String,
    override val components: List<IEditorComponent>
) :
    IEditorComponent {
    override val conceptInstance: String
        get() = Indices.Editor.CellModelConstant.ConceptIndex
    override val conceptRole: String
        get() = CHILD_CELLMODEL

    override val defaultProperties: Map<String, String>
        get() = mapOf(
            Indices.Editor.CellModelConstant.Text to value
        )
}

open class PropertyReference(
    override val nodeID: String,
    private val role: ConceptProperty,
    override val components: List<IEditorComponent>
) :
    IEditorComponent {
    override val conceptInstance: String
        get() = Indices.Editor.CellModelProperty.ConceptIndex
    override val conceptRole: String
        get() = CHILD_CELLMODEL

    override val defaultReferences: List<Map<String, String>>
        get() = listOf(
            mapOf(
                "role" to Indices.Editor.CellModelWithRole.RelationDeclaration,
                "to" to "sidx:${role.nodeID}", //TODO: Get concept (sidx) reference from parental concept
                "resolve" to role.role
            )
        )
}

class InheritedPropertyReference(id: String, role: ConceptProperty, components: List<IEditorComponent>) :
    PropertyReference(id, role, components){}

class ChildRefNode(
    override val nodeID: String,
    private val linkDeclaration: Concept.ChildReference,
    override val components: List<IEditorComponent>
) :
    IEditorComponent {
    override val conceptInstance: String
        get() =
            if (linkDeclaration.isSingleton)
                Indices.Editor.CellModelRefNodeList.ConceptIndex
            else
                Indices.Editor.CellModelRefNode.ConceptIndex

    override val conceptRole: String
        get() = CHILD_CELLMODEL
}

class Ref(
    override val nodeID: String,
    val linkDeclaration: Concept.Reference,
    val role: String,
    override val components: List<IEditorComponent>
) :
    IEditorComponent {
    override val conceptInstance: String
        get() = "foo"
    override val conceptRole: String
        get() = "foo"
}

interface IEditorStyleComponent : IEditorComponent {
    override val components: List<IEditorComponent>
        get() = listOf()
    override val defaultProperties: Map<String, String>
        get() = mapOf(
            Indices.Editor.BooleanStyleSheetItem.Flag to "true"
        )
}

class IndentLayoutNewLineStyleClassItem(override val nodeID: String) : IEditorStyleComponent {
    override val conceptInstance: String
        get() = Indices.Editor.IndentLayoutNewLineStyleClassItem.ConceptIndex
    override val conceptRole: String
        get() = Indices.Editor.IStyleContainer.StyleItem
}

class IndentLayoutNewLineChildrenStyleClassItem(override val nodeID: String) : IEditorStyleComponent {
    override val conceptInstance: String
        get() = Indices.Editor.IndentLayoutNewLineChildrenStyleClassItem.ConceptIndex
    override val conceptRole: String
        get() = Indices.Editor.IStyleContainer.StyleItem
}
