package gameInstances

import gameInstances.states.ActionKeys
import gameInstances.states.enums.Dir
import gameInstances.states.enums.IType
import gameInstances.states.enums.VertState
import graphicInstances.Size
import graphicInstances.VectorD

class Character(halfSize:Size, pos: VectorD, tile: Size) :
        Movable("character", IType.SOLID, halfSize, pos, tile,true) {
    var movable: Movable? = null

    fun act(actions: ActionKeys, world: World) {
        if (movable == null)
            move(actions.hor, actions.vert, world)
        else {
            move(Dir.NO, Dir.NO, world)
            movable?.move(actions.hor, actions.vert, world)
        }
    }

    fun setMovable(item: Item?, actions: ActionKeys) {
        if (movable == null) {
            when(item?.type) {
                IType.EMPTY -> when {
                    item.movables.isEmpty() -> {}
                else -> {
                    item.movables.forEach { movable ->
                        if (movable.isPointIn(actions.mousePos) && !movable.state.isActive){
                            this.movable = movable
                            movable.state.vertState = VertState.NOT_FALLING
                        }}}
            }
            else -> {}
            }
        }
        else {
            movable?.state?.vertState = VertState.FALLING
            movable = null
        }
    }
}