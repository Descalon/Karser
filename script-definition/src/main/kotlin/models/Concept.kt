package models


class Concept(
    val name: String,
    val id: Int,
    val implementations: Array<out String>,
    internal var parent: Language? = null,
) : IModel, IDProvider<Aspect>{
    internal val aspects: MutableSet<Aspect> = mutableSetOf()
    private var _structure: Structure? = null
    internal val structure: Structure
        get() =
            _structure ?: (aspects.find {it is Structure} as Structure).apply { _structure = this }

    override fun getIdForModel(model: Aspect) = when(model) {
        is Structure -> parent?.getIdForModel(this) ?: 0
        else -> aspects.indexOf(model)
    }


}

