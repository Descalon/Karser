package script.models.aspects.editor.components

import script.models.aspects.EditorComponent
enum class CollectionLayout {
    INDENT,
    VERTICAL,
    HORIZONTAL
}

class ComponentCollection(val layout: CollectionLayout): EditorComponent()
class StringConstant(val value: String): EditorComponent()
class PropertyReference(val role: String) : EditorComponent()
class ChildRefNode(val linkDeclaration: String): EditorComponent()
class Ref(val linkDeclaration: String, val role: String): EditorComponent()