package script.builders

import script.models.Concept
import script.models.Language

class LanguageBuilder(name: String) {
    private val principle = Language(name)
    internal fun build() = principle

    fun concept(name: String, init: ConceptBuilder.() -> Unit) = apply {
        ConceptBuilder(name).apply(init).build().let { principle.concepts.add(it) }
    }

    fun concept(concept: Concept)
        = apply { principle.concepts.add(concept)}
}
