package script.models.aspects.editor.components.styles

import script.models.aspects.IEditorComponent

interface IEditorStyleComponent : IEditorComponent {
    override val components: MutableList<IEditorComponent>
        get() = mutableListOf()
}

class IndentLayoutNewLineStyleClassItem : IEditorStyleComponent
class IndentLayoutNewLineChildrenStyleClassItem: IEditorStyleComponent