package controller

import gameInstances.World
import gameInstances.items.movables.Movable
import gameInstances.states.ActionKeys
import gameInstances.states.enums.IType
import graphicInstances.VectorInt
import java.awt.Color
import java.awt.Graphics2D
import kotlin.math.max
import kotlin.math.min

class GameController(private val world: World) {
    var offset = VectorInt(0 ,0)
    var outScreenX = true
    var outScreenY = true

    fun drawGameField(g2: Graphics2D?, actions: ActionKeys, width: Int, height: Int, mapC: MapController) {
        setOffset(width, height)
        mapC.offset = VectorInt(offset.x, offset.y)
        mapC.currentLevel = world.currentLevel
        mapC.doors.clear()

        drawMapAll(g2)
        drawMovable(g2)
        drawMechanisms(g2)
        drawMovableWalls(g2)
        drawCharacter(g2)
        drawFragments(g2)
        g2?.color = Color.BLUE
        g2?.drawOval(actions.mousePos.x + (if (outScreenX) -1 else 1) * offset.x,
                actions.mousePos.y + (if (outScreenY) -1 else 1) * offset.y, 3, 3)
    }

    private fun setOffset(width: Int, height: Int) {
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

    private fun drawFragments(g2: Graphics2D?) {
        world.currentLevel.fragments
                .forEach { if (!it.checked) drawElem(g2, Color.getHSBColor(13f, 100.0f, 82.0f), it) }
    }

    private fun drawCharacter(g2: Graphics2D?) {
        val character = world.character
        val size = character.halfSize * 2
        val pos = (character.pos - character.halfSize).toInt()
        g2?.color = Color.cyan
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