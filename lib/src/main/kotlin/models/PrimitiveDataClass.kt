package models

import utils.BuiltInDefinitions
import utils.ModelImport

class PrimitiveDataClass(
    override val nodeID: String,
    override val name: String,
    val parent: Language): IModel {

    override val defaultProperties: Map<String, String>
        get() = mapOf(
            "dataTypeId" to nodeID,
            "name" to name
        )

    override val conceptInstance: String = "PrimitiveDataTypeDeclaration"

    override val reference: String = "r:${parent.name}(${parent.uuid})"

    override val imports: List<ModelImport> = listOf()
    override val registry: List<Language> = listOf(
        BuiltInDefinitions.BuiltInLanguage.Core,
        BuiltInDefinitions.BuiltInLanguage.Structure)

}