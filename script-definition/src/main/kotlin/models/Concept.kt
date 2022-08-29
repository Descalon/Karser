package models


class Concept(
    val name: String,
    val id: Int,
    val implementations: Array<out String>,
    internal var parent: Language? = null,
) : IModel {
    internal val aspects: MutableSet<Aspect> = mutableSetOf()
    private var _structure: Structure? = null
    internal val structure: Structure
        get() =
            _structure ?: (aspects.find {it is Structure} as Structure).apply { _structure = this }
}

