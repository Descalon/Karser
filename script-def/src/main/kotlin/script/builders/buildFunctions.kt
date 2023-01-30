import script.builders.LanguageBuilder

fun language(name: String, init: LanguageBuilder.() -> Unit)
        = LanguageBuilder(name).apply(init).build()