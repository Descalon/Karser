package models


class Editor(parent: Concept) : Aspect(parent), IModel {
    internal val components: MutableList<IEditorComponent> = mutableListOf();
    var collectionLayout = CollectionLayout.NONE
        internal set
}
