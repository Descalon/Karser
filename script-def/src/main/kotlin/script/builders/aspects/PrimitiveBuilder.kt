package script.builders.aspects

import script.models.PrimitiveDataClass

class PrimitiveBuilder(name:String) {
    val principle = PrimitiveDataClass(name)

    internal fun build() = principle
}