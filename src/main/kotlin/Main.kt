import writers.LanguageWriter

fun main(args: Array<String>) {
    val inputfieldref = conceptBuilder("InputFieldReference"){
        extends("Expression", "baseLanguage")
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
        concept(inputfieldref)
        concept("Calculator"){
            implements("INamedConcept")
            isRoot()
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