package gameInstances.states.enums

enum class Dir {
    RIGHT,
    LEFT,
    UP,
    DOWN,
    NO;

    fun getValue(): Double = when(this) {
        RIGHT, DOWN -> 1.0
        LEFT, UP -> -1.0
        else -> 0.0
    }

    fun getOpposite() = when(this) {
        RIGHT -> LEFT
        LEFT -> RIGHT
        DOWN -> UP
        UP -> DOWN
        NO -> NO
    }
}