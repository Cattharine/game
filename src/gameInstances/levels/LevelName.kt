package gameInstances.levels

enum class LevelName {
    EMPTY,
    LVL1,
    LVL2,
    LVL3;

    fun getName() = when(this) {
        LVL1 -> "lvl1"
        LVL2 -> "lvl2"
        LVL3 -> "lvl3"
        else -> ""
    }
}