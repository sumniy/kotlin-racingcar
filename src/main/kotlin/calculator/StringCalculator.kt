package calculator

class StringCalculator {
    private val symbolToFunctionMap = mapOf(
        "+" to this::add,
        "-" to this::subtract,
        "*" to this::multiply,
        "/" to this::divide
    )

    fun calculate(arithmeticExpression: String): Float {
        if (arithmeticExpression.isBlank()) {
            throw IllegalArgumentException("문자열이 비어있습니다.")
        }

        val splitedExpression = splitByBlank(arithmeticExpression)
        val numbers = splitedExpression.filter { isNumber(it) }
        val arithmeticSymbols = splitedExpression.filter { isOperator(it) }

        validate(splitedExpression, numbers, arithmeticSymbols)

        val result = numbers.map { it.toFloat() }
            .reduceIndexed { index, acc, s ->
                doCalculate(acc, s, arithmeticSymbols[index - 1])
            }

        println(result)
        return result
    }

    private fun validate(splitedExpression: List<String>, numbers: List<String>, arithmeticSymbols: List<String>) {
        if (splitedExpression.any { it !in allowedCharacter }) {
            throw IllegalArgumentException("숫자와 기호외의 문자가 포함되었습니다.")
        }

        if (numbers.size != arithmeticSymbols.size + 1) {
            throw IllegalArgumentException("숫자는 기호보다 1개 많아야 합니다.")
        }
    }

    private fun splitByBlank(arithmeticExpression: String): List<String> {
        return arithmeticExpression.split(blankRegex)
    }

    private fun isNumber(input: String): Boolean {
        return numberRegex.matches(input)
    }

    private fun isOperator(input: String): Boolean {
        return input in symbolToFunctionMap
    }

    private fun doCalculate(num1: Float, num2: Float, operator: String): Float {
        val operatorFunction = symbolToFunctionMap[operator] ?: throw IllegalArgumentException("기호가 올바르지 않습니다.")
        return operatorFunction.invoke(num1, num2)
    }

    fun add(num1: Float, num2: Float) = num1 + num2

    fun subtract(num1: Float, num2: Float) = num1 - num2

    fun multiply(num1: Float, num2: Float) = num1 * num2

    fun divide(num1: Float, num2: Float): Float {
        if (num2 == 0F) {
            throw IllegalArgumentException("0으로 나눌 수 없습니다.")
        }

        return num1 / num2
    }

    companion object {
        val blankRegex = Regex(" +")
        val numberRegex = Regex("\\d+(\\.\\d+)?")
        val allowedCharacter = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", "*", "/")
    }
}
