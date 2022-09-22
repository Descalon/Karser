package utils

import models.Aspect
import models.Editor
import models.Ref
import models.Structure
import org.w3c.dom.Document
import org.w3c.dom.Element
import writers.EditorWriter
import writers.StructureWriter
import writers.appendChildren
import kotlin.math.abs

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

class PropertyBuilder2(private val e: Element) {
    private fun set (key: String, value: String) =
        apply {e.setAttribute(key,value)}
    fun role(input: String) =
        set("role", input)
    fun value(input: String) =
        set("value", input)
}

class ReferenceBuilder(private val e: Element) {

}

class ElementBuilder (private val doc: Document, private val tagName: String = "node") {
    private val element: Element = doc.createElement(tagName);

    fun attribute(key: String, value: String) =
        apply { element.setAttribute(key,value) }

    private fun addChildNode(tagName: String, init: ElementBuilder.() -> Unit) =
        apply {
            ElementBuilder(doc,tagName).apply(init).build().run { element.appendChild(this) }
        }
    fun property(init: ElementBuilder.() -> Unit) =
        addChildNode("property", init)

    fun properties(map: Map<String, String>) =
        apply {
        }

    fun ref(init: ElementBuilder.() -> Unit) =
        addChildNode("property", init)

    fun child(init: ElementBuilder.() -> Unit) =
        addChildNode("node", init)

    fun child(c : Element) = apply { element.appendChild(c) }

    fun build() = element

}
fun element(doc: Document, init: ElementBuilder.() -> Unit) =
    ElementBuilder(doc).apply(init).build()