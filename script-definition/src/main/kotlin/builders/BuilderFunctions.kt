package builders

import models.Concept
import models.IModel
import models.Language

fun conceptBuilder(name: String, vararg implements: String, lambda: ConceptModelBuilder.() -> Unit) =
    ConceptModelBuilder(name, implements).build(lambda)

fun editorBuilder(parent: Concept, init: EditorModelBuilder.() -> Unit) =
    EditorModelBuilder(parent).build(init)

// Naming this function language, not languageBuilder, as this is the entrypoint for the DSL
fun language(name: String, init: LanguageModelBuilder.() -> Unit): Language =
    LanguageModelBuilder(name).build(init)

fun <T : IModelBuilder<Y>, Y : IModel> T.build(block: T.() -> Unit): Y =
    apply(block).run { subject }
