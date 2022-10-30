package builders

import models.ChildReference
import models.Reference
import models.Structure

open class ReferenceBuilder(protected val parent: Structure): IModelBuilder<Reference> {
    protected var name = ""
    protected var type = ""
    protected var optional = false

    override val subject: Reference
        get() = Reference(parent, name, type, optional)

    fun name(input:String) = apply { name = input }
    fun type(input:String) = apply { type = input }

    fun isOptional() = apply { optional = true }
}

class ChildReferenceBuilder(parent: Structure): ReferenceBuilder(parent) {
    protected var singleton = false
    override val subject: ChildReference
        get() = ChildReference(parent, name, type, optional, singleton)
    fun isSingleton() = apply { singleton = true }
}