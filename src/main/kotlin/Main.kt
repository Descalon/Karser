import org.w3c.dom.Document
import org.w3c.dom.Element
import script.models.aspects.editor.components.CollectionLayout
import writers.LanguageWriter

fun main(args: Array<String>) {
    val lang = language("CalculatorLanguage"){
        concept("InputField"){
            property("name", "string")
        }
        concept("Calculator"){
            isRoot()
            property("name", "string")
            child("inputField", "InputField") {
                optional()
            }
            editor{
                stringConstant("calculator")
                property("name"){
                    newline()
                }
                child("inputField"){
                    newlineForChildren()
                }
            }
        }
    }.run { validation.Validator().validate(this)}

    LanguageWriter(lang).save("c:\\users\\nagla\\testdocs")
}