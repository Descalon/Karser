package utils

import models.Aspect
import models.Editor
import models.Structure
import org.w3c.dom.Document
import org.w3c.dom.Element
import writers.EditorWriter
import writers.StructureWriter
import kotlin.math.abs

val DataTypeMap = mapOf(
    "string" to "fKAOsGN",
    "integer" to "fKAQMTA",
    "BaseConcept" to "gw2VY9q"
)

fun String.toMPSIDNumber() = "${abs(this.hashCode())}"

fun resolver(aspect: Aspect) = when(aspect){
    is Structure -> StructureWriter(aspect)
    is Editor -> EditorWriter(aspect)
    else -> throw IllegalArgumentException("things!")
}

class ElementBuilder (private val doc: Document, tagName: String = "node") {
    private val element: Element = doc.createElement(tagName)

    fun attribute(key: String, value: String) =
        apply { element.setAttribute(key,value) }

    private fun addChildNode(tagName: String, init: ElementBuilder.() -> Unit) =
        apply {
            ElementBuilder(doc,tagName).apply(init).build().run { element.appendChild(this) }
        }
    fun property(init: ElementBuilder.() -> Unit) =
        addChildNode("property", init)

    fun ref(init: ElementBuilder.() -> Unit) =
        addChildNode("ref", init)

    fun child(init: ElementBuilder.() -> Unit) =
        addChildNode("node", init)

    fun child(c : Element) = apply { element.appendChild(c) }

    fun build() = element

}
fun elementBuilder(doc: Document, init: ElementBuilder.() -> Unit) =
    ElementBuilder(doc).apply(init)
