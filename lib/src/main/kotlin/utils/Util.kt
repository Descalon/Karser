package utils

import models.Aspect
import models.Editor
import models.Structure
import writers.EditorWriter
import writers.StructureWriter
import kotlin.math.abs

val DataTypeMap = mapOf(
    "string" to "fKAOsGN",
    "integer" to "fKAQMTA",
    "BaseConcept" to "gw2VY9q"
)

fun String.toMPSIDNumber() = "${abs(this.hashCode())}"
