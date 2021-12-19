package gameInstances.items.movables

import gameInstances.items.ItemName
import gameInstances.states.enums.Ability
import gameInstances.states.enums.IType
import gameInstances.states.enums.VertState
import graphicInstances.Size
import graphicInstances.VectorD

class Fragment(val ability: Ability, pos: VectorD, tile: Size): Movable(ItemName.EMPTY, IType.FRAGMENT, tile / 2,
        pos, tile, initialVertState = VertState.NOT_FALLING)