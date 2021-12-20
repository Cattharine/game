package controller

import gameInstances.states.ActionKeys
import gameInstances.states.enums.ActionButton
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import kotlin.system.exitProcess

class MainMenuController {
    private var exitColor = Color.WHITE
    private var gameColor = Color.WHITE

    fun action(actions: ActionKeys, height: Int) {
        if (actions.mousePos.y >= (height / 4 - 70) && actions.mousePos.y <= (height / 4 - 40)) {
            if (actions.grabbingObject)
                goToGame(actions)
            gameColor = Color.cyan
            exitColor = Color.WHITE
        }
        else if (actions.mousePos.y >= (height / 4 + 20) && actions.mousePos.y <= (height / 4 + 50)){
            if (actions.grabbingObject)
                exit()
            exitColor = Color.cyan
            gameColor = Color.WHITE
        }
        else {
            exitColor = Color.WHITE
            gameColor = Color.WHITE
        }
    }

    private fun goToGame(actions: ActionKeys) {
        actions.action = ActionButton.NO
    }

    private fun exit() {
        exitProcess(0)
    }

    fun paint(g2: Graphics2D?, height: Int, actions: ActionKeys) {
        g2?.color = gameColor
        g2?.font = Font(Font.MONOSPACED, Font.BOLD, 30)
        g2?.drawString("Играть", 0, height / 4 - 40)
        g2?.color = exitColor
        g2?.drawString("Выйти", 0, height / 4 + 40)
        g2?.color = Color.BLUE
        g2?.drawOval(actions.mousePos.x,actions.mousePos.y, 3, 3)
    }
}