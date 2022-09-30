package builders

import models.*

class ConceptModelBuilder(conceptName: String, implementations: Array<out String>, parentLanguage: Language? = null) : IModelBuilder<Concept>{
    override val subject: Concept
    private val structureAspect: Structure

    init {
        subject = Concept(conceptName, parentLanguage)
        structureAspect = Structure(subject)
        subject.aspects.add(structureAspect)
    }

    private fun <T, Y> addAspect(builder: T, init: T.() -> Unit): Y
            where T : IModelBuilder<Y>, Y : IModel, Y : Aspect = builder.build(init).also { subject.aspects.add(it) }
    private fun addAspect(aspect: Aspect) {
        subject.aspects.add(aspect)
    }

    fun editor(init: EditorCellModelCollectionBuilder.() -> Unit): Editor {
        val editor = EditorCellModelCollectionBuilder(Editor(subject)).build(init).getEditor()
        addAspect(editor)
        return editor
    }
    fun set(key: String, value: String) = apply { structureAspect.properties.add(ConceptProperty(key,value,structureAspect))}
    fun setProperty(key: String, value: String) = set(key, value)

    fun add(name: String, type: String) =
        apply { structureAspect.children.add(ChildReference(structureAspect,name,type)) }

    fun addChild(name: String, type: String) =
        add(name, type)

    fun add(name: String, type: String, init: ChildReferenceBuilder.() -> Unit): ConceptModelBuilder {
        val childRef = ChildReferenceBuilder(structureAspect).apply{name(name); type(type)}.build(init) as ChildReference
        return apply {structureAspect.children.add(childRef)}
    }
    fun add(init: ChildReferenceBuilder.() -> Unit): ConceptModelBuilder {
        val childRef = ChildReferenceBuilder(structureAspect).build(init) as ChildReference
        return apply {structureAspect.children.add(childRef)}
    }

    fun reference(name: String, type: String, builderInit: ReferenceBuilder.() -> Unit): ConceptModelBuilder {
        val reference = ReferenceBuilder(structureAspect).apply{name(name); type(type)}.build(builderInit)
        return apply { structureAspect.references.add(reference) }
    }
    fun reference(builderInit: ReferenceBuilder.() -> Unit): ConceptModelBuilder {
        val reference = ReferenceBuilder(structureAspect).build(builderInit)
        return apply { structureAspect.references.add(reference) }
    }

    fun root() = apply { structureAspect.isRoot = true }
    fun extends(parent: String) = apply { structureAspect.extendsConcept = parent }
}

