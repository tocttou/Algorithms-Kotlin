package lib.directedgraphs

import lib.stackqueue.QueueLinkedList

abstract class Digraph<T> {
    protected abstract val adj: Array<QueueLinkedList<T>>
    abstract fun adj(v: Int): Iterable<T>
    abstract fun V(): Int
    abstract fun E(): Int
}