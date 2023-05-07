import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import validation.Validator

val validLang = language("TestLanguage"){
    concept("TestConcept"){
        implements("INamedConcept")

    }
}

val invalidLang = language("TestLanguage"){
    concept("TestConcept"){
        implements("NonExistantInterface")

    }
}

class ValidatorTests: ShouldSpec({
    should("validate interface"){
    }
})