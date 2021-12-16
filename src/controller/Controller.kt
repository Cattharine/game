package controller

import gameInstances.states.enums.IType
import gameInstances.World
import gameInstances.states.ActionKeys
import gameInstances.states.enums.Dir
import graphicInstances.Size
import java.awt.event.ActionEvent
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.*
import javax.swing.AbstractAction
import javax.swing.JPanel
import javax.swing.Timer
import kotlin.system.exitProcess


class Controller : JPanel() {
    var actions = ActionKeys(Dir.NO, Dir.NO, isActing = false, isMap = false)
    val keys = HashSet<Int>()
    val world = World(Size(20, 20))
    var clear = false
    var t = false
    var br = false

    private val timer = Timer(10, object : AbstractAction() {
        override fun actionPerformed(e: ActionEvent) {
            br = checkBr()
            if (!br) {
                clear = false
                clearTrail()
                actions.hor = getHorDir()
                actions.vert = getVertDir()
                actions.isActing = getAct()
                world.update(actions)
                invalidate()
                repaint()
            }
        }
    })

    init {
        isFocusable = true
        isDoubleBuffered = true
        br = false
        timer.start()
        addKL()
    }

    private fun addKL() {
        addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                if (e.keyCode == KeyEvent.VK_ESCAPE) {
                    isVisible = false
                    exitProcess(0)
                }
                keys.add(e.keyCode)
            }

            override fun keyReleased(e: KeyEvent) {
                keys.remove(e.keyCode)
            }
        })
    }

    fun clearTrail() {
        if (KeyEvent.VK_P in keys) {
            t = false
            clear = true
            invalidate()
            repaint()
        }
    }

    fun checkBr() = KeyEvent.VK_SPACE in keys

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

    fun getAct() : Boolean = KeyEvent.VK_E in keys

    override fun paint(g: Graphics?) {
        val g2 = g as Graphics2D?
        if (clear) {
            g2?.fillRect(0, 0, width, height)
        }

        drawMap(g2)
        drawMovable(g2)
        drawCharacter(g2)
    }

    private fun drawMap(g2: Graphics2D?) {
        val map = world.currentLevel.map
        val width = world.tile.width
        val height = world.tile.height
        map.indices.forEach { y -> map[y].indices.forEach { x ->
            when (map[y][x].type) {
                IType.SOLID -> g2?.color = Color.GRAY
                else -> g2?.color = Color.BLACK
            }
            if (!t) {
                g2?.fillRect(x * width, y * height, width, height)
            }
        }}
//        t = true
    }

    private fun drawMovable(g2: Graphics2D?) {
        for (elem in world.currentLevel.movable) {
            val size = elem.halfSize * 2
            g2?.color = Color.getHSBColor(0.3f, 0.7f, 0.45f)
            val pos = (elem.pos - elem.halfSize).toInt()
//            g2?.drawOval(pos.x, pos.y, size.width, size.height)
            g2?.drawRect(pos.x, pos.y, size.width, size.height)
        }
    }

    private fun drawCharacter(g2: Graphics2D?) {
        val character = world.character
        val size = character.halfSize * 2
        g2?.color = Color.cyan
        val pos = (character.pos - character.halfSize).toInt()
//        g2?.drawOval(pos.x, pos.y, size.width, size.height)
        g2?.drawRect(pos.x, pos.y, size.width, size.height)

        g2?.color = Color.BLACK
        g2?.fillRect(400, 400, 600, 600)
        g2?.color = Color.WHITE
        g2?.drawString(character.pos.toString(), 500, 500)
        g2?.drawString(character.pos.toInt().toString(), 500, 600)
        g2?.drawString((character.pos.toInt() / world.tile.width).toString(), 500, 700)
    }
}


//        val width = width
//        val height = height
//        val k = width / world.tile.width
//        val l = height / world.tile.height
//        (0 .. k + 1).forEach { g2?.drawLine(it * world.tile.width, 0,
//            it * world.tile.width, height) }
//        (0 .. l + 1).forEach { g2?.drawLine(0, it * world.tile.height,
//            width, it * world.tile.height) }