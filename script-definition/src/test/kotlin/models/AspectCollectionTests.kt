package models

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val concept
    get() = Concept("TestConcept")

private open class TestAspect : Aspect(concept) {
    override val conceptInstance: String = ""
}

class AspectCollectionTests : FunSpec({
    context("Add returns") {
        test("true when no other item exists") {
            val sut = AspectCollection()
            val element = object : TestAspect() {}
            val result = sut.add(element)
            result shouldBe true
        }
        test("true when unique element is added") {
            val sut = AspectCollection()
            val element = object : TestAspect() {}
            val element2 = object : TestAspect() {}
            sut.add(element)
            val result = sut.add(element2)

            result shouldBe true
        }
        test("false when non-unique element is added") {
            val sut = AspectCollection()
            val element = Editor(concept)
            val element2 = Editor(concept)
            sut.add(element)
            val result = sut.add(element2)

            result shouldBe false
        }
    }
    context("Add all returns"){
        test("true when collection is empty"){
            val sut = AspectCollection()
            val elements = (0..10).map { object: TestAspect(){} }
            val result = sut.addAll(elements)
            result shouldBe true
        }
        test("true when added collection contains single valid entry "){
            val sut = AspectCollection()
            sut.add(Editor(concept))
            val elements = listOf(Editor(concept), object: TestAspect(){})
            val result = sut.addAll(elements)
            result shouldBe true
        }
        test("false when added collection contains no valid entry "){
            val sut = AspectCollection()
            sut.add(Editor(concept))
            val elements = listOf<Aspect>(Editor(concept))
            val result = sut.addAll(elements)
            result shouldBe false
        }
    }
})