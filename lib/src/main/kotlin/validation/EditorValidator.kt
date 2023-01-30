package validation

import models.Concept
import models.aspects.CollectionLayout
import models.aspects.Editor
import models.aspects.IEditorComponent
import models.aspects.editor.components.*

class EditorValidator(private val parent: Concept) {
    fun resolve(aspect: script.models.aspects.Editor) : Editor {
        return Editor("e${parent.id}", resolve(0, aspect.component), parent)
    }

    private fun resolve(id:Int, component: script.models.aspects.IEditorComponent): IEditorComponent = when (component) {
        is script.models.aspects.editor.components.StringConstant -> transform(id, component)
        is script.models.aspects.editor.components.PropertyReference -> transform(id, component)
        is script.models.aspects.editor.components.ComponentCollection -> transform(id, component)
        is script.models.aspects.editor.components.Ref -> transform(id, component)
        is script.models.aspects.editor.components.ChildRefNode -> transform(id, component)
        is script.models.aspects.editor.components.styles.IEditorStyleComponent -> resolve(id, component)
        else -> throw Exception("no info for this type $component")
    }
    private fun resolve(id: Int, styleComponent: script.models.aspects.editor.components.styles.IEditorStyleComponent) = when(styleComponent) {
        is script.models.aspects.editor.components.styles.IndentLayoutNewLineStyleClassItem -> IndentLayoutNewLineStyleClassItem("st$id")
        is script.models.aspects.editor.components.styles.IndentLayoutNewLineChildrenStyleClassItem -> IndentLayoutNewLineChildrenStyleClassItem("st$id")
        else -> throw Exception("no info for this type $styleComponent")
    }

    private fun transform(id: Int, component: script.models.aspects.editor.components.StringConstant) =
        component.components.mapIndexed(this::resolve).let { StringConstant("", component.value, it) }
    private fun transform(id: Int, component: script.models.aspects.editor.components.PropertyReference): PropertyReference {
        val p = parent.properties.find {it.role == component.role} ?: throw Exception("Unresolved reference ${component.role}")
        return component.components.mapIndexed(this::resolve).let { PropertyReference("pr$id", p, it) }
    }
    private fun transform(id: Int, component: script.models.aspects.editor.components.ComponentCollection): ComponentCollection {
        val layout = when(component.layout){
            script.models.aspects.editor.components.CollectionLayout.INDENT -> CollectionLayout.INDENT
            script.models.aspects.editor.components.CollectionLayout.VERTICAL -> CollectionLayout.VERTICAL
            script.models.aspects.editor.components.CollectionLayout.HORIZONTAL -> CollectionLayout.HORIZONTAL
        }
        return component.components.mapIndexed(this::resolve).let { ComponentCollection("cc${id}", layout, it) }
    }
    private fun transform(id: Int, component: script.models.aspects.editor.components.Ref): Ref {
        val link = parent.references.first { it.role == component.role }
        return component.components.mapIndexed(this::resolve).let { Ref("r${id}", link, component.role, it) }
    }
    private fun transform(id: Int, component: script.models.aspects.editor.components.ChildRefNode): ChildRefNode {
        val link = parent.children.first { it.role == component.linkDeclaration }
        return component.components.mapIndexed(this::resolve).let { ChildRefNode("cr$id", link, it) }

    }
}