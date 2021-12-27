package controller

import gameInstances.World
import gameInstances.items.Door
import gameInstances.items.movables.Fragment
import gameInstances.items.movables.Movable
import gameInstances.states.ActionKeys
import gameInstances.states.enums.ActionButton
import gameInstances.states.enums.Dir
import gameInstances.states.enums.IType
import gameInstances.states.enums.VertState
import graphicInstances.VectorD
import graphicInstances.VectorInt
import java.awt.*

class MapController(private val world: World) {
    var currentLevel = world.currentLevel
    var offset = VectorInt(0, 0)
    var outScreenX = true
    var outScreenY = true
    var doors = ArrayList<Pair<Door, VectorInt>>()
    val speed = VectorInt(3, 3)

    fun mapAction(actions: ActionKeys, width: Int, height: Int, gameController: GameController) {
        if (actions.grabbingObject)
            tryChangeLevel(actions)
        else if (actions.teleporting)
            tryTeleport(actions, gameController)
        val levelSize = currentLevel.getSize(world.tile)
        outScreenX = levelSize.width > width
        outScreenY = levelSize.height > height
        changeOffsetX(actions.hor)
        changeOffsetY(actions.vert)
    }

    private fun tryChangeLevel(actions: ActionKeys) {
        val item = currentLevel.tryGetItem(actions.mousePos.x / world.tile.width,
                actions.mousePos.y / world.tile.height)
        if (item?.type == IType.DOOR) {
            currentLevel = (item as Door).nextLevel
            doors.clear()
            offset = VectorInt(0, 0)
            outScreenX = true
            outScreenY = true
        }
    }

    private fun tryTeleport(actions: ActionKeys, gameController: GameController) {
        if (world.character.state.vertState == VertState.STANDING) {
            val item = currentLevel.tryGetItem(actions.mousePos.x / world.tile.width,
                    actions.mousePos.y / world.tile.height)
            item?.movables?.forEach {
                if (it.type == IType.FRAGMENT && (it as Fragment).checked)
                    teleport(actions, it, gameController)
            }
        }
    }

    private fun teleport(actions: ActionKeys, fragment: Fragment, gameController: GameController) {
        world.currentLevel = currentLevel
        world.levels[currentLevel.name] = currentLevel
        world.character.pos = VectorD(fragment.pos.x, fragment.pos.y)
        gameController.offset = VectorInt(offset.x, offset.y)
        gameController.outScreenX = outScreenX
        gameController.outScreenY = outScreenY
        actions.action = ActionButton.NO
    }

    private fun changeOffsetX(hor: Dir) {
        offset.x = when(hor) {
            Dir.RIGHT -> if (outScreenX) offset.x + speed.x else offset.x - speed.x
            Dir.LEFT -> if (outScreenX) offset.x - speed.x else offset.x + speed.x
            else -> offset.x
        }
    }

    private fun changeOffsetY(vert: Dir) {
        offset.y = when(vert) {
            Dir.DOWN -> if (outScreenY) offset.y + speed.y else offset.y - speed.y
            Dir.UP -> if (outScreenY) offset.y - speed.y else offset.y + speed.y
            else -> offset.y
        }
    }

    fun paint(g2: Graphics2D?, actions: ActionKeys) {
        drawMap(g2)
        drawCharacter(g2)
        for (elem in doors) {
            writeNext(elem.first, g2, elem.second.x, elem.second.y, world.tile.width, world.tile.height)
        }
        drawFragment(g2)
        g2?.color = Color.BLUE
        g2?.drawOval(actions.mousePos.x + (if (outScreenX) -1 else 1) * offset.x,
                actions.mousePos.y + (if (outScreenY) -1 else 1) * offset.y, 3, 3)
    }

    private fun drawMap(g2: Graphics2D?) {
        val map = currentLevel.map
        val areas = currentLevel.areas
        val width = world.tile.width
        val height = world.tile.height

        map.indices.forEach { y -> map[y].indices.forEach { x ->
            val curItem = currentLevel.getItem(x, y)
            when {
                !areas[curItem.areaNum].isChecked ->
                    g2?.color = Color.getHSBColor(0.56f, 0.6f, 0.1f)
                curItem.type == IType.SOLID -> g2?.color = Color.LIGHT_GRAY
                curItem.type == IType.DOOR -> {
                    g2?.color = Color.WHITE
                    doors.add(Pair(curItem as Door, VectorInt(x, y)))
                }
                else -> g2?.color = Color.BLACK
            }
            g2?.fillRect(x * width + (if (outScreenX) -1 else 1) * offset.x,
                    y * height + (if (outScreenY) -1 else 1) * offset.y, width, height)
        }}
    }

    private fun writeNext(door: Door, g2: Graphics2D?, x: Int, y: Int, width: Int, height: Int) {
        g2?.color = Color.blue
        g2?.font = Font(Font.MONOSPACED, Font.BOLD, 16)
        g2?.drawString(door.nextLevel.name.getName(), x * width + (if (outScreenX) -1 else 1) * offset.x,
                y * height + (if (outScreenY) -1 else 1) * offset.y + 30)
        g2?.color = Color.gray
        g2?.font = Font(Font.MONOSPACED, Font.PLAIN, 16)
        g2?.drawString(door.nextLevel.name.getName(), x * width + (if (outScreenX) -1 else 1) * offset.x,
                y * height + (if (outScreenY) -1 else 1) * offset.y + 30)
    }

    private fun drawCharacter(g2: Graphics2D?) {
        if (currentLevel == world.currentLevel) {
            val character = world.character
            val size = character.halfSize * 2
            val pos = (character.pos - character.halfSize).toInt()
            g2?.color = Color.cyan
            g2?.drawOval(pos.x + (if (outScreenX) -1 else 1) * offset.x,
                    pos.y + (if (outScreenY) -1 else 1) * offset.y, size.width, size.height)
        }
    }

    private fun drawFragment(g2: Graphics2D?) {
        currentLevel.fragments
                .forEach { if(it.checked) drawElem(g2, Color.getHSBColor(13f, 100.0f, 82.0f), it) }
    }

    private fun drawElem(g2: Graphics2D?, color: Color, elem: Movable) {
        val size = elem.halfSize * 2
        g2?.color = color
        val pos = (elem.pos - elem.halfSize).toInt()
        g2?.drawRect(pos.x + (if (outScreenX) -1 else 1) * offset.x,
                pos.y + (if (outScreenY) -1 else 1) * offset.y, size.width, size.height)
    }
}