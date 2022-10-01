package writers

import models.Aspect
import org.w3c.dom.Document

interface IWriter {
    val documentName: String
    fun writeToDocument(): Document
}

fun getModelRef(aspect: Aspect) = "r:${aspect.uuid}(${aspect.parent.parent?.name}.${aspect::class.simpleName?.lowercase()})"