package writers

import models.Structure
import org.w3c.dom.Element

class StructureWriter(principle: Structure): AspectWriter<Structure>(principle) {
    override val documentName: String
        get() = "${principle.parent.name}.mpsr"

    override fun generateChildren(): List<Element> {
        TODO("Not yet implemented")
    }
}