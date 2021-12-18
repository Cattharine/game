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
            regainSize()
            if (!actions.teleporting)
                usualMove(actions, world)
            else {
                world.clearPoses(this)
                tryToTeleport(actions, world)
                world.fillPoses(this)
            }
        }
        else {
            move(Dir.NO, Dir.NO, world)
            movable?.move(actions.hor, actions.vert, world)
        }
    }

    private fun usualMove(actions: ActionKeys, world: World) {
        move(actions.hor, actions.vert, world)
    }

    private fun regainSize() {
        counter--
        if (counter == 0) {
            val prevPos = getDownY(true)
            halfSize = halfSize.min(dif * (-1), maxSize)
            counter = maxCounter
            pos.y = prevPos - halfSize.y
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
            reduceSize()
        }
    }

    private fun reduceSize() {
        halfSize = halfSize.max(dif, minSize)
    }

    private fun releaseMovable() {
        movable?.state?.vertState = VertState.FALLING
        movable = null
    }

    private fun tryToTeleport(actions: ActionKeys, world: World) {
        if (state.vertState == VertState.STANDING) {
            val pos = VectorInt(actions.mousePos.x / tile.width, actions.mousePos.y / tile.height)
            val item = world.currentLevel.tryGetItem(pos.x, pos.y)
            when {
                item?.type == IType.EMPTY && !item.hasSolidMovables() && halfSize != minSize &&
                        world.canTeleportTo(item) -> {
                    this.pos = VectorD(
                        (pos.x * tile.width).toDouble() + halfSize.x,
                        (pos.y * tile.height).toDouble() + halfSize.y
                    )
                    reduceSize()
                }
                else -> usualMove(actions, world)
            }
        }
    }
}