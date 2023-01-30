package script.builders

import script.models.Language

class LanguageBuilder(name: String) {
    private val principle = Language(name)
    internal fun build() = principle

    fun concept(name: String, init: ConceptBuilder.() -> Unit): LanguageBuilder {
        val concept = ConceptBuilder(name).apply(init).build()
        principle.concepts.add(concept)
        return this
    }
}
