package controller

import gameInstances.World
import gameInstances.states.ActionKeys
import java.awt.Color
import java.awt.Graphics2D

class StateController(world: World) {
    fun action(actions: ActionKeys) {

    }

    fun paint(g2: Graphics2D?, actions: ActionKeys) {
        g2?.color = Color.BLUE
        g2?.drawOval(actions.mousePos.x, actions.mousePos.y, 3, 3)
    }
}