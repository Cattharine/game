package gameInstances.states.enums

enum class VertState {
    JUMPING,
    FALLING,
    STANDING;

    fun getValue(): Double = when(this) {
        JUMPING -> 1.0
        FALLING -> -1.0
        else -> 0.0
    }
}

