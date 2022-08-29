package builders

import models.Concept
import models.Language

class LanguageModelBuilder(languageName: String): IModelBuilder<Language>{
    override val subject: Language = Language(languageName)
    fun concept(concept: Concept): Concept =
        concept.apply { parent = subject; subject.concepts.add(this) }

    fun concept(name: String, vararg implements: String, lambda: ConceptModelBuilder.() -> Unit): Concept =
        ConceptModelBuilder(name, subject.concepts.size, implements, subject).build(lambda).also{subject.concepts.add(it)}
}