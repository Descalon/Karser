package script.builders.aspects

import script.models.aspects.EditorComponent
import script.models.aspects.IEditorComponent
import script.models.aspects.editor.components.*
import script.models.aspects.editor.components.styles.*

class EditorComponentCollectionBuilder(layout: CollectionLayout){
    private val principle = ComponentCollection(layout)
    private fun addComponent(component: IEditorComponent) = apply { principle.components.add(component) }
    fun build() = principle

    fun stringConstant(value: String) = addComponent(StringConstant(value))
    fun property(linkDeclaration: String) = property(linkDeclaration){}
    fun property(linkDeclaration: String, init: StyleBuilder.() -> Unit) =
        apply { val principle = PropertyReference(linkDeclaration).apply { StyleBuilder(this).init() }
        addComponent(principle)}
    fun collection(layout: CollectionLayout, init: EditorComponentCollectionBuilder.() -> Unit) =
        addComponent(EditorComponentCollectionBuilder(layout).apply(init).build())
    fun child(linkDeclaration: String) = child(linkDeclaration){}
    fun child(linkDeclaration: String, style: StyleBuilder.() -> Unit) =
        apply{
            val component = ChildRefNode(linkDeclaration).apply { StyleBuilder(this).style() }
            addComponent(component)
        }
    fun ref(linkDeclaration: String, role: String) =
        addComponent(Ref(linkDeclaration, role))

    class StyleBuilder(private val principle: EditorComponent) {
        fun newline() = apply { principle.components.add(IndentLayoutNewLineStyleClassItem())}
        fun newlineForChildren() = apply { principle.components.add(IndentLayoutNewLineChildrenStyleClassItem())}
    }
}