package writers

import models.INode
import org.w3c.dom.Document
import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory

abstract class ElementFactory(private val document: Document) {
    open fun generateChildren() = listOf<Element>()

    open fun createFromNode(principle: INode): Element = ElementBuilder.build(document) {
        attribute("concept", principle.conceptInstance)
        attribute("id", principle.id)
        if (principle.role.isNotEmpty())
            attribute("role", principle.role)

        principle.defaultProperties.forEach { (k, v) ->
            property {
                attribute("role", k)
                attribute("value", v)
            }
        }
        principle.defaultReferences.forEach {
            ref {
                attribute("to", it.to)
                attribute("role", it.role)
                if (it.resolve.isNotEmpty())
                    attribute("resolve", it.resolve)
            }
        }
        generateChildren().forEach {
            child(it)
        }
    }

    private class ElementBuilder(private val doc: Document, tagName: String = "node") {
        private val element: Element = doc.createElement(tagName)

        fun attribute(key: String, value: String) =
            apply { element.setAttribute(key, value) }

        private fun addChildNode(tagName: String, init: ElementBuilder.() -> Unit) =
            apply {
                ElementBuilder(doc, tagName).apply(init).build().run { element.appendChild(this) }
            }

        fun property(init: ElementBuilder.() -> Unit) =
            addChildNode("property", init)

        fun ref(init: ElementBuilder.() -> Unit) =
            addChildNode("ref", init)

        fun child(init: ElementBuilder.() -> Unit) =
            addChildNode("node", init)

        fun child(c: Element) = apply { element.appendChild(c) }

        fun build() = element

        companion object BuildFunction {
            fun build(doc: Document, init: ElementBuilder.() -> Unit) =
                ElementBuilder(doc).apply(init).build()
        }

    }
}