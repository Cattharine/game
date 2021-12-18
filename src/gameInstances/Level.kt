package gameInstances

import gameInstances.movables.Mechanism
import gameInstances.movables.Movable
import gameInstances.movables.MovableWall
import gameInstances.states.enums.IType
import graphicInstances.Size
import graphicInstances.VectorD
import graphicInstances.VectorInt

class Level(tile: Size) {
    private val lines = arrayOf(
        "s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s3|s3|s3|s3|s3|s3|s3|s3",
        "s1| 1| 1| 1| 1|s1| 1| 1|s1| 1| 1| 1|s3| 3| 3| 3| 3| 3| 3|s3",
        "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s3|s3| 3| 3| 3| 3|s3| 3| 3|s3",
        "s1|s1| 1| 1| 1| 1| 1|s1|s1| 2|s3| 3| 3| 3| 3| 3| 3| 3|s3|s3",
        "s1| 1| 1| 1|s1|s1|s1| 1|s1| 2|s3| 3| 3| 3| 3|s3| 3| 3| 3|s3",
        "s1|s1| 1| 1| 1| 1| 1| 1|s1| 2|s3|s3| 3| 3| 3| 3| 3|s3| 3|s3",
        "s1| 1| 1| 1| 1| 1| 1| 1| 1| 2| 2| 3| 3| 3| 3| 3| 3| 3| 3|s3",
        "s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4| 3|s4",
        "s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4",
        "s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4|s4",
        "s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4",
        "s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4|s4",
        "s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4",
        "s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4",
        "s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4")
    val map : List<List<Item>>
    val movable : ArrayList<Movable>
    val movableWalls: ArrayList<MovableWall>
    private val areaNum = 4
    val mechanisms: ArrayList<Mechanism>
    val areas: Array<Area> = Array(areaNum) { Area(-1) }

    init {
        map = lines.map { it
            .split("|")
            .map {char -> val area = Area(Integer.parseInt(char[1].toString()) - 1)
                if (areas[area.number].number == -1)
                    areas[area.number] = area
                when(char[0]) {
                's' -> Item("", IType.SOLID, area.number)
                ' ' -> Item("", IType.EMPTY, area.number)
                else -> Item("", IType.EMPTY, area.number)
            }}}
        movable = ArrayList()
        initializeMovables(tile)
        movableWalls = ArrayList()
        initializeMMovables(tile)
        mechanisms = ArrayList()
        initializeMechs(tile)
    }

    private fun initializeMovables(tile: Size) {
        movable.add(Movable("", IType.SOLID, Size(50, 15),
            VectorD(180.0, 185.0), tile))
        movable.add(Movable("", IType.SOLID, Size(8, 8),
            VectorD(190.0, 160.0), tile))
    }

    private fun initializeMMovables(tile: Size) {
        movableWalls.add(MovableWall(Size(10, 10), VectorD(210.0, 130.0),
            tile, Size(0, 0), VectorInt(0, 1)))
        movableWalls.add(MovableWall(Size(10, 10), VectorD(170.0, 130.0),
            tile, Size(0,0), VectorInt(0, 1)))
        movableWalls.add(MovableWall(Size(10, 10), VectorD(190.0, 70.0),
            tile, Size(0, 0), VectorInt(1, 0)))
    }

    private fun initializeMechs(tile: Size) {
        mechanisms.add(Mechanism(Size(10, 1), VectorD(330.0, 39.0), tile))
        mechanisms.add(Mechanism(Size(10, 1), VectorD(30.0, 59.0), tile))

        movableWalls[0].movables.add(mechanisms[0])
        movableWalls[1].movables.add(mechanisms[0])
        movableWalls[2].movables.add(mechanisms[1])
    }

    fun getType(x: Int, y: Int) = map[y][x].type

    fun getItem(x: Int, y: Int) = map[y][x]

    fun tryGetItem(x: Int, y: Int): Item? {
        return if (y < map.size && x < map[0].size) getItem(x, y)
        else null
    }
}