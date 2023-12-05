package script.builders

import script.builders.aspects.PrimitiveBuilder
import script.models.Concept
import script.models.Language
import script.models.PrimitiveDataClass

class LanguageBuilder(name: String) {
    private val principle = Language(name)
    internal fun build() = principle

    fun concept(name: String, init: ConceptBuilder.() -> Unit) = apply {
        val c = ConceptBuilder(name).apply(init).build()
        concept(c)
    }

    fun primitive(name: String, init: PrimitiveBuilder.() -> Unit) = apply {
        val p = PrimitiveBuilder(name).apply(init).build()
        primitive(p)
    }
    fun primitive(name: String) = apply {
        primitive(PrimitiveDataClass(name))
    }

    fun concept(concept: Concept)
        = apply { principle.concepts.add(concept)}
    fun primitive(primitive: PrimitiveDataClass)
        = apply { principle.primitives.add(primitive) }
}
