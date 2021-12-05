package gameInstances

import gameInstances.states.enums.IType

class Level {
    private val lines = arrayOf("s|s|s|s|s|s|s|s|s|s|s|s|s|s|s|s|s|s|s|s",
                                "s| | | | |s| | |s| | | |s| | | | | | |s",
                                "s| | | | | | | | | |s|s| | | | |s| | |s",
                                "s|s| | | | | |s|s| |s| | | | | | | |s|s",
                                "s| | | |s|s|s| |s| |s| | | | |s| | | |s",
                                "s|s| | | | | | |s| |s|s| | | | | |s| |s",
                                "s| | | | | | | | | | | | | | | | | | |s",
                                "s|s|s|s|s|s|s|s|s|s|s|s|s|s|s|s|s|s| |s",
                                "s| | | | | | | | | | | | | | | | | | |s",
                                "s| | | | | | | | | | | | | | | | | |s|s",
                                "s| | | | | | | | | | | | | | | | | | |s",
                                "s| | | | | | | | | | | | | | | | | |s|s",
                                "s| | | | | | | | | | | | | | | | | | |s",
                                "s| | | | | | | | | | | | | | | | | | |s",
                                "s|s|s|s|s|s|s|s|s|s|s|s|s|s|s|s|s|s|s|s")
    val map : List<List<Item>>
    init {
        map = lines.map { it
            .split("|")
            .map {char -> when(char) {
                "s" -> Item("", IType.SOLID)
                " " -> Item("", IType.EMPTY)
                else -> Item("", IType.EMPTY)
            }}}
    }

    fun getType(x: Int, y: Int) = map[y][x].type
}