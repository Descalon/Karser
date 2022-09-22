package models

import utils.Indices

open class Reference(var name: String, var type: String, val parent: Structure): IModel, INode{
    var isOptional = false
        private set
    var isSingleton = false
        private set

    fun optional() = apply {isOptional = true}
    fun singleton() = apply {isSingleton = true}

    override val id: Int
        get() = parent.getIdForModel(this)
    override val conceptInstance: String
        get() = Indices.Structure.LinkDeclaration.ConceptIndex
    override val role: String
        get() = Indices.Structure.AbstractConceptDeclaration.LinkDeclaration

    operator fun component1() = name
    operator fun component2() = type
}
class ChildReference(name: String, type: String, parent: Structure) : Reference(name, type, parent)
