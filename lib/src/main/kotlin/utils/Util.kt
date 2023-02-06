package utils

import kotlin.math.abs

fun String.toMPSIdAttribute() = "${abs(this.hashCode())}"

