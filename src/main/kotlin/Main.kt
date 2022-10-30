import builders.conceptBuilder
import builders.language
import models.CollectionLayout
import writers.ConceptWriter
import writers.LanguageWriter

fun main(args: Array<String>) {
    val testLang = language("CalculatorLanguage"){
        concept("InputField"){
            set("name", "string")
        }
        concept("InputFieldReference"){
            extends("Expression")
            reference("field", "InputField"){}
        }
        concept("OutputField"){
            set("name", "string")
        }
        concept("Calculator"){
            root()
            set("name", "string")
            add("inputField", "InputField")
            add("outputField", "OutputField")
            editor {
                layout(CollectionLayout.INDENT)
                constant("calculator")
                property("name")
            }
        }
    }
    val writer = LanguageWriter(testLang)
    writer.write()
    println("Written file(s)")

//    val result = evalFile(input)
 //   val value = (result.valueOrThrow().returnValue as ResultValue.Value).value as Language


}