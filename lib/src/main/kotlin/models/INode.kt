package models

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


abstract class IRegistry {
    abstract val references: MutableList<LanguageReference>
}

interface IModel {
    val node: INode
    val registry: IRegistry
}
