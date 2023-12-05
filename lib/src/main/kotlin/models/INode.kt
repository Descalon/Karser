package models

import utils.ModelImport

interface INode {

    val nodeID: String
    val conceptInstance: String
    val conceptRole: String
        get() = ""
    val defaultProperties
        get() = mapOf<String,String>()
    val defaultReferences
        get() = listOf<Map<String,String>>()
    val childNodes
        get() = listOf<INode>()
}

interface IModel : INode {
    val name: String
    val imports: List<ModelImport>
    val registry: List<Language>
    val reference: String
}
