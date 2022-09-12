import builders.conceptBuilder
import writers.ConceptWriter

fun main(args: Array<String>) {
    val input = "language(\"Hello\"){}"
    val c = conceptBuilder("OtherThings", 0){
        set("aName", "string")
        set("aNumber", "integer")
        editor {
            property("aName")
        }
    }
    val writer = ConceptWriter(c)
    writer.write()

//    val result = evalFile(input)
 //   val value = (result.valueOrThrow().returnValue as ResultValue.Value).value as Language


}