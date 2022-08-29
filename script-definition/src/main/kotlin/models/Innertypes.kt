package models

data class ConceptProperty(val key: String, val value: String, val id: Int)

typealias ConceptProperties = MutableList<ConceptProperty>

interface IEditorComponent
data class EditorConstant(val value: String) : IEditorComponent
data class PropertyReferenceEditor(val reference: ConceptProperty) : IEditorComponent{}
data class ListDeclarationEditor(val reference: String) : IEditorComponent
data class ChildPropertyReferenceEditor(val reference: String, val childProperty: String) : IEditorComponent
data class ChildIncludeEditor(val reference: String): IEditorComponent
class NewLine : IEditorComponent

enum class CollectionLayout {
    NONE,
    VERTICAL,
    HORIZONTAL
}
