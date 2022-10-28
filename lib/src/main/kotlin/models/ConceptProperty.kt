package models

import utils.DataTypeMap
import utils.Indices
import utils.toMPSIDNumber

data class ConceptProperty internal constructor(val key: String, val value: String, val parent: Structure): IModel, INode{
    override val id: String
        get() = "P${parent.getIdForModel(this)}"

    override val conceptInstance: String =
        Indices.Structure.PropertyDeclaration.ConceptIndex
    override val role: String =
        Indices.Structure.AbstractConceptDeclaration.PropertyDeclaration

    override val defaultProperties: Map<String, String>
        get() = mapOf(
            Indices.Structure.PropertyDeclaration.Id to (key + value).toMPSIDNumber(),
            Indices.Core.INamedConcept.Name to key
        )
    override val defaultReferences: List<Ref>
        get() = listOf(
            mapOf(
                "role" to Indices.Structure.PropertyDeclaration.DataType,
                "to" to "${Indices.Imports.JetbrainsStructure}:${DataTypeMap[value]}",
                "resolve" to value)
        )
}

