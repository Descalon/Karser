package models

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val concept
    get() = Concept("testConcept")
class EditorTests : FunSpec ({
    test("Component gets id from editor"){
        val sut = Editor(concept)
        sut.components.add(EditorConstant("test", sut))
        val child = NewLine(sut)
        sut.components.add(child)

        sut.getIdForModel(child) shouldBe 1
    }
})