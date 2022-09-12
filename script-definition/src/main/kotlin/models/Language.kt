package models

data class Language(val name: String): IModel {
    val concepts : MutableList<Concept> = mutableListOf()
    fun getRoots() = concepts.filter { it.structure.isRoot }
    fun getConceptMap() = concepts.associateBy { it.name }
}
