package gameInstances

import gameInstances.states.ActionKeys
import gameInstances.states.enums.Dir
import gameInstances.states.enums.IType
import graphicInstances.Size
import graphicInstances.VectorD
import kotlin.math.max
import kotlin.math.min

class World(val tile : Size) {
    val currentLevel = Level(tile)
    val character = Character(tile / 2, VectorD(190.0, 270.0), tile)

    fun update(actions: ActionKeys) {
        for (elem in currentLevel.movable) {
            elem.checkFall(this)
            elem.move(Dir.NO, Dir.NO, this)
        }
        character.checkFall(this)
        character.act(actions, this)
    }

    fun checkInterX(startY: Int, endY: Int, startX: Int, endX: Int): Pair<Item, Int> =
            checkInter(startX, endX, startY, endY, getCurInter = {main, other -> getInter(main, other, main)})

    fun checkInterY(startX: Int, endX: Int, startY: Int, endY: Int): Pair<Item, Int> =
        checkInter(startY, endY, startX, endX, getCurInter = {main, other -> getInter(other, main, main)})


    private fun checkInter(mainStart: Int, mainEnd: Int,
                           otherStart: Int, otherEnd: Int,
                           getCurInter: (Int, Int) -> (Pair<Item, Int>?)): Pair<Item, Int> {
        val mStart = min(mainStart, mainEnd)
        val mEnd = max(mainStart, mainEnd)
        (mStart .. mEnd).forEach { main ->
            (otherStart .. otherEnd).forEach { other ->
                val inter = getCurInter(main, other)
                if (inter != null) return inter
            }
        }
        return Pair(Item("", IType.EMPTY), -1)
    }

    private fun getInter(x: Int, y: Int, current: Int) = when(currentLevel.getType(x, y)) {
        IType.SOLID -> Pair(currentLevel.getItem(x, y), current)
        IType.EMPTY -> {
            if (currentLevel.getItem(x, y).movables.isNotEmpty())
                Pair(currentLevel.getItem(x, y), current)
            else null
        }}

    fun clearPoses(movable: Movable) = updatePoses(movable, action = { list, elem -> list.remove(elem) })


    fun fillPoses(movable: Movable) = updatePoses(movable, action = { list, elem -> list.add(elem) })

    private fun updatePoses(movable: Movable, action: (ArrayList<Movable>, Movable) -> Boolean) {
        val (left, right) = getMapX(movable)
        val ceil = getMapUp(movable)
        val floor = getMapDown(movable)
        (left .. right).forEach { x ->
            (ceil .. floor).forEach { y -> action(currentLevel.getItem(x, y).movables, movable) }
        }
    }

    private fun getMapX(movable: Movable) = Pair(getMapX(movable.getX(Dir.LEFT, true)),
            getMapX(movable.getX(Dir.RIGHT, false)))

    private fun getMapUp(movable: Movable) = getMapY(movable.getUpY(true))
    private fun getMapDown(movable: Movable) = getMapY(movable.getDownY(false))

    private fun getMapX(pos: Double) = pos.toInt() / tile.width
    private fun getMapY(pos: Double) = pos.toInt() / tile.height
}