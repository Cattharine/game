package graphicInstances

class VectorD(var x: Double, var y: Double): Vector<Double> {
    operator fun plus(other: VectorD) = VectorD(x + other.x, y + other.y)
    operator fun minus(other: VectorD) = VectorD(x - other.x, y - other.y)
    operator fun times(value: Double) = VectorD(x * value, y * value)
    operator fun div(value: Double) = VectorD(x / value, y / value)
    operator fun minus(other: VectorInt) = VectorD(x - other.x, y - other.y)
    operator fun plus(other: VectorInt) = VectorD(x + other.x, y + other.y)

    fun toInt() = VectorInt(x.toInt(), y.toInt())

    override fun toString(): String {
        return "$x, $y"
    }
}

open class VectorInt(var x: Int, var y: Int): Vector<Int> {
    open operator fun times(value: Int) = VectorInt(x * value, y * value)
    open operator fun div(value: Int) = VectorInt(x / value, y / value)

    fun toDouble() = VectorD(x.toDouble(), y.toDouble())

    override fun toString(): String {
        return "$x, $y"
    }
}

interface Vector<T: Number>