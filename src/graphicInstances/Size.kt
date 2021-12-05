package graphicInstances

class Size(val width : Int, val height: Int): VectorInt(width, height) {
    override operator fun times(value: Int) = Size(x * value, y * value)
    override operator fun div(value: Int) = Size(x / value, y / value)
}