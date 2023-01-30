package validation

import models.Concept
import models.Language

class Validator {
    fun validate(raw: script.models.Language) = raw.concepts.mapIndexed { index, concept ->
        transform("s$index", concept)
    }.let { Language(raw.name, it) }.apply {
        concepts.zip(raw.concepts).forEach { (concept, node) ->
            resolve(concept, node, this)
            node.aspects.map {
                when(it) {
                    is script.models.aspects.Editor -> EditorValidator(concept).resolve(it)
                    else -> throw Exception("The fuck is $it?")
                }
            }.apply { concept.aspects.addAll(this) }
        }
    }

    private fun resolve(concept: Concept, node: script.models.Concept, ctx: Language) {
        node.children.mapIndexed { index, child ->
            resolve(child, ctx).run { transform("ch$index", this, child) }
        }.apply { concept.children.addAll(this) }
        node.references.mapIndexed { index, ref ->
            resolve(ref, ctx).run { transform("r$index", this, ref) }
        }.apply { concept.references.addAll(this) }
    }

    private fun resolve(node: script.models.Concept.Reference, ctx: Language) =
        ctx.concepts.find { it.name == node.type } ?: throw Exception("Unresolved reference ${node.type}")

    private fun transform(id: String, type: Concept, prop: script.models.Concept.Reference) =
        Concept.Reference(id, prop.name, type, prop.isOptional)

    private fun transform(id: String, type: Concept, prop: script.models.Concept.ChildReference) =
        Concept.ChildReference(id, prop.name, type, prop.isOptional, prop.isSingleton)

    private fun transform(id: String, prop: script.models.Concept.Property) = Concept.Property(id, prop.name, prop.type)
    private fun transform(id: String, concept: script.models.Concept) =
        Concept(id, concept.name, concept.isRoot, concept.properties.mapIndexed { index, property ->
            transform("p$index", property)
        })

}

