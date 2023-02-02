import script.builders.ConceptBuilder
import script.builders.LanguageBuilder

fun language(name: String, init: LanguageBuilder.() -> Unit)
        = LanguageBuilder(name).apply(init).build()

fun conceptBuilder(name: String, init: ConceptBuilder.() -> Unit)
    = ConceptBuilder(name).apply(init).build()