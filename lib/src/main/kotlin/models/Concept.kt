package models

import utils.*

typealias LinkDeclaration = Indices.Structure.LinkDeclaration
class ConceptProperty(override val nodeID: String, val parent: AbstractConceptClass, val role: String, private val type: String): INode{
    override val conceptInstance: String =
        Indices.Structure.PropertyDeclaration.ConceptIndex
    override val conceptRole: String =
        Indices.Structure.AbstractConceptDeclaration.PropertyDeclaration

    override val defaultProperties: Map<String, String>
        get() = mapOf(
            Indices.Structure.PropertyDeclaration.Id to (role + type).toMPSIdAttribute(),
            Indices.Core.INamedConcept.Name to role
        )
    override val defaultReferences: List<Map<String,String>>
        get() = listOf(
            mapOf(
                "role" to Indices.Structure.PropertyDeclaration.DataType,
                "to" to "foo", //ModelImport.Structure.Core.typeReferenceString(type),
                "resolve" to type)
        )
}

abstract class AbstractConceptClass(
    val conceptID: String,
    val name: String,
    val properties: MutableList<ConceptProperty>,
    val parent: Language, ) {
    companion object BuiltIns {
        val DefaultLangauage = Language("default")
        val DefaultConcept = object: AbstractConceptClass(
            conceptID = "default",
            name = "",
            properties = mutableListOf(),
            parent = DefaultLangauage
        ){}
    }
}

class AbstractConcept(conceptID: String, name: String, parent: Language):
    AbstractConceptClass(conceptID, name, mutableListOf(), parent)

open class Concept internal constructor(
    conceptID: String,
    parent: Language,
    name: String,
    private val isRoot: Boolean,
    private val baseClass: BaseConceptReference,
    properties: MutableList<ConceptProperty>
): AbstractConceptClass(conceptID, name, properties, parent), INode {
    val aspects: MutableList<Aspect> = mutableListOf()
    val children: MutableList<ChildReference> = mutableListOf()
    val references: MutableList<Reference> = mutableListOf()
    val interfaces: MutableList<InterfaceConceptReference> = mutableListOf()

    private var additionalImports: MutableList<ModelImport> = mutableListOf()

    val imports: Set<ModelImport>
        get() = additionalImports.toSet()//apply { add(ModelImport.Structure.Core) }.toSet()
    override val nodeID: String = this.conceptID


    override val conceptInstance: String = Indices.Structure.ConceptDeclaration.ConceptIndex
    override val conceptRole: String = ""
    override val defaultProperties
        get() = mapOf(
            Indices.Structure.AbstractConceptDeclaration.ConceptId to (name.toMPSIdAttribute()),
            Indices.Core.INamedConcept.Name to name,
            Indices.Structure.ConceptDeclaration.Rootable to "$isRoot"
        )

    override val defaultReferences: List<Map<String,String>>
        get() {
            return listOf(
                    mapOf(
                        "role" to Indices.Structure.ConceptDeclaration.Extends,
                        "to" to baseClass.resolveReferenceString(),
                        "resolve" to baseClass.name))
        }

    fun resolveProperty(name: String) = properties.find { it.role == name }
        ?: interfaces.flatMap { it.reference.properties }.find { it.role == name }
        ?: throw Exception("unresolved reference")

    open class Reference(override val nodeID: String, val role: String, val type: Concept, val isOptional: Boolean = false): INode {
        protected open fun cardinalityString() = if(isOptional) null else "_1"
        override val conceptInstance: String
            get() = LinkDeclaration.ConceptIndex
        override val conceptRole: String
            get() = Indices.Structure.AbstractConceptDeclaration.LinkDeclaration

        override val defaultProperties: Map<String, String>
            get() {
                val cardinality = cardinalityString()?.let { mapOf(LinkDeclaration.SourceCardinality to it) } ?: mapOf()

                return mapOf(
                    LinkDeclaration.Role to role,
                    LinkDeclaration.LinkId to "$role${type.name}".toMPSIdAttribute(),
                ) + cardinality
            }
        override val defaultReferences: List<Map<String,String>>
            get() {
                val principleId = type.nodeID
                val principleName = type.name
                return listOf(
                    mapOf(
                        "role" to LinkDeclaration.Target,
                        "node" to principleId,
                        "resolve" to principleName
                    )
                )
            }
    }
    class ChildReference(id: String, role: String, type: Concept, isOptional: Boolean = false, val isSingleton: Boolean = false): Reference(id, role, type, isOptional) {
        override fun cardinalityString() = when {
            this.isSingleton && this.isOptional     -> null
            this.isOptional && !this.isSingleton    -> "fLJekj5/_0__n"
            !this.isOptional && this.isSingleton    -> "fLJekj4/_1"
            else                                    -> "fLJekj6/_1__n"
        }
        override val defaultProperties: Map<String, String>
            get() = super.defaultProperties + (LinkDeclaration.MetaClass to "fLJjDmT/aggregation")
    }

    abstract class ConceptReference(val name:String, val reference: AbstractConceptClass){
        fun resolveReferenceString() =
            "${reference.parent.name}:${reference.conceptID}"//import.typeReferenceString(name)
    }
    abstract class ConceptPropertyReference(){}
    abstract class ConceptChildReference(){}
    abstract class ConceptRefReference(){}

    class BaseConceptReference(name: String, reference: AbstractConceptClass): ConceptReference(name, reference){
        companion object Builtins {
            val EmptyReference = BaseConceptReference("", DefaultConcept)
        }
    }

}