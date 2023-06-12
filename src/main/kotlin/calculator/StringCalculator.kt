package calculator

import calculator.exception.DivideByZeroException
import calculator.exception.IllegalSymbolException

object StringCalculator {
    val symbolToFunction = mapOf(
        "+" to this::add,
        "-" to this::subtract,
        "*" to this::multiply,
        "/" to this::divide
    )

    fun calculate(arithmeticExpression: String): Float {
        val expression = Expression(arithmeticExpression)
        val result = expression.numbers.reduceIndexed { index, acc, s ->
            doCalculate(acc, s, expression.arithmeticSymbols[index - 1])
        }

        return result
    }

    private fun doCalculate(num1: Float, num2: Float, operator: String): Float {
        val operatorFunction = symbolToFunction[operator] ?: throw IllegalSymbolException()
        return operatorFunction.invoke(num1, num2)
    }

    fun add(num1: Float, num2: Float): Float = num1 + num2

    fun subtract(num1: Float, num2: Float): Float = num1 - num2

    fun multiply(num1: Float, num2: Float): Float = num1 * num2

    fun divide(num1: Float, num2: Float): Float {
        if (num2 == 0F) {
            throw DivideByZeroException()
        }

        return num1 / num2
    }
}
