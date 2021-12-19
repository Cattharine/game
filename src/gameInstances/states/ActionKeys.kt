package gameInstances.states

import gameInstances.states.enums.ActionButton
import gameInstances.states.enums.Dir
import graphicInstances.VectorInt

class ActionKeys(var hor: Dir, var vert: Dir, var action: ActionButton,
                 var mousePos: VectorInt, var grabbingObject: Boolean, var teleporting: Boolean) {
    fun isActing() = action == ActionButton.ACTION
}
//hor = horizontal, vert = vertical