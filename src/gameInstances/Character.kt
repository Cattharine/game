package gameInstances

import gameInstances.states.ActionKeys
import gameInstances.states.enums.Dir
import gameInstances.states.enums.IType
import graphicInstances.Size
import graphicInstances.VectorD

class Character(halfSize:Size, pos: VectorD, tile: Size) :
        Movable("character", IType.SOLID, halfSize, pos, tile,true) {
    fun act(movable: Movable?, actions: ActionKeys, world: World) {
        if (movable == null)
            move(actions.hor, actions.vert, world)
        else {
            move(Dir.NO, Dir.NO, world)
            movable.move(actions.hor, actions.vert, world)
        }
    }
}