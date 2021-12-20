package controller

import gameInstances.World
import gameInstances.states.ActionKeys
import gameInstances.states.enums.ActionButton
import gameInstances.states.enums.Dir
import graphicInstances.Size
import graphicInstances.VectorInt
import java.awt.*
import java.awt.event.*
import javax.swing.AbstractAction
import javax.swing.JPanel
import javax.swing.Timer
import kotlin.system.exitProcess

class Controller : JPanel() {
    var actions = ActionKeys(Dir.NO, Dir.NO, action = ActionButton.MAIN_MENU,
            mousePos = VectorInt(0, 0), grabbingObject = false, teleporting = false)
    val keys = HashSet<Int>()
    val world = World(Size(20, 20))
    val mapC = MapController(world)
    val gameC = GameController(world)
    val mainMenuC = MainMenuController()
    val stateC = StateController(world)

    private val timer = Timer(10, object : AbstractAction() {
        override fun actionPerformed(e: ActionEvent) {
            actions.hor = getHorDir()
            actions.vert = getVertDir()
            when(actions.action) {
                ActionButton.NO, ActionButton.ACTION -> world.update(actions)
                ActionButton.MAP -> mapC.mapAction(actions, width, height, gameC)
                ActionButton.SAVE -> saveAction(actions)
                ActionButton.LOAD -> loadAction(actions)
                ActionButton.MAIN_MENU -> mainMenuC.action(actions, height)
                ActionButton.STATE -> stateC.action(actions)
            }
            if (actions.action == ActionButton.ACTION)
                actions.action = ActionButton.NO
            actions.grabbingObject = false
            actions.teleporting = false
            invalidate()
            repaint()
        }
    })

    init {
        isFocusable = true
        isDoubleBuffered = true
        timer.start()
        addKL()
        addMML()
        addML()
    }

    private fun saveAction(actions: ActionKeys) {
        world.save()
    }

    private fun loadAction(actions: ActionKeys) {
        world.load()
    }

    private fun addKL() {
        addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                when (e.keyCode) {
                    KeyEvent.VK_ESCAPE -> actions.action = ActionButton.MAIN_MENU
                    KeyEvent.VK_E -> actions.action = ActionButton.ACTION
                    KeyEvent.VK_M -> updateMapState()
                    KeyEvent.VK_F5 -> actions.action = ActionButton.SAVE
                    KeyEvent.VK_F6 -> actions.action = ActionButton.LOAD
                    KeyEvent.VK_J -> updateCharStateState()
                    else -> keys.add(e.keyCode)
                }
            }

            override fun keyReleased(e: KeyEvent) {
                keys.remove(e.keyCode)
            }
        })
    }

    private fun updateMapState() {
        actions.action = when(actions.action) {
            ActionButton.NO -> ActionButton.MAP
            ActionButton.MAIN_MENU -> ActionButton.MAIN_MENU
            else -> ActionButton.NO
        }
    }

    private fun updateCharStateState() {
        actions.action = when(actions.action) {
            ActionButton.NO -> ActionButton.STATE
            ActionButton.MAIN_MENU -> ActionButton.MAIN_MENU
            else -> ActionButton.NO
        }
    }

    private fun addMML() {
        addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseMoved(e: MouseEvent) {
                when(actions.action) {
                    ActionButton.NO, ActionButton.ACTION -> actions.mousePos =
                            VectorInt(e.xOnScreen - (if (gameC.outScreenX) -1 else 1) * gameC.offset.x,
                                    e.yOnScreen - (if (gameC.outScreenY) -1 else 1) * gameC.offset.y)
                    ActionButton.MAP -> actions.mousePos = VectorInt(
                            e.xOnScreen - (if (mapC.outScreenX) -1 else 1) * mapC.offset.x,
                            e.yOnScreen - (if (mapC.outScreenY) -1 else 1) * mapC.offset.y)
                    else -> actions.mousePos = VectorInt(e.xOnScreen, e.yOnScreen)
                }
            }
        })
    }

    private fun addML() {
        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                if (e?.button == MouseEvent.BUTTON1)
                    actions.grabbingObject = true
                else actions.teleporting = true
            }
        })
    }

    fun getHorDir() : Dir {
        return when {
            KeyEvent.VK_RIGHT in keys == KeyEvent.VK_LEFT in keys -> Dir.NO
            KeyEvent.VK_RIGHT in keys -> Dir.RIGHT
            KeyEvent.VK_LEFT in keys -> Dir.LEFT
            else -> Dir.NO
        }
    }

    fun getVertDir() : Dir {
        return when {
            KeyEvent.VK_DOWN in keys == KeyEvent.VK_UP in keys -> Dir.NO
            KeyEvent.VK_DOWN in keys -> Dir.DOWN
            KeyEvent.VK_UP in keys -> Dir.UP
            else -> Dir.NO
        }
    }

    override fun paint(g: Graphics?) {
        val g2 = g as Graphics2D?

        g2?.color = when(actions.action) {
            ActionButton.MAP -> Color.getHSBColor(0.56f, 0.6f, 0.1f)
            ActionButton.ACTION, ActionButton.NO -> Color.LIGHT_GRAY
            else -> Color.BLACK
        }
        g2?.fillRect(0, 0, width, height)

        when(actions.action) {
            ActionButton.NO, ActionButton.ACTION -> gameC.drawGameField(g2, actions, width, height, mapC)
            ActionButton.MAP -> mapC.paint(g2, actions)
            ActionButton.STATE -> stateC.paint(g2, actions)
            ActionButton.MAIN_MENU -> mainMenuC.paint(g2, height, actions)
            else -> {}
        }
    }
}