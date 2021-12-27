package gameInstances.items

import java.io.Serializable

enum class ItemName: Serializable {
    EMPTY,
    CHARACTER;

    fun getName() = when(this) {
        EMPTY -> ""
        CHARACTER -> "character"
    }
}