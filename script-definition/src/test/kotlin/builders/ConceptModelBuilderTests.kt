package builders

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import models.ChildReference
import models.Concept
import models.ConceptProperty

object Counter {
    private var value = 0;
    val count
        get() = value++
}
class ConceptModelBuilderTests : FunSpec({
    fun conceptBuilderAdd(builder:ConceptModelBuilder, name:String, type:String) = builder.add(name,type)

    fun sutBuilder(lambda: ConceptModelBuilder.() -> Unit) = when(val id = Counter.count) {
        else -> ConceptModelBuilder("TestConcept$id", id, arrayOf()).build(lambda)
    }
    test("An 'empty' concept still has a Structure aspect"){
        val sut = sutBuilder {  }
        sut.aspects.shouldHaveSize(1)
        sut.structure.shouldNotBeNull()
    }
    test("Extends function adds parental concept to structure") {
        val sut = sutBuilder { extends("foobar") }
        sut.structure.extendsConcept shouldBe "foobar"
    }
    test("Root function adds root status to structure") {
        val sut = sutBuilder { root() }
        sut.structure.isRoot shouldBe true
    }
    test("Editor command adds an aspect"){
        val sut = sutBuilder { editor {} }
        sut.aspects shouldHaveSize 2 //always has the Structure aspect
    }
    test("Adding references should work?") {
        val sut = sutBuilder { reference("foo", "bar"){} }
        sut.structure.references shouldHaveSize 1
    }
    test("Adding child references should work?") {
        val sut = sutBuilder { add("foo", "bar"){} }
        sut.structure.children shouldHaveSize 1
    }
    listOf(
        ConceptModelBuilder::set to {sut: Concept -> sut.structure.properties },
        ConceptModelBuilder::setProperty to {sut: Concept -> sut.structure.properties },
        ::conceptBuilderAdd to {sut: Concept -> sut.structure.children },
        ConceptModelBuilder::addChild to {sut: Concept -> sut.structure.children }
    ).forEach { (fn,getList) ->
        test("Invoking ${fn.name} should add item to list") {
            val sut = sutBuilder {
                fn(this, "testKey", "testValue")
            }
            getList(sut) shouldHaveSize 1
        }
    }

})