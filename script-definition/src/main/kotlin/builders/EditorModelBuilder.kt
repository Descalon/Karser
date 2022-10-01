package builders

import models.*


class EditorCellModelCollectionBuilder(private val parent: IEditorComponentCollection): IModelBuilder<EditorCellModelCollection>{
    override val subject: EditorCellModelCollection = EditorCellModelCollection(parent).apply { parent.components.add(this) }

    private fun addComponent(c: IEditorComponent){
        subject.components.add(c)
    }

    fun constant(value: String) =
        apply { addComponent(EditorConstant(value, subject)) }

    fun property(reference: String) =
        apply {
            // TODO better exceptions
            val editor = when(parent){
                is EditorCellModelCollection -> (parent as IEditorComponent).getEditor()
                else -> parent as Editor
            }
            val ref = editor.parent.structure.properties.associateBy { it.key }[reference] ?: throw IllegalArgumentException("Reference not found")
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
        apply { addComponent(EditorCellLayoutComponent(l,subject))}

    fun collection(init: EditorCellModelCollectionBuilder.() -> Unit): EditorCellModelCollectionBuilder {
        return apply {
            EditorCellModelCollectionBuilder(subject).build(init)
        }
    }

}