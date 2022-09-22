package models

abstract class Aspect(val parent: Concept) : IModel{
    val parentLanguage
        get() = parent.parent
}
