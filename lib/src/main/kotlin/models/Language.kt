package models

data class Language(val name: String): IResolver, IDProvider<Concept> {
    val concepts : MutableList<Concept> = mutableListOf()
    fun getRoots() = concepts.filter { it.structure.isRoot }
    fun getConceptMap() = concepts.associateBy { it.name }

    override fun getIdForModel(model: Concept) = concepts.indexOf(model)
    override fun resolve(){
        concepts.forEach { c -> c.resolveWith(this) }
    }
    override fun resolveFor(context: IModel) = when (context) {
        is Reference -> concepts.find { it.name == context.type }
        else -> null
    }

}
