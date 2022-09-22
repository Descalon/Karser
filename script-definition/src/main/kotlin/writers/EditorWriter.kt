package writers

import models.ConceptProperty
import models.Editor
import models.PropertyReferenceEditor
import org.w3c.dom.Document
import org.w3c.dom.Element
import utils.*

class EditorWriter(principle: Editor) : AspectWriter<Editor>(principle){
    override fun generateChildren(): List<Element> {
        TODO("Not yet implemented")
    }

    override val documentName: String
        get() = "${principle.parent.name}_Editor.mpsr"
}