package writers

import models.ChildReference
import models.INode
import models.Reference
import org.w3c.dom.Document
import org.w3c.dom.Element
import utils.Indices

class ReferenceElementFactory(document: Document) : ElementFactory(document) {
    override fun buildForNode(principle: INode): ElementBuilder = ElementBuilder.build(document) builder@{
        if(principle !is Reference) return@builder
        if(principle.isOptional) return@builder
        property {
            attribute("role", Indices.Structure.LinkDeclaration.SourceCardinality)
            attribute("value", "fLJekj4/_1")
        }
    }
}
class ChildReferenceElementFactory(document: Document) : ElementFactory(document) {
    private fun cardinalityString(principle: ChildReference) = when {
        principle.isOptional && !principle.isSingleton      -> "fLJekj5/_0__n"
        !principle.isOptional && principle.isSingleton      -> "fLJekj4/_1"
        else                                                -> "fLJekj6/_1__n"
    }
    override fun buildForNode(principle: INode): ElementBuilder = ElementBuilder.build(document) builder@{
        if(principle !is ChildReference) return@builder
        if(principle.isOptional) return@builder
        property {
            attribute("role", Indices.Structure.LinkDeclaration.SourceCardinality)
            attribute("value", cardinalityString(principle))
        }
    }
}
