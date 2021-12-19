package gameInstances.items.movables

import gameInstances.World
import gameInstances.items.ItemName
import gameInstances.states.enums.IType
import gameInstances.states.enums.VertState
import graphicInstances.Size
import graphicInstances.VectorD
import graphicInstances.VectorInt

class MovableWall(val func: (List<Boolean>) -> Boolean,
                  halfSize: Size, pos: VectorD, tile: Size, private val minSize: Size, private val dif: VectorInt):
        Movable(ItemName.EMPTY, IType.SOLID, halfSize, pos, tile,false,  VertState.NOT_FALLING) {
    private val maxSize = halfSize
    private val values = ArrayList<(Boolean) -> (Boolean)>()

    fun check(world: World) {
        world.clearPoses(this)
        val isActive = func(movables.indices.map { values[it]((movables[it] as Mechanism).isActive) })
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

    fun addMechanism(mechanism: Mechanism, value: (Boolean) -> (Boolean)) {
        movables.add(mechanism)
        values.add(value)
    }
}