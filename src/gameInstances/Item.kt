package gameInstances

import gameInstances.movables.Movable
import gameInstances.states.enums.IType

open class Item(val name: String, val type: IType) {
    val movables = ArrayList<Movable>()

    fun hasSolidMovables(): Boolean {
        for (elem in movables) {
            if (elem.type == IType.SOLID)
                return true
        }
        return false
    }
}