package gameInstances.levels

import gameInstances.Area
import gameInstances.items.Door
import gameInstances.items.Item
import gameInstances.items.movables.Mechanism
import gameInstances.items.movables.Movable
import gameInstances.items.movables.MovableWall
import gameInstances.states.enums.IType
import graphicInstances.Size
import graphicInstances.VectorD

open class Level(areaNum: Int, val initialCharPos: VectorD) {
    var name = ""
    var lines = Array(1) {""}
    var map : List<List<Item>> = List(1) { List(1) { Item("", IType.EMPTY, -1) } }
    val movable : ArrayList<Movable> = ArrayList()
    val movableWalls: ArrayList<MovableWall> = ArrayList()
    val mechanisms: ArrayList<Mechanism> = ArrayList()
    val areas: Array<Area> = Array(areaNum) { Area(-1) }
    var finalCharPos = initialCharPos
    val doors = ArrayList<Door>()

    fun getType(x: Int, y: Int) = map[y][x].type

    fun getItem(x: Int, y: Int) = map[y][x]

    fun tryGetItem(x: Int, y: Int): Item? {
        return if (y >= 0 && y < map.size && x >= 0 && x < map[0].size) getItem(x, y)
        else null
    }
}