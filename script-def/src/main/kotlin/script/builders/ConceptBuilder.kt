package script.builders

import script.builders.aspects.EditorComponentCollectionBuilder
import script.models.Aspect
import script.models.Concept
import script.models.aspects.Editor
import script.models.aspects.editor.components.CollectionLayout

open class ConceptBuilder(name: String) {
    private val principle = Concept(name)
    private fun addAspect(aspect: Aspect)
        = apply { principle.aspects.add(aspect) }

    fun editor(init: EditorComponentCollectionBuilder.() -> Unit)
        = addAspect(Editor(EditorComponentCollectionBuilder(CollectionLayout.INDENT).apply(init).build()))
    fun editor(layout: CollectionLayout, init: EditorComponentCollectionBuilder.() -> Unit)
            = addAspect(Editor(EditorComponentCollectionBuilder(layout).apply(init).build()))
    fun implements(interfaceName: String, packageName: String = "core")
        = apply { principle.interfaces.add(Concept.InterfaceConceptReference(interfaceName, packageName))}
    fun extends(className: String, packageName: String = "core")
        = apply { principle.extends = Concept.ExtensionConceptReference(className, packageName)}
    fun property(targetName: String, targetType: String)
        = apply { principle.properties.add(Concept.Property(targetName,targetType))}
    fun reference(targetName: String, targetType: String)
        = apply { principle.references.add(Concept.Reference(targetName,targetType))}
    fun reference(targetName: String, targetType: String, init: ReferenceBuilder.() -> Unit): ConceptBuilder {
        val b = ReferenceBuilder(targetName, targetType).apply(init).build()
        principle.references.add(b)
        return this
    }
    fun child(targetName: String, targetType: String)
            = apply { principle.children.add(Concept.ChildReference(targetName,targetType))}
    fun child(targetName: String, targetType: String, init: ChildReferenceBuilder.() -> Unit): ConceptBuilder {
        val b = ChildReferenceBuilder(targetName, targetType).apply(init).build()
        principle.children.add(b)
        return this
    }

    fun isRoot() = apply { principle.isRoot = true }

    internal fun build() = principle

    class ReferenceBuilder(val targetName: String, val targetType: String) {
        private var isOptional: Boolean = false
        fun optional() = apply{ isOptional = true }
        internal fun build() = Concept.Reference(targetName, targetType, isOptional)
    }
    class ChildReferenceBuilder(val targetName: String, val targetType: String) {
        private var isOptional: Boolean = false
        private var isSingleton: Boolean = false
        fun optional() = apply { isOptional = true }
        fun singleton() = apply { isSingleton = true }
        internal fun build() = Concept.ChildReference(targetName, targetType, isOptional, isSingleton)
    }
}