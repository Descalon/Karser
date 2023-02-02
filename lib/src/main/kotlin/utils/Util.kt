package utils

import kotlin.math.abs

val DataTypeMap = mapOf(
    "string" to "fKAOsGN",
    "integer" to "fKAQMTA",
    "BaseConcept" to "gw2VY9q",
    "INamedConcept" to "h0TrEE\$"
)

fun String.toMPSIDNumber() = "${abs(this.hashCode())}"
