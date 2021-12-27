package gameInstances.states

import gameInstances.states.enums.VertState
import java.io.Serializable

class MState(val isAvailable: Boolean, initialVertState: VertState): Serializable {
    var vertState: VertState = initialVertState
}