package gameInstances.items.movables

import gameInstances.items.ItemName
import gameInstances.states.enums.Ability
import gameInstances.states.enums.IType
import gameInstances.states.enums.VertState
import graphicInstances.Size
import graphicInstances.VectorD

class Fragment(val ability: Ability, pos: VectorD, tile: Size, var checked: Boolean = false):
        Movable(ItemName.EMPTY, IType.FRAGMENT, tile / 2,
        pos, tile, isAvailable = false, initialVertState = VertState.NOT_FALLING)