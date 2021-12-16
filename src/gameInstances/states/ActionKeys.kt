package gameInstances.states

import gameInstances.states.enums.Dir
import graphicInstances.VectorInt

class ActionKeys(var hor: Dir, var vert: Dir, var isActing: Boolean,
                 var isMap: Boolean, var mousePos: VectorInt, var mouseClicked: Boolean)
//hor = horizontal, vert = vertical