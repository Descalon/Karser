package writers

import models.Aspect
import models.Editor
import models.Structure
import org.w3c.dom.Document
import org.w3c.dom.Element
import kotlin.math.abs

val CoreImportIndex = "tpck"
val StructureImportIndex = "dvvt" //TODO: This is silly to declare at const
val ConceptDeclarationIndex = "1TIwiD"
val IsRootableIndex = "19KtqR"
val ConceptIdIndex = "EcuMT"
val INamedConceptNameIndex = "TrG5h"
val ConceptDeclarationExtendsIndex = "1TJDcQ"

val AbstractConceptDeclarationPropertyDeclarationIndex = "1TKVEl"
val PropertyDeclarationConceptIndex = "1TJgyi"
val PropertyDeclarationIdIndex = "IQ2nx"
val PropertyDeclarationDataTypeIndex = "AX2Wp"

val ConceptEditorDeclarationIndex = "24kQdi"
val AbstractComponentConceptDeclarationIndex = "1XX52x"
val CellModelPropertyIndex = "3F0A7n"
val CellModelWRoleRelationDeclarationIndex = "1NtTu8"
val EditorCellModelIndex = "2wV5jI"

val DataTypeMap = mapOf(
    "string" to "fKAOsGN",
    "integer" to "fKAQMTA",
    "BaseConcept" to "gw2VY9q"
)

class PropertyBuilder(private val e: Element) {
    fun role(input: String) =
        apply { e.setAttribute("role", input)}
    fun value(input: String) =
        apply { e.setAttribute("value", input)}
}
fun property (doc: Document, init: PropertyBuilder.() -> Unit): Element {
    val element = doc.createElement("property")
    PropertyBuilder(element).apply(init)
    return element
}
class RefBuilder(private val e: Element) {
    fun role(input: String) =
        apply { e.setAttribute("role", input)}
    fun resolve(input: String) =
        apply { e.setAttribute("resolve", input)}
    fun to(input:String) =
        apply {e.setAttribute("to", input)}
}
fun ref (doc: Document, init: RefBuilder.() -> Unit): Element {
    val element = doc.createElement("ref")
    RefBuilder(element).apply(init)
    return element
}
fun String.toMPSIDNumber() = "${abs(this.hashCode())}"

fun resolver(aspect: Aspect) = when(aspect){
    is Structure -> StructureWriter(aspect)
    is Editor -> EditorWriter(aspect)
    else -> throw IllegalArgumentException("things!")
}