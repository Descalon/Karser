package script.models.aspects

import script.models.Aspect

interface IEditorComponent{
}

abstract class EditorComponent(val components: MutableList<IEditorComponent> = mutableListOf()) : IEditorComponent

class Editor internal constructor(val component: IEditorComponent): Aspect