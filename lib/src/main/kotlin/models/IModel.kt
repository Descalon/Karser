package models

interface IModel{
    fun resolveWith(context:IModel){
        throw Exception("not implemented for this type")
    }
}

interface IResolver: IModel {
    fun resolveFor(context:IModel): IModel? =
        throw Exception("not implemented for this type")
    fun resolve()
}