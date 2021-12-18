package gameInstances.movables

import gameInstances.World
import gameInstances.states.enums.IType
import gameInstances.states.enums.VertState
import graphicInstances.Size
import graphicInstances.VectorD
import graphicInstances.VectorInt

class MovableWall(halfSize: Size, pos: VectorD, tile: Size, private val minSize: Size, private val dif: VectorInt):
        Movable("", IType.SOLID, halfSize, pos, tile,false,  VertState.NOT_FALLING) {
    private val maxSize = halfSize

    fun check(world: World) {
        world.clearPoses(this)
        var isActive = true
        for (elem in movables) {
            if (!(elem as Mechanism).isActive)
                isActive = false
        }
        if (isActive) {
            val prevSize = halfSize
            halfSize = halfSize.max(dif, minSize)
            pos += (halfSize - prevSize).toDouble()
        } else {
            val prevSize = halfSize
            halfSize = halfSize.min(dif * (-1), maxSize)
            pos += (halfSize - prevSize).toDouble()
        }
        world.fillPoses(this)
    }
}