package models

import utils.Indices

interface IEditorComponentCollection: IDProvider<IEditorComponent> {
    val components: MutableList<IEditorComponent>

    override fun getIdForModel(model: IEditorComponent) =
        components.indexOf(model)
}

class Editor internal constructor(parent: Concept) : Aspect(parent), IModel, IEditorComponentCollection {
    override val conceptInstance: String =
        Indices.Editor.ConceptEditorDeclaration.ConceptIndex
    override val components: MutableList<IEditorComponent> = mutableListOf()
    val collection
        get() = components.first() as EditorCellModelCollection

    override val defaultReferences: List<Ref>
        get() = listOf(
            mapOf(
                "role" to Indices.Editor.AbstractComponent.ConceptDeclaration,
                "to" to "sidx:${parent.structure.id}",
                "resolve" to parent.name)
        )
}