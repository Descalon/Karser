package validation

import models.*
import utils.BuiltInDefinitions

class Validator {
    fun validate(raw: script.models.Language) = Language(raw.name).apply {
        raw.concepts.mapIndexed { index, concept ->
            transform("s$index", this, concept)
        }.let { conceptClasses.addAll(it) }
        concepts.zip(raw.concepts).forEach { (concept, node) ->
            resolve(concept, node, this)
            node.aspects.map {
                when(it) {
                    is script.models.aspects.Editor -> EditorValidator(concept).resolve(it)
                    else -> throw Exception("The fuck is $it?")
                }
            }.apply { concept.aspects.addAll(this) }
        }
        initializeModelMap()
    }

    private fun resolve(concept: Concept, node: script.models.Concept, ctx: Language) {
        node.children.mapIndexed { index, child ->
            resolve(child, ctx).run { transform("ch$index", this, child) }
        }.apply { concept.children.addAll(this) }
        node.references.mapIndexed { index, ref ->
            resolve(ref, ctx).run { transformRef("r$index", this, ref) }
        }.apply { concept.references.addAll(this) }
        node.interfaces.mapIndexed{i,intfc ->
            transform("ifc$i", ctx, intfc)
        }.apply { concept.interfaces.addAll(this) }
    }

    private fun resolve(node: script.models.Concept.Reference, ctx: Language) =
        ctx.concepts.find { it.name == node.type } ?: throw Exception("Unresolved reference ${node.type}")

    private fun transformRef(id: String, type: Concept, prop: script.models.Concept.Reference) =
        Concept.Reference(id, prop.name, type, prop.isOptional)

    private fun transform(id: String, type: Concept, prop: script.models.Concept.ChildReference) =
        Concept.ChildReference(id, prop.name, type, prop.isOptional, prop.isSingleton)

    private fun transform(id: String, parent: Concept, prop: script.models.Concept.Property) = ConceptProperty(id, parent, prop.name, prop.type)
    private fun transform(id: String, parent: Language, concept: script.models.Concept): Concept {
        val baseConceptReference = BuiltInDefinitions.ConceptReferences.BaseConcept
        val retval =  Concept(id, parent, concept.name, concept.isRoot, baseConceptReference, mutableListOf())
        retval.properties.addAll(
            concept.properties.mapIndexed{ index, property ->
                transform("p$index", retval, property)
            }
        )
        return retval
    }

    private fun transform(id: String, ctx: Language, intfc: script.models.Concept.InterfaceConceptReference)  =
        ctx.resolveLanguage(intfc.packageName)
            .findInterface { it.name == intfc.name }
            .let { InterfaceConceptReference.fromInterface(id,it) }
}

