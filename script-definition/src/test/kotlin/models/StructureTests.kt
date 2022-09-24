package models

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.lang.IllegalArgumentException

class StructureTests : FunSpec ({
    context("Node gets id from parental Structure") {
        test("Property") {
            val sut = Structure(Concept("testConcept"))
            sut.properties.add(ConceptProperty("key1", "value", sut))
            val property = ConceptProperty("key", "value", sut).also { sut.properties.add((it)) }
            sut.getIdForModel(property) shouldBe 1
        }
        test("ChildReference") {
            val sut = Structure(Concept("testConcept"))
            sut.children.add(ChildReference("key1", "value", sut))
            val child = ChildReference("key", "value", sut).also { sut.children.add((it)) }
            sut.getIdForModel(child) shouldBe 1
        }
        test("Reference") {
            val sut = Structure(Concept("testConcept"))
            sut.references.add(Reference("key1", "value", sut))
            val child = Reference("key", "value", sut).also { sut.references.add((it)) }
            sut.getIdForModel(child) shouldBe 1
        }
        test("Other model type"){
            val sut = Structure(Concept("testConcept"))
            val model = object: IModel{}

            val exception = shouldThrow<IllegalArgumentException> { sut.getIdForModel(model) }
            exception.message shouldBe "Illegal type ${model::class}. Structure only supports ids for ChildReference, Reference, and ConceptProperty"
        }
    }
})