import builders.conceptBuilder
import models.CollectionLayout
import writers.ConceptWriter

fun main(args: Array<String>) {
    val input = "language(\"Hello\"){}"
    val c = conceptBuilder("OtherThings"){
        set("aName", "string")
        set("aNumber", "integer")
        editor {
            layout(CollectionLayout.HORIZONTAL)
            property("aName")
        }
    }
    val writer = ConceptWriter(c)
    writer.write()

//    val result = evalFile(input)
 //   val value = (result.valueOrThrow().returnValue as ResultValue.Value).value as Language


}