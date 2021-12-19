package controller

import gameInstances.states.enums.IType
import gameInstances.World
import gameInstances.items.movables.Movable
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
import kotlin.math.max
import kotlin.math.min
import kotlin.system.exitProcess

class Controller : JPanel() {
    var actions = ActionKeys(Dir.NO, Dir.NO, action = ActionButton.NO,
            mousePos = VectorInt(0, 0), mouseClicked = false, teleporting = false)
    val keys = HashSet<Int>()
    val world = World(Size(20, 20))
    var offset = VectorInt(0 ,0)
    var outScreenX = true
    var outScreenY = true

    private val timer = Timer(10, object : AbstractAction() {
        override fun actionPerformed(e: ActionEvent) {
            actions.hor = getHorDir()
            actions.vert = getVertDir()
            when(actions.action) {
                ActionButton.NO, ActionButton.ACTION -> world.update(actions)
                ActionButton.MAP -> mapAction()
                ActionButton.SAVE -> world.save()
                ActionButton.LOAD -> world.load()
                ActionButton.MAIN_MENU -> mainMenuAction()
                ActionButton.STATE -> stateAction()
            }
            if (actions.action == ActionButton.ACTION)
                actions.action = ActionButton.NO
            actions.mouseClicked = false
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

    private fun mapAction() {
        
    }

    private fun mainMenuAction() {

    }

    private fun stateAction() {

    }

    private fun addKL() {
        addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                when (e.keyCode) {
                    KeyEvent.VK_ESCAPE -> {
                        isVisible = false
                        exitProcess(0)
                    }
                    KeyEvent.VK_E -> actions.action = ActionButton.ACTION
                    KeyEvent.VK_M -> actions.action = if (actions.action == ActionButton.NO)
                        ActionButton.MAP
                    else ActionButton.NO
                    KeyEvent.VK_F5 -> actions.action = ActionButton.SAVE
                    KeyEvent.VK_F6 -> actions.action = ActionButton.LOAD
                    else -> keys.add(e.keyCode)
                }
            }

            override fun keyReleased(e: KeyEvent) {
                keys.remove(e.keyCode)
            }
        })
    }

    private fun addMML() {
        addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseMoved(e: MouseEvent) {
                actions.mousePos = VectorInt(e.xOnScreen - (if (outScreenX) -1 else 1) * offset.x,
                        e.yOnScreen - (if (outScreenY) -1 else 1) * offset.y)
            }
        })
    }

    private fun addML() {
        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                if (e?.button == MouseEvent.BUTTON1)
                    actions.mouseClicked = true
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
            KeyEvent.VK_UP in keys -> Dir.JUMP
            else -> Dir.NO
        }
    }

    override fun paint(g: Graphics?) {
        val g2 = g as Graphics2D?

        g2?.color = if(actions.action == ActionButton.MAP)
            Color.getHSBColor(0.56f, 0.6f, 0.1f) else Color.LIGHT_GRAY
        g2?.fillRect(0, 0, width, height)

        if (actions.action != ActionButton.MAP) {
            drawGameField(g2)
        }
        else {
            drawMap(g2)
            drawCharacter(g2)
        }
    }

    private fun drawGameField(g2: Graphics2D?) {
        setOffset()

        drawMapAll(g2)
        drawMovable(g2)
        drawMechanisms(g2)
        drawMovableWalls(g2)
        drawCharacter(g2)
    }

    private fun setOffset() {
        val levelSize = world.getSizeOfCurrentLevel()
        val charPos = world.character.pos.toInt()
        outScreenX = levelSize.width > width
        outScreenY = levelSize.height > height
        val offsetX = when {
            outScreenX -> max(0, min(charPos.x - width / 2, levelSize.width - width))
            else -> width / 2 - levelSize.width / 2
        }
        val offsetY = when {
            outScreenY -> max(0, min(charPos.y - height / 2, levelSize.height - height))
            else -> height / 2 - levelSize.height / 2
        }
        offset = VectorInt(offsetX, offsetY)
    }

    private fun drawMap(g2: Graphics2D?) {
        //для каждого уровня
        val map = world.currentLevel.map
        val areas = world.currentLevel.areas
        val width = world.tile.width
        val height = world.tile.height

        map.indices.forEach { y -> map[y].indices.forEach { x ->
            when {
                !areas[map[y][x].areaNum].isChecked ->
                    g2?.color = Color.getHSBColor(0.56f, 0.6f, 0.1f)
                map[y][x].type == IType.SOLID -> g2?.color = Color.LIGHT_GRAY
                map[y][x].type == IType.DOOR -> g2?.color = Color.WHITE
                else -> g2?.color = Color.BLACK
            }
            g2?.fillRect(x * width + (if (outScreenX) -1 else 1) * offset.x,
                    y * height + (if (outScreenY) -1 else 1) * offset.y, width, height)
        }}

    }

    private fun drawMapAll(g2: Graphics2D?) {
        val map = world.currentLevel.map
        val width = world.tile.width
        val height = world.tile.height
        map.indices.forEach { y -> map[y].indices.forEach { x ->
            when (map[y][x].type) {
                IType.SOLID -> g2?.color = Color.LIGHT_GRAY
                IType.DOOR -> g2?.color = Color.white
                else -> g2?.color = Color.BLACK
            }
            g2?.fillRect(x * width + (if (outScreenX) -1 else 1) * offset.x,
                    y * height + (if (outScreenY) -1 else 1) * offset.y, width, height)
        }}
    }

    private fun drawMovableWalls(g2: Graphics2D?) {
        world.currentLevel.movableWalls
                .forEach { drawElem(g2, Color.getHSBColor(0.666f, 0.7f, 0.9f), it) }
    }

    private fun drawMechanisms(g2: Graphics2D?) {
        world.currentLevel.mechanisms
                .forEach { drawElem(g2, if (it.isActive) Color.green else Color.red, it) }
    }

    private fun drawMovable(g2: Graphics2D?) {
        world.currentLevel.movable
                .forEach { drawElem(g2, Color.getHSBColor(0.3f, 0.7f, 0.45f), it) }
    }

    private fun drawCharacter(g2: Graphics2D?) {
        val character = world.character
        val size = character.halfSize * 2
        val pos = (character.pos - character.halfSize).toInt()
        g2?.color = Color.cyan
//        drawElem(g2, Color.cyan, character)
        g2?.drawOval(pos.x + (if (outScreenX) -1 else 1) * offset.x,
                pos.y + (if (outScreenY) -1 else 1) * offset.y, size.width, size.height)
    }

    private fun drawElem(g2: Graphics2D?, color: Color, elem: Movable) {
        val size = elem.halfSize * 2
        g2?.color = color
        val pos = (elem.pos - elem.halfSize).toInt()
        g2?.drawRect(pos.x + (if (outScreenX) -1 else 1) * offset.x,
                pos.y + (if (outScreenY) -1 else 1) * offset.y, size.width, size.height)
    }
}