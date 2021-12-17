package gameInstances

import gameInstances.movables.Movable
import gameInstances.states.enums.IType

open class Item(val name: String, val type: IType) {
    val movables = ArrayList<Movable>()
}