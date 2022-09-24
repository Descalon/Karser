package models

import utils.Indices

class Editor(parent: Concept) : Aspect(parent), IModel, IDProvider<IEditorComponent> {
    internal val components: MutableList<IEditorComponent> = mutableListOf()
    var collectionLayout = CollectionLayout.NONE
        internal set

    override val conceptInstance: String =
        Indices.Editor.ConceptEditorDeclaration.ConceptIndex
    override fun getIdForModel(model: IEditorComponent) =
        components.indexOf(model)
}