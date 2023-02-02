package script.models

class Concept(val name:String) {
    val aspects: MutableList<Aspect> = mutableListOf()

    val properties: MutableList<Property> = mutableListOf()
    val children: MutableList<ChildReference> = mutableListOf()
    val references: MutableList<Reference> = mutableListOf()

    val interfaces: MutableList<InterfaceConceptReference> = mutableListOf()

    var isRoot: Boolean = false

    class Property(val name: String, val type: String)
    open class Reference(val name: String, val type:String, val isOptional: Boolean = false)
    class ChildReference(name: String, type: String, isOptional: Boolean = false, val isSingleton: Boolean = false): Reference(name, type, isOptional)

    class InterfaceConceptReference(val name: String, val packageName: String)
}