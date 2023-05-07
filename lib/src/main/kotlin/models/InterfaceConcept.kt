package models

import utils.Indices

class InterfaceConcept(
    conceptID: String,
    name: String,
    parent: Language,
    extends: InterfaceConceptReference? = null
) :AbstractConceptClass(conceptID, name, mutableListOf(), parent){
}

class InterfaceConceptReference(override val nodeID: String, reference: InterfaceConcept): Concept.ConceptReference(reference.name, reference), INode {
    override val conceptInstance: String
        get() = Indices.Structure.InterfaceConceptReference.ConceptIndex
    override val conceptRole: String
        get() = Indices.Structure.ConceptDeclaration.Implements

    override val defaultReferences: List<Map<String, String>>
        get () = listOf(
            mapOf(
                "role" to Indices.Structure.InterfaceConceptReference.Intfc,
                "to" to resolveReferenceString(),
                "resolve" to reference.name
            )
        )
    companion object Default {
        fun fromInterface(id: String, reference: InterfaceConcept) =
            InterfaceConceptReference(id, reference)
    }
}
