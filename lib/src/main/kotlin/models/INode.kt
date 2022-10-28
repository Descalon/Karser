package models

interface IDProvider<T: IModel> {
    fun getIdForModel(model: T): Int
}
//data class Ref(val role: String, val to: String, val resolve: String)

typealias Ref = Map<String,String>

interface INode {
    val id: String
    val conceptInstance: String
    val role: String
    val defaultProperties
        get() = mapOf<String,String>()
    val defaultReferences
        get() = listOf<Ref>()
    val childNodes
        get() = listOf<INode>()
}