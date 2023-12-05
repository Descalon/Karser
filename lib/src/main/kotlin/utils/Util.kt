package utils

import models.*
import java.util.*
import kotlin.math.abs

fun String.toMPSIdAttribute() = "${abs(this.hashCode())}"

class BuiltInDefinitions {
    class BuiltInLanguage(
        name: String,
        uuid: UUID,
    ) : Language(name, uuid){

        override val defaultImports: List<LanguageReference>
            get() = listOf()
        companion object Values {
            val Core = BuiltInLanguage(
                "jetbrains.mps.lang.core",
                UUID.fromString("00000000-0000-4000-0000-011c89590288")
            ).apply {
                conceptClasses.addAll(listOf(
                    AbstractConcept("gw2v91", "BaseConcept", this),
                    InterfaceConcept("h0trEE$","INamedConcept", this).apply {
                     properties.add( ConceptProperty("p0", this, "name", "")) }))
//                modelMap["structure"] =  ModelReference("${name}.structure", listOf(
//                    AspectReference("string", "fKAOsGN"),
//                    AspectReference("integer", "fKAQMTA"),
//                    AspectReference("BaseConcept", "gw2VY9q"),
//                    AspectReference("INamedConcept", "h0TrEE\$"))
//                )
            }
            val BaseLanguage = BuiltInLanguage(
                "jetbrains.mps.baseLanguage",
               UUID.fromString("00000000-0000-4000-0000-011c895902ca")
            )
            val Structure = BuiltInLanguage(
                "jetbrains.mps.lang.structure",
                UUID.fromString("")
            ).apply {
                conceptClasses.addAll(listOf(
                    AbstractConcept("s0","AbstractConceptDeclaration", this).apply{
                        properties.add(ConceptProperty("6714410169261853888", this,"conceptId", "IDNumber" ))

                    }
                ))
            }
            fun resolve(alias: String) = when (alias.lowercase()) {
                Core.name -> Core
                BaseLanguage.name -> BaseLanguage
                else -> throw Exception("can't resolve $alias")
            }
        }
    }

    class ConceptReferences {
        companion object Values {
            val BaseConcept = Concept.BaseConceptReference("BaseConcept", BuiltInLanguage.Core.findConcept { it.name == "BaseConcept" })
        }
    }
}