package builders

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull

class ConceptModelBuilderTests : FunSpec({
    test("An 'empty' concept still has a Structure aspect"){
        val sut = ConceptModelBuilder("TestConcept", 0, arrayOf()).build {  }
        sut.aspects.shouldHaveSize(1)
        sut.structure.shouldNotBeNull()
    }
})