package builders

import models.*

class EditorModelBuilder(parentConcept: Concept) : IModelBuilder<Editor> {
    override val subject: Editor = Editor(parentConcept)

    private fun addComponent(c: IEditorComponent) =
        subject.components.add(c)

    fun constant(value: String) =
        apply { addComponent(EditorConstant(value, subject)) }

    fun property(reference: String) =
        apply {
            // TODO better exceptions
            val ref = subject.parent.structure.properties.associateBy { it.key }[reference] ?: throw IllegalArgumentException("Reference not found")
            addComponent(PropertyReferenceEditor(ref, subject))
        }

    fun list(childReference: String) =
        apply { addComponent(ListDeclarationEditor(childReference, subject)) }

    fun child(childReference: String) =
        apply { addComponent(ChildIncludeEditor(childReference, subject))}

    fun referent(childReference: String, property: String) =
        apply { addComponent(ChildPropertyReferenceEditor(childReference, property, subject)) }

    fun newLine() =
        apply { addComponent(NewLine(subject)) }

    fun layout(l: CollectionLayout) =
        apply { subject.collectionLayout = l}
}