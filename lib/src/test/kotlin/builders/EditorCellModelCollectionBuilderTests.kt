package builders

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import models.*

private val Concept.editor
    get() = this.aspects.first { it is Editor } as Editor

class EditorCellModelCollectionBuilderTests : FunSpec({
    fun editorOnly(init: EditorCellModelCollectionBuilder.() -> Unit): EditorCellModelCollection {
        val c = ConceptModelBuilder("TestConcept", arrayOf()).build {}
        return EditorCellModelCollectionBuilder(Editor(c)).build(init)
}

    fun concept(lambda: ConceptModelBuilder.() -> Unit) =
        ConceptModelBuilder("TestConcept", arrayOf()).build(lambda)

    test("Invoking constant should add component of type EditorConstant to list") {
        val value = "value"
        val sut = editorOnly {
            constant(value)
        }

        val actual = sut.components.find { it is EditorConstant } as EditorConstant
        actual shouldNotBe null
        actual.value shouldBe value
    }
    test("Invoking property should add component of type PropertyReferenceEditor to list") {
        val testKey = "testName"
        val testValue = "testValue"
        val sut = concept {
            set(testKey, testValue)
            editor {
                property(testKey)
            }
        }.editor.collection

        val actual = sut.components.find { it is PropertyReferenceEditor } as PropertyReferenceEditor
        actual shouldNotBe null
        actual.reference.key shouldBe testKey
        actual.reference.value shouldBe testValue
    }
    test("Invoking property that doesn't exist in parent should throw an exception") {
        val exception = shouldThrowExactly<IllegalArgumentException> { editorOnly { property("someValue") } }
        exception.message shouldBe "Reference not found"
    }
    test("Invoking list should add component of type ListDeclarationEditor to list") {
        val value = "value"
        val sut = editorOnly {
            list(value)
        }

        val actual = sut.components.find { it is ListDeclarationEditor } as ListDeclarationEditor
        actual shouldNotBe null
        actual.reference shouldBe value
    }
    test("Invoking child should add component of type ChildIncludeEditor to list") {
        val value = "value"
        val sut = editorOnly {
            child(value)
        }

        val actual = sut.components.find { it is ChildIncludeEditor } as ChildIncludeEditor
        actual shouldNotBe null
        actual.reference shouldBe value
    }
    test("Invoking referent should add component of type ChildPropertyReferenceEditor to list") {
        val value = "value"
        val property = "property"
        val sut = editorOnly {
            referent(value, property)
        }

        val actual = sut.components.find { it is ChildPropertyReferenceEditor } as ChildPropertyReferenceEditor
        actual shouldNotBe null
        actual.reference shouldBe value
        actual.childProperty shouldBe property
    }
    test("Invoking newline should add component of type NewLine to list") {
        val sut = editorOnly {
            newLine()
        }

        val actual = sut.components.find { it is NewLine } as NewLine
        actual shouldNotBe null
    }
    listOf(
        CollectionLayout.NONE,
        CollectionLayout.HORIZONTAL,
        CollectionLayout.VERTICAL,
        CollectionLayout.INDENT,
    ).forEach { collectionLayout ->
        test("Invoking layout should set collection layout to ${collectionLayout.name}") {
            val sut = editorOnly {
                layout(collectionLayout)
            }
            val actual = sut.components.find { it is EditorCellLayoutComponent } as EditorCellLayoutComponent
            actual.layout shouldBe collectionLayout

        }
    }
    test("Sub collection should have same parent as main collection"){
        val sut = editorOnly {
            collection {

            }
        }
        val child = sut.components.first() as EditorCellModelCollection
        sut.getEditor() shouldBe child.getEditor()
    }
})