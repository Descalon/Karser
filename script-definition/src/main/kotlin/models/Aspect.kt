package models

abstract class Aspect(val parent: Concept) {
    val parentLanguage
        get() = parent.parent
}
