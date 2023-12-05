package models

import java.util.PrimitiveIterator
import java.util.UUID

open class Language(
    val name: String,
    uuid: UUID? = null,
    val conceptClasses: MutableList<AbstractConceptClass> = mutableListOf(),
    val primitiveDataClasses: MutableList<PrimitiveDataClass> = mutableListOf(),
    private val additionalImports: List<LanguageReference> = listOf(),
) {

    val uuid : UUID
    init{
        this.uuid = uuid ?: UUID.randomUUID()
    }

    val shortName : String
        get() = name.split(".").joinToString { it.first().toString() }
    val concepts
        get() = conceptClasses.filterIsInstance<Concept>()
    private val interfaces
        get() = conceptClasses.filterIsInstance<InterfaceConcept>()

    protected open val defaultImports
        get() = listOf<LanguageReference>()

    fun findConcept(predicate: (AbstractConceptClass) -> Boolean): AbstractConceptClass =
        conceptClasses.find(predicate)
            ?: defaultImports.map { it.language.findConcept(predicate) }.firstOrNull()
            ?: throw Exception("unresolved reference")
    fun findInterface(predicate: (AbstractConceptClass) -> Boolean): InterfaceConcept =
        interfaces.find(predicate)
        ?: defaultImports.map { it.language.findInterface(predicate) }.firstOrNull()
        ?: throw Exception("unresolved reference")

    private val importMap
        get() = (defaultImports + additionalImports).flatMap { i ->
            i.names.map { it.lowercase() }.associateWith { i }.asSequence()
        }.associate { (k, v) -> k to v }

    fun resolveLanguage (languageName: String): Language = when{
        languageName.isBlank() -> this
        importMap.containsKey(languageName) -> importMap[languageName]!!.language
        else -> throw Exception("Can not resolve reference $languageName")
    }
}

class LanguageReference(
    val language: Language,
    val aliases: List<String> = listOf()
) {
    val names
        get() = aliases + listOf(language.name)
}
