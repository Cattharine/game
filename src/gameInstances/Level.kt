package gameInstances

import gameInstances.movables.Mech
import gameInstances.movables.Movable
import gameInstances.movables.MovableWall
import gameInstances.states.enums.IType
import graphicInstances.Size
import graphicInstances.VectorD
import graphicInstances.VectorInt

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
    val movableWalls: ArrayList<MovableWall>
    val mechs: ArrayList<Mech>
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
        movable.add(Movable("", IType.SOLID, Size(8, 8),
                VectorD(190.0, 160.0), tile))
        movableWalls = ArrayList()
        movableWalls.add(MovableWall(Size(10, 10), VectorD(210.0, 130.0),
                tile, Size(0, 0), VectorInt(0, 1)))
        movableWalls.add(MovableWall(Size(10, 10), VectorD(170.0, 130.0),
                tile, Size(0,0), VectorInt(0, 1)))
        movableWalls.add(MovableWall(Size(10, 10), VectorD(190.0, 70.0),
                tile, Size(0, 0), VectorInt(1, 0)))
        mechs = ArrayList()
        mechs.add(Mech(Size(10, 1), VectorD(330.0, 39.0), tile))
        mechs.add(Mech(Size(10, 1), VectorD(30.0, 59.0), tile))
        movableWalls[0].movables.add(mechs[0])
        movableWalls[1].movables.add(mechs[0])
        movableWalls[2].movables.add(mechs[1])
    }

    fun getType(x: Int, y: Int) = map[y][x].type

    fun getItem(x: Int, y: Int) = map[y][x]

    fun tryGetItem(x: Int, y: Int): Item? {
        return if (y < map.size && x < map[0].size) getItem(x, y)
        else null
    }
}