package gameInstances

import gameInstances.states.enums.IType
import graphicInstances.Size
import graphicInstances.VectorD

class Level(tile: Size) {
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
    val movable : ArrayList<Movable>
    init {
        map = lines.map { it
            .split("|")
            .map {char -> when(char) {
                "s" -> Item("", IType.SOLID)
                " " -> Item("", IType.EMPTY)
                else -> Item("", IType.EMPTY)
            }}}
        movable = ArrayList()
        movable.add(Movable("", IType.SOLID, Size(50, 15),
                VectorD(180.0, 185.0), tile))
        movable.add(Movable("", IType.SOLID, Size(10, 10),
                VectorD(190.0, 160.0), tile))
    }

    fun getType(x: Int, y: Int) = map[y][x].type

    fun getItem(x: Int, y: Int) = map[y][x]
}