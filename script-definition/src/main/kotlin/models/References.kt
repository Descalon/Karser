package models

import utils.Indices

open class Reference
internal constructor(
    val parent: Structure,
    val name: String,
    private val type: String,
    val isOptional: Boolean = false,
    val isSingleton: Boolean = false
) : IModel, INode {
    override val id: String
        get() = "R${parent.getIdForModel(this)}"
    override val conceptInstance: String
        get() = Indices.Structure.LinkDeclaration.ConceptIndex
    override val role: String
        get() = Indices.Structure.AbstractConceptDeclaration.LinkDeclaration

    operator fun component1() = name
    operator fun component2() = type
}

class ChildReference(
    parent: Structure,
    name: String,
    type: String,
    isOptional: Boolean = false,
    isSingleton: Boolean = false
) : Reference(parent, name, type, isOptional, isSingleton){
    override val id: String
        get() = "C${parent.getIdForModel(this)}"
}
