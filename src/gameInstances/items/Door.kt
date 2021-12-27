package gameInstances.items

import gameInstances.levels.Level
import gameInstances.states.enums.IType
import graphicInstances.VectorD

class Door(areaNum: Int): Item(ItemName.EMPTY, IType.DOOR, areaNum) {
    lateinit var nextLevel: Level
    lateinit var connectedDoor: Door
    lateinit var exitPos: VectorD
}