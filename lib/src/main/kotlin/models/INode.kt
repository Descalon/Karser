package models

interface INode {
    val id: String
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