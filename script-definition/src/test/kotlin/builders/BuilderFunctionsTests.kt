package builders

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import models.Editor

class BuilderFunctionsTests: FunSpec ({

    test("LanguageModelBuilder Function should return Language with name"){
        language("TestLanguage"){}.name shouldBe "TestLanguage"
    }

    test("ConceptModelBuilder function should return Concept with name and id"){
        val sut = conceptBuilder("TestConcept"){}
        sut.name shouldBe "TestConcept"
        sut.structure.id shouldBe 0
    }

    test("EditorModelBuilder function should return Editor "){
        editorBuilder(conceptBuilder("TestConcept"){}){}.shouldBeTypeOf<Editor>()
    }

    test("Happy flow builders") {
        val sut = language("TestLanguage"){
            concept("RootConcept"){
                root()
                set("Name", "string")
            }
            concept("OtherConcept"){
                set("Name", "string")
            }
        }
        sut.concepts.shouldHaveSize(2)
    }
})