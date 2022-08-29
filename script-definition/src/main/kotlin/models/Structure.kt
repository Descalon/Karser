package models

class Structure(parent: Concept) : Aspect(parent), IModel {
    internal val properties: ConceptProperties = mutableListOf()
    internal val propertyMap
        get() = properties.associateBy ({it.key},{it.value})
    internal val children: MutableList<ChildReference> = mutableListOf()
    internal val references: MutableList<Reference> = mutableListOf()
    var isRoot = false
        internal set
    var extendsConcept = "BaseConcept"
        internal set
}
