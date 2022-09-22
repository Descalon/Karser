package models

data class Language(val name: String): IModel, IDProvider<Concept> {
    val concepts : MutableList<Concept> = mutableListOf()
    fun getRoots() = concepts.filter { it.structure.isRoot }
    fun getConceptMap() = concepts.associateBy { it.name }

    override fun getIdForModel(model: Concept) = concepts.indexOf(model)
}
