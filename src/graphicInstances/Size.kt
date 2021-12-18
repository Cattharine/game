package graphicInstances

import kotlin.math.max
import kotlin.math.min

class Size(val width : Int, val height: Int): VectorInt(width, height) {
    override operator fun times(value: Int) = Size(x * value, y * value)
    override operator fun div(value: Int) = Size(x / value, y / value)
    operator fun plus (value: VectorInt) = Size(width + value.x, height + value.y)
    operator fun minus (value: VectorInt) = Size(width - value.x, height - value.y)

    fun max(dif: VectorInt, minVal: VectorInt) = Size(max(x - dif.x, minVal.x), max(y - dif.y, minVal.y))
    fun min(dif: VectorInt, maxVal: VectorInt) = Size(min(x - dif.x, maxVal.x), min(y - dif.y, maxVal.y))

    override fun equals(other: Any?): Boolean {
        if (other == null)
            return false
        if (other.javaClass != this.javaClass)
            return false
        val oth = other as Size
        return oth.height == height && oth.width == width
    }

    override fun hashCode(): Int {
        var result = width
        result = 31 * result + height
        return result
    }
}