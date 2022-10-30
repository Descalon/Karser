package models

import utils.Indices
import utils.toMPSIDNumber


typealias LinkDeclaration = Indices.Structure.LinkDeclaration

open class Reference
internal constructor(
    val parent: Structure,
    val name: String,
    val type: String,
    val isOptional: Boolean = false,
) : IModel, INode {
    protected var principle: Concept? = null
    override fun resolveWith(context: IModel){
        if (context !is IResolver) return
        context.resolveFor(this)?.also { principle = it as Concept }
            ?: throw Exception("can't find context for reference $name")
    }

    // 0..1
    // 0..n
    // 1
    // 1..n
    protected open fun cardinalityString() = if(isOptional) "_0__1" else "_1"
    operator fun component1() = name
    operator fun component2() = type
    override val id: String
        get() = "R${parent.getIdForModel(this)}"
    override val conceptInstance: String
        get() = Indices.Structure.LinkDeclaration.ConceptIndex
    override val role: String
        get() = Indices.Structure.AbstractConceptDeclaration.LinkDeclaration
    override val defaultProperties: Map<String, String>
        get() = mapOf(
            LinkDeclaration.Role to name,
            LinkDeclaration.LinkId to "$name$type${parent.parent.name}".toMPSIDNumber(),
        )
    override val defaultReferences: List<Ref>
        get() {
            val principleId = principle?.structure?.id ?: throw Exception("Unresolved principle. Did you call resolve(context: Language)?")
            val principleName = principle?.name ?: throw Exception("Unresolved principle. Did you call resolve(context: Language)?")
            return listOf(
                mapOf(
                    "role" to LinkDeclaration.Target,
                    "node" to principleId,
                    "resolve" to principleName
                )
            )
        }
}

class ChildReference(
    parent: Structure,
    name: String,
    type: String,
    isOptional: Boolean = false,
    val isSingleton: Boolean = true
) : Reference(parent, name, type, isOptional){
    override val id: String
        get() = "C${parent.getIdForModel(this)}"

    override fun cardinalityString() = when {
        this.isSingleton && this.isOptional     -> "_0__1"
        this.isOptional && !this.isSingleton    -> "fLJekj5/_0__n"
        !this.isOptional && this.isSingleton    -> "fLJekj4/_1"
        else                                    -> "fLJekj6/_1__n"
    }
    override val defaultProperties: Map<String, String>
        get() = super.defaultProperties + (LinkDeclaration.MetaClass to "fLJjDmT/aggregation")
}
