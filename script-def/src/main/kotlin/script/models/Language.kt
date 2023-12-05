package script.models

class Language(val name: String) {
    val concepts: MutableList<Concept> = mutableListOf()
    val primitives: MutableList<PrimitiveDataClass> = mutableListOf()
}