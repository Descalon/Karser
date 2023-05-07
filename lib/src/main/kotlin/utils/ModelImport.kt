package utils

import models.Language
import java.lang.Exception

abstract class ModelImport(val index: String, val guid: String, val packageName: String) {
    abstract val dataTypes: Map<String, String>
    fun typeReferenceString(type: String) = "$index:${dataTypes[type]}"

    override fun equals(other: Any?) = when (other) {
        is ModelImport -> other.guid == guid
        else -> super.equals(other)
    }

    override fun hashCode(): Int {
        var result = index.hashCode()
        result = 31 * result + guid.hashCode()
        result = 31 * result + packageName.hashCode()
        result = 31 * result + dataTypes.hashCode()
        return result
    }
}
