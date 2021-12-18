package gameInstances.movables

import gameInstances.World
import gameInstances.states.ActionKeys
import gameInstances.states.enums.IType
import gameInstances.states.enums.VertState
import graphicInstances.Size
import graphicInstances.VectorD

class Mechanism(halfSize: Size, pos: VectorD, tile: Size):
        Movable("", IType.MECHANISM, halfSize, pos, tile,false, VertState.NOT_FALLING) {
    var isActive = false

    fun check(world: World, actions: ActionKeys, character: Movable) {
        world.clearPoses(this)
        if (actions.isActing && hasInterX(character, true) && hasInterY(character, true)) {
            isActive = !isActive
        }
        world.fillPoses(this)
    }
}