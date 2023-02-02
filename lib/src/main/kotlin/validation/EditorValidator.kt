package validation

import models.Concept
import models.aspects.CollectionLayout
import models.aspects.Editor
import models.aspects.IEditorComponent
import models.aspects.editor.components.*
import utils.Indices

class EditorValidator(private val parent: Concept) {
    fun resolve(aspect: script.models.aspects.Editor) : Editor {
        return Editor("e${parent.id}", resolve(0, aspect.component).first(), parent)
    }

    private fun resolve(id:Int, component: script.models.aspects.IEditorComponent): List<IEditorComponent> = when (component) {
        is script.models.aspects.editor.components.PropertyReference -> transform(id, component)
        is script.models.aspects.editor.components.StringConstant -> transform(id, component)
        is script.models.aspects.editor.components.ComponentCollection -> transform(id, component)
        is script.models.aspects.editor.components.Ref -> transform(id, component)
        is script.models.aspects.editor.components.ChildRefNode -> transform(id, component)
        is script.models.aspects.editor.components.styles.IEditorStyleComponent -> resolve(id, component)
        else -> throw Exception("no info for this type $component")
    }
    private fun resolve(id: Int, styleComponent: script.models.aspects.editor.components.styles.IEditorStyleComponent) = when(styleComponent) {
        is script.models.aspects.editor.components.styles.IndentLayoutNewLineStyleClassItem ->
            listOf(IndentLayoutNewLineStyleClassItem("st$id"))
        is script.models.aspects.editor.components.styles.IndentLayoutNewLineChildrenStyleClassItem -> listOf(
            LayoutNode("st${id}layout", CollectionLayout.INDENT, Indices.Editor.CellModelListWithRole.CellLayout),
            IndentLayoutNewLineChildrenStyleClassItem("st$id"))
        else -> throw Exception("no info for this type $styleComponent")
    }

    private fun mapComponents(component: script.models.aspects.IEditorComponent) =
        component.components.flatMapIndexed(this::resolve)
    private fun IEditorComponent.toList() = listOf(this)

    private fun transform(id: Int, component: script.models.aspects.editor.components.StringConstant) =
        StringConstant("sc$id", component.value, mapComponents(component)).toList()
    private fun transform(id: Int, component: script.models.aspects.editor.components.PropertyReference): List<IEditorComponent> {
        val p = parent.properties.find {it.role == component.role} ?: throw Exception("Unresolved reference ${component.role}")
        return PropertyReference("pr$id", p, mapComponents(component)).toList()
    }
    private fun transform(id: Int, component: script.models.aspects.editor.components.ComponentCollection): List<IEditorComponent> {
        val layout = when(component.layout){
            script.models.aspects.editor.components.CollectionLayout.INDENT -> CollectionLayout.INDENT
            script.models.aspects.editor.components.CollectionLayout.VERTICAL -> CollectionLayout.VERTICAL
            script.models.aspects.editor.components.CollectionLayout.HORIZONTAL -> CollectionLayout.HORIZONTAL
        }
        return ComponentCollection("cc$id", layout, mapComponents(component)).toList()
    }
    private fun transform(id: Int, component: script.models.aspects.editor.components.Ref): List<IEditorComponent> {
        val link = parent.references.first { it.role == component.role }
        return Ref("r$id", link, component.role, mapComponents(component)).toList()
    }
    private fun transform(id: Int, component: script.models.aspects.editor.components.ChildRefNode): List<IEditorComponent> {
        val link = parent.children.first { it.role == component.linkDeclaration }
        return ChildRefNode("cr$id", link, mapComponents(component)).toList()
    }
}