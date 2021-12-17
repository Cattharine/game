package gameInstances.states

import gameInstances.states.enums.VertState

class MState(val isAvailable: Boolean, initialVertState: VertState) {
    var vertState: VertState = initialVertState
}