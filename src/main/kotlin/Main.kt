import writers.LanguageWriter

fun main(args: Array<String>) {
    val lang = language("CalculatorLanguage"){
        primitive("myString")
    }.run { validation.Validator().validate(this)}

    LanguageWriter(lang).save("d:\\thesis\\testdocs")
}