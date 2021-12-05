package gameInstances.states.enums

enum class Dir {
    RIGHT,
    LEFT,
    JUMP,
    DOWN,
    NO;

    fun getValue(): Int = when(this) {
        RIGHT, DOWN -> 1
        LEFT, JUMP -> -1
        else -> 0
    }
}