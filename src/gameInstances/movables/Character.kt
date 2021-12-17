package gameInstances.movables

import gameInstances.Item
import gameInstances.World
import gameInstances.states.ActionKeys
import gameInstances.states.enums.Dir
import gameInstances.states.enums.IType
import gameInstances.states.enums.VertState
import graphicInstances.Size
import graphicInstances.VectorD
import graphicInstances.VectorInt

class Character(halfSize:Size, pos: VectorD, tile: Size, private val minSize: Size) :
        Movable("character", IType.SOLID, halfSize, pos, tile,false) {
    var movable : Movable? = null
    private var dif = VectorInt(1, 1)
    private val maxCounter = 1000
    private var counter = maxCounter
    private val maxSize = halfSize

    fun act(actions: ActionKeys, world: World) {
        if (movable == null) {
            counter--
            if (counter == 0) {
                val prevPos = getDownY(true)
                halfSize = halfSize.min(dif * (-1), maxSize)
                counter = maxCounter
                pos.y = prevPos - halfSize.y
            }
            move(actions.hor, actions.vert, world)
        }
        else {
            move(Dir.NO, Dir.NO, world)
            movable?.move(actions.hor, actions.vert, world)
        }
    }

    fun setMovable(item: Item?, actions: ActionKeys) {
        if (movable == null)
            when(item?.type) {
                IType.EMPTY ->
                    if (item.movables.isNotEmpty())
                        item.movables.forEach { trySetMovable(it, actions) }
                else -> {}
            }
        else releaseMovable()
    }

    private fun trySetMovable(current: Movable, actions: ActionKeys) {
        if (current.hasPoint(actions.mousePos) &&
                current.state.isAvailable && movable == null && halfSize != minSize) {
            movable = current
            current.state.vertState = VertState.NOT_FALLING
            halfSize = halfSize.max(dif, minSize)
        }
    }

    private fun releaseMovable() {
        movable?.state?.vertState = VertState.FALLING
        movable = null
    }
}