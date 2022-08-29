package builders

import models.*

class EditorModelBuilder(parentConcept: Concept) : IModelBuilder<Editor> {
    override val subject: Editor = Editor(parentConcept)

    private fun addComponent(c: IEditorComponent) =
        subject.components.add(c)

    fun constant(value: String) =
        apply { addComponent(EditorConstant(value)) }

    fun property(reference: String) =
        apply {
            // TODO better exceptions
            val ref = subject.parent.structure.properties.associateBy { it.key }[reference] ?: throw IllegalArgumentException("Reference not found")
            addComponent(PropertyReferenceEditor(ref))
        }

    fun list(childReference: String) =
        apply { addComponent(ListDeclarationEditor(childReference)) }

    fun child(childReference: String) =
        apply { addComponent(ChildIncludeEditor(childReference))}

    fun referent(childReference: String, property: String) =
        apply { addComponent(ChildPropertyReferenceEditor(childReference, property)) }

    fun newLine() =
        apply { addComponent(NewLine()) }

    fun layout(l: CollectionLayout) =
        apply { subject.collectionLayout = l}
}
