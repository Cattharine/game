package gameInstances

import gameInstances.states.ActionKeys
import gameInstances.states.enums.Dir
import gameInstances.states.enums.IType
import gameInstances.states.enums.VertState
import graphicInstances.Size
import graphicInstances.VectorD
import graphicInstances.VectorInt
import kotlin.math.max
import kotlin.math.min

class World(val tile : Size) {
    val currentLevel = Level(tile)
    val character = Character(Size(9, 9), VectorD(190.0, 270.0), tile)
    var activeMovable : Movable? = null

    fun update(actions: ActionKeys) {
        for (elem in currentLevel.movable) {
            elem.checkFall(this)
            elem.move(Dir.NO, Dir.NO, this)
        }
        character.checkFall(this)
        changeActive(actions)
        character.act(activeMovable, actions, this)
    }

    private fun changeActive(actions: ActionKeys) {
        if (actions.mouseClicked && activeMovable == null) {
            val pos = VectorInt(actions.mousePos.x / tile.width, actions.mousePos.y / tile.height)
            val item = currentLevel.tryGetItem(pos.x, pos.y)
            when(item?.type) {
                IType.EMPTY -> when {
                    item.movables.isEmpty() -> {}
                    else -> {
                        item.movables.forEach { movable ->
                            if (movable.isPointIn(actions.mousePos) && !movable.state.isActive){
                                activeMovable = movable
                                movable.state.vertState = VertState.NOT_FALLING
                            }}}
                }
                else -> {}
            }
        } else if (actions.mouseClicked) {
            activeMovable?.state?.vertState = VertState.FALLING
            activeMovable = null
        }
    }

    fun checkInterX(startY: Int, endY: Int, startX: Int, endX: Int) =
            checkInter(startX, endX, startY, endY, getCurInter = {main, other -> getInter(main, other)})

    fun checkInterY(startX: Int, endX: Int, startY: Int, endY: Int) =
        checkInter(startY, endY, startX, endX, getCurInter = {main, other -> getInter(other, main)})


    private fun checkInter(mainStart: Int, mainEnd: Int,
                           otherStart: Int, otherEnd: Int,
                           getCurInter: (Int, Int) -> (Pair<Item, VectorInt>?)): ArrayList<Pair<Item, VectorInt>> {
        val res = ArrayList<Pair<Item, VectorInt>>()
        val mStart = min(mainStart, mainEnd)
        val mEnd = max(mainStart, mainEnd)
        (mStart .. mEnd).forEach { main ->
            (otherStart .. otherEnd).forEach { other ->
                val inter = getCurInter(main, other)
                if (inter != null) res.add(inter)
            }
        }
        return res
    }

    private fun getInter(x: Int, y: Int) = when(currentLevel.getType(x, y)) {
        IType.SOLID -> Pair(currentLevel.getItem(x, y), VectorInt(x, y))
        IType.EMPTY -> {
            if (currentLevel.getItem(x, y).movables.isNotEmpty())
                Pair(currentLevel.getItem(x, y), VectorInt(x, y))
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