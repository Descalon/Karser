package models

abstract class Aspect(val parent: Concept) : IModel, INode {
    override val id: Int
        get() = parent.getIdForModel(this)
    override val role: String
        get() = ""
}

class AspectCollection : MutableSet<Aspect> {
    private val _collection = mutableSetOf<Aspect>()
    override fun add(element: Aspect): Boolean {
        if (_collection.any { it.javaClass == element.javaClass }) return false
        return _collection.add(element)
    }

    override fun addAll(elements: Collection<Aspect>) =
        elements.fold(false) { s, e -> s || add(e) }

    override val size
        get() = _collection.size

    override fun clear() = _collection.clear()

    override fun isEmpty(): Boolean = _collection.isEmpty()

    override fun containsAll(elements: Collection<Aspect>) =
        _collection.containsAll(elements)

    override fun contains(element: Aspect) = _collection.contains(element)

    override fun iterator(): MutableIterator<Aspect> = _collection.iterator()

    override fun retainAll(elements: Collection<Aspect>) = _collection.retainAll(elements.toSet())

    override fun removeAll(elements: Collection<Aspect>) = _collection.removeAll(elements.toSet())

    override fun remove(element: Aspect) = _collection.remove(element)

}
