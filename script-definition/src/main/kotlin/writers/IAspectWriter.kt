package writers

import models.Aspect
import org.w3c.dom.Document
import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory

interface IWriter {
    val documentName: String
    fun write(): Pair<String,Document>
}
abstract class AspectWriter<T: Aspect>(val principle: T) : IWriter {
    protected val document: Document
    protected val model: Element

    init {
        val name = principle::class.simpleName!!.lowercase()
        val template = this::class.java.classLoader.getResource("$name.template")?.file
        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(template)
        model = document.documentElement
    }
}
