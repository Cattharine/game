package gameInstances.items

enum class ItemName {
    EMPTY,
    CHARACTER;

    fun getName() = when(this) {
        EMPTY -> ""
        CHARACTER -> "character"
    }
}