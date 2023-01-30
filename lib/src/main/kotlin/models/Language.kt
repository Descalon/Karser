package models

import models.Concept


class Language(val name: String, val concepts: List<Concept>) {

    fun find(predicate: (Concept) -> Boolean) =
        concepts.find(predicate) ?: throw Exception("unresolved reference")
}

