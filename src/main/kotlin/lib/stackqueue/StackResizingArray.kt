package lib.stackqueue

class StackResizingArray<T> : Iterable<T> {
    private var pointer = 0
    private var stack: Array<in Any?> = Array(1, { null })
    val size
        get() = pointer

    fun isEmpty(): Boolean = pointer == 0

    fun push(newItem: T) {
        if (pointer == stack.size) resize(2 * pointer)
        stack[pointer++] = newItem
    }

    fun pop(): T {
        if (isEmpty()) throw NoSuchElementException("Stack is empty.")
        val item = stack[--pointer]
        stack[pointer] = null
        if (pointer == stack.size / 4) {
            if (stack.size == 1) {
                resize(2)
            } else {
                resize(stack.size / 2)
            }
        }
        @Suppress("UNCHECKED_CAST") return item as T
    }

    fun peek(): T {
        if (isEmpty()) throw NoSuchElementException("Stack is empty.")
        @Suppress("UNCHECKED_CAST") return stack[pointer - 1] as T
    }

    private fun resize(length: Int) {
        val copyStack: Array<in Any?> = Array(length, { null })
        for (i in 0..(pointer - 1)) copyStack[i] = stack[i]
        stack = copyStack
    }

    override fun iterator(): Iterator<T> = ReverseArrayIterator(pointer, stack)

    private class ReverseArrayIterator<T>(pointer: Int, private val stack: Array<Any?>) :
        Iterator<T> {
        private var currentPointer = pointer
        override fun hasNext(): Boolean = currentPointer != 0
        @Suppress("UNCHECKED_CAST")
        override fun next(): T = stack[--currentPointer] as T
    }
}