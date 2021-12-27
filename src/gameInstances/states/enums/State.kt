package gameInstances.states.enums

import java.io.Serializable

enum class VertState: Serializable {
    JUMPING,
    FALLING,
    STANDING,
    NOT_FALLING;

    fun getValue(): Double = when(this) {
        JUMPING -> 1.0
        FALLING -> -1.0
        else -> -1.0
    }
}

