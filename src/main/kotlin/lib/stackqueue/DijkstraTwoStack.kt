package lib.stackqueue

// positive integers, +, *, no precedence order, no associativity
class DijkstraTwoStack {
    private val valueStack = StackResizingArray<Int>()
    private val operatorStack = StackResizingArray<Char>()

    fun eval(expression: String): Int {
        expression.forEach {
            when (it) {
                '(', ' ' -> {
                }
                '*', '+' -> operatorStack.push(it)
                ')' -> {
                    when (operatorStack.pop()) {
                        '*' -> valueStack.push(valueStack.pop() * valueStack.pop())
                        '+' -> valueStack.push(valueStack.pop() + valueStack.pop())
                    }
                }
                else -> valueStack.push(it.toInt() - 48)
            }
        }
        return valueStack.pop()
    }

    fun evalPostfix(expression: String): Int {
        expression.forEach {
            when (it) {
                '*' -> valueStack.push(valueStack.pop() * valueStack.pop())
                '+' -> valueStack.push(valueStack.pop() + valueStack.pop())
                else -> valueStack.push(it.toInt() - 48)
            }
        }
        return valueStack.pop()
    }
}