import writers.LanguageWriter

fun main(args: Array<String>) {
    val lang = language("CalculatorLanguage"){
        concept("Foo"){
            property("some", "string")
        }
    }.run { validation.Validator().validate(this)}

    LanguageWriter(lang).save("d:\\thesis\\testdocs")
}