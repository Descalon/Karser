import org.w3c.dom.Document
import org.w3c.dom.Element
import script.builders.ConceptBuilder
import script.models.aspects.editor.components.CollectionLayout
import writers.LanguageWriter

fun main(args: Array<String>) {
    val inputfielaref = conceptBuilder("InputFieldReference"){
        //extends("Expression")
        reference("inputField", "InputField")
        editor {
            ref("inputField", "name")
        }
    }
    val lang = language("CalculatorLanguage"){
        //import("jetbrains.mps.baseLanguage")
        concept("InputField"){
            property("name", "string")
            editor {
                stringConstant("Input")
                property("name")
            }
        }
        concept("OutputField") {
        //    child("expression", "Expression")
            editor {
                stringConstant("output")
        //        child("expression")
            }
        }
        concept(inputfielaref)
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