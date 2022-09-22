package models

import utils.Indices


class Editor(parent: Concept) : Aspect(parent), IModel, INode, IDProvider<IEditorComponent> {
    internal val components: MutableList<IEditorComponent> = mutableListOf();
    var collectionLayout = CollectionLayout.NONE
        internal set

    override val id: Int = parent.getIdForModel(this)
    override val conceptInstance: String =
        Indices.Editor.ConceptEditorDeclaration.ConceptIndex
    override val role: String = ""
    override fun getIdForModel(model: IEditorComponent): Int {
        TODO("Not yet implemented")
    }
}