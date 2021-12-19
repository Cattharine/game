package gameInstances.items

import gameInstances.levels.Level
import gameInstances.states.enums.IType

class Door(areaNum: Int): Item(ItemName.EMPTY, IType.DOOR, areaNum) {
    lateinit var nextLevel: Level
}