package models

import utils.*

class Structure(parent: Concept) : Aspect(parent), IModel, INode, IDProvider<IModel> {
    internal val properties: ConceptProperties = mutableListOf()
    internal val children: MutableList<ChildReference> = mutableListOf()
    internal val references: MutableList<Reference> = mutableListOf()

    internal val propertyMap
        get() = properties.associateBy ({it.key},{it.value})
    var isRoot = false
        internal set
    var extendsConcept = "BaseConcept"
        internal set

    override val id: Int
        get() = parent.getIdForModel(this)
    override val conceptInstance: String = Indices.Structure.ConceptDeclaration.ConceptIndex
    override val role: String = ""

    override fun getIdForModel(model: IModel): Int = when(model) {
        is ChildReference -> children.indexOf(model)
        is Reference -> references.indexOf(model)
        is ConceptProperty -> properties.indexOf(model)
        else -> references.indexOf(model)
    }

    override val defaultProperties = mapOf(
        Indices.Structure.AbstractConceptDeclaration.ConceptId to (parent.name.toMPSIDNumber()),
        Indices.Core.INamedConceptIndex.ConceptIndex to parent.name,
        Indices.Structure.ConceptDeclaration.Rootable to "$isRoot"
    )

    override val defaultReferences = listOf(
        Ref(Indices.Structure.ConceptDeclaration.Extends, "${Indices.Imports.JetbrainsStructure}:${DataTypeMap["BaseConcept"]}", "")
    )

    override val childNodes: List<INode> = properties + children + references

}
