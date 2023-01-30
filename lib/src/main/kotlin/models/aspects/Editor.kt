package models.aspects

import models.INode
import utils.Indices
import models.Aspect
import models.Concept

interface IEditorComponent: INode{
    val components: List<IEditorComponent>
}
enum class CollectionLayout {
    INDENT,
    VERTICAL,
    HORIZONTAL
}
class Editor internal constructor(override val id: String, val component: IEditorComponent, parent: Concept): Aspect(parent), INode {
    override val conceptInstance: String =
        Indices.Editor.ConceptEditorDeclaration.ConceptIndex

    override val defaultReferences: List<Map<String, String>>
        get() = listOf(
            mapOf(
                "role" to Indices.Editor.AbstractComponent.ConceptDeclaration,
                "to" to "sidx:${parent.id}",
                "resolve" to parent.name
            )
        )
}

