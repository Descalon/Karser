package utils

import java.lang.Exception

abstract class ModelImport(val index: String, val guid: String, val packageName: String) {
    abstract val dataTypes: Map<String, String>
    fun typeReferenceString(type:String) = "$index:${dataTypes[type]}"

    override fun equals(other: Any?) = when(other){
        is ModelImport -> other.guid == guid
        else -> super.equals(other)
    }
    override fun hashCode(): Int {
        var result = index.hashCode()
        result = 31 * result + guid.hashCode()
        result = 31 * result + packageName.hashCode()
        result = 31 * result + dataTypes.hashCode()
        return result
    }

    abstract class Structure {
        companion object Default {
            val Core = object: ModelImport("ixc", "00000000-0000-4000-0000-011c89590288", "jetbrains.mps.core.structure"){
                override val dataTypes = mapOf(
                    "string" to "fKAOsGN",
                    "integer" to "fKAQMTA",
                    "BaseConcept" to "gw2VY9q",
                    "INamedConcept" to "h0TrEE\$"
                )
            }
            private val BaseLanguage = object: ModelImport("ixbl", "00000000-0000-4000-0000-011c895902ca", "jetbrains.mps.baseLanguage.structure"){
                override val dataTypes = mapOf(
                    "Expression" to "fz3vP1J"
                )
            }

            fun resolve(input: String) = when(input.lowercase()) {
                "core" -> Core
                "baselanguage" -> BaseLanguage
                else -> throw Exception("Can't find structure import for ${input}.")
            }
        }
    }
}