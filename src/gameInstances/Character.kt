package gameInstances

import gameInstances.states.ActionKeys
import gameInstances.states.enums.IType
import graphicInstances.Size
import graphicInstances.VectorD

class Character(halfSize:Size, pos: VectorD) : Movable("character", IType.SOLID, halfSize, pos, true) {
    fun act(actions: ActionKeys, world: World) {
        move(actions.hor, actions.vert, world)
    }
}