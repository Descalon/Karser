package models

import utils.DataTypeMap
import utils.Indices
import utils.toMPSIDNumber

class Structure(parent: Concept) : Aspect(parent), IModel, IDProvider<IModel> {
    internal val properties: MutableList<ConceptProperty> = mutableListOf()
    internal val children: MutableList<ChildReference> = mutableListOf()
    internal val references: MutableList<Reference> = mutableListOf()

    var isRoot = false
        internal set
    var extendsConcept = "BaseConcept"
        internal set

    override val conceptInstance: String = Indices.Structure.ConceptDeclaration.ConceptIndex
    override val role: String = ""

    override fun getIdForModel(model: IModel): Int = when(model) {
        is ChildReference -> children.indexOf(model)
        is Reference -> references.indexOf(model)
        is ConceptProperty -> properties.indexOf(model)
        else -> throw IllegalArgumentException("Illegal type ${model::class}. Structure only supports ids for ChildReference, Reference, and ConceptProperty")
    }

    override val defaultProperties
        get() = mapOf(
            Indices.Structure.AbstractConceptDeclaration.ConceptId to (parent.name.toMPSIDNumber()),
            Indices.Core.INamedConcept.Name to parent.name,
            Indices.Structure.ConceptDeclaration.Rootable to "$isRoot"
        )

    override val defaultReferences
        get() = listOf(
            mapOf(
                "role" to Indices.Structure.ConceptDeclaration.Extends,
                "to" to "${Indices.Imports.JetbrainsStructure}:${DataTypeMap["BaseConcept"]}")
        )

    override val childNodes: List<INode> = properties + children + references
    override fun resolveWith(context: IModel) {
        (children + references).forEach{ it.resolveWith(context) }
    }

}