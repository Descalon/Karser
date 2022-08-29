package models

open class Reference(var name: String, var type: String){
    var isOptional = false
        private set
    var isSingleton = false
        private set

    fun optional() = apply {isOptional = true}
    fun singleton() = apply {isSingleton = true}
}