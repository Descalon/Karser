package builders

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe

class LanguageModelBuilderTests : FunSpec({
    test("Invoking concept should add it to the list"){
        val concept = ConceptModelBuilder("TestConcept", 0, arrayOf()).build{}
        val sut = LanguageModelBuilder("testLanguage").build{ concept(concept)}
        concept.parent shouldBe sut
        sut.concepts shouldContain concept
    }
})