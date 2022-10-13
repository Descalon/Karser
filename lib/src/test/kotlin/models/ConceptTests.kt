package models

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val concept: Concept
    get() {
        val concept = Concept("testConcept")
        val structure = Structure(concept)
        concept.aspects.add(structure)
        return concept
    }
class ConceptTests : FunSpec({
    context("Structure aspect") {
        test("should get id from language") {
            val parent = Language("testLang")
            parent.concepts.add(Concept("1"))
            val sut = concept.apply { this.parent = parent }.apply { parent.concepts.add(this) }

            sut.getIdForModel(sut.structure) shouldBe 1
        }
        test("Should get id 0 when there is no parent") {
            val sut = concept
            val result = sut.getIdForModel(sut.structure)
            result shouldBe 0
        }
    }
    context("Other aspects") {
        test("should get id from parental concept") {
            val sut = concept
            val editor = Editor(sut)
            sut.aspects.add(editor)
            val result = sut.getIdForModel(editor)
            result shouldBe 1
        }
    }
})