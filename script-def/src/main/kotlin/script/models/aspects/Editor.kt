package script.models.aspects

import script.models.Aspect

interface IEditorComponent{
    val components: MutableList<IEditorComponent>
}

abstract class EditorComponent(override val components: MutableList<IEditorComponent> = mutableListOf()) : IEditorComponent

class Editor internal constructor(val component: IEditorComponent): Aspect