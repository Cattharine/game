package gameInstances.items

import gameInstances.items.movables.Movable
import gameInstances.states.enums.IType
import java.io.Serializable

open class Item(val name: ItemName, val type: IType, val areaNum: Int) : Serializable {
    val movables = ArrayList<Movable>()

    fun hasSolidMovables(): Boolean {
        for (elem in movables) {
            if (elem.type == IType.SOLID)
                return true
        }
        return false
    }
}