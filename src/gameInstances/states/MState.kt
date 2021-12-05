package gameInstances.states

import gameInstances.states.enums.VertState

class MState(val isActive: Boolean) {
    var vertState: VertState = VertState.STANDING
}