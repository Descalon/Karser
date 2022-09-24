package writers

import models.Editor
import org.w3c.dom.Element

class EditorWriter(principle: Editor) : AspectWriter<Editor>(principle){
    override fun generateChildren(): List<Element> {
        TODO("Not yet implemented")
    }

    override val documentName: String
        get() = "${principle.parent.name}_Editor.mpsr"
}