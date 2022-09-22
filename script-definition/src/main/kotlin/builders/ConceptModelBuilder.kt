package builders

import models.*

class ConceptModelBuilder(conceptName: String, conceptId: Int, implementations: Array<out String>, parentLanguage: Language? = null) : IModelBuilder<Concept>{
    override val subject: Concept
    private val structureAspect: Structure

    init {
        subject = Concept(conceptName,conceptId, implementations, parentLanguage)
        structureAspect = Structure(subject)
        subject.aspects.add(structureAspect)
    }

    private fun <T, Y> addAspect(builder: T, init: T.() -> Unit): Y
            where T : IModelBuilder<Y>, Y : IModel, Y : Aspect = builder.build(init).also { subject.aspects.add(it) }

    fun editor(init: EditorModelBuilder.() -> Unit): Editor = addAspect(EditorModelBuilder(subject), init)
    fun set(key: String, value: String) = apply { structureAspect.properties.add(ConceptProperty(key,value,structureAspect))}
    fun setProperty(key: String, value: String) = set(key, value)

    fun add(name: String, type: String) =
        apply { structureAspect.children.add(ChildReference(name, type, structureAspect)) }

    fun addChild(name: String, type: String) =
        add(name, type)

    fun add(name: String, type: String, init: ChildReference.() -> Unit) =
        apply { structureAspect.children.add(ChildReference(name, type, structureAspect).apply(init)) }

    fun reference(name: String, type: String, init: Reference.() -> Unit) =
        apply { structureAspect.references.add(Reference(name, type, structureAspect).apply(init)) }

    fun root() = apply { structureAspect.isRoot = true }
    fun extends(parent: String) = apply { structureAspect.extendsConcept = parent }
}

