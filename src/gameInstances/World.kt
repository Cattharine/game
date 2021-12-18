package gameInstances

import gameInstances.movables.Character
import gameInstances.movables.Movable
import gameInstances.states.ActionKeys
import gameInstances.states.enums.Dir
import gameInstances.states.enums.IType
import graphicInstances.Size
import graphicInstances.VectorD
import graphicInstances.VectorInt
import kotlin.math.max
import kotlin.math.min

class World(val tile : Size) {
    val currentLevel = Level(tile)
    val character = Character(Size(9, 9), VectorD(190.0, 270.0), tile, Size(5, 5))

    fun update(actions: ActionKeys) {
        checkMechs(actions)
        checkMWalls()
        for (elem in currentLevel.movable) {
            elem.checkFall(this)
            elem.move(Dir.NO, Dir.NO, this)
        }
        character.checkFall(this)
        changeActive(actions)
        character.act(actions, this)
    }

    private fun checkMWalls() {
        for (elem in currentLevel.movableWalls) {
            elem.check(this)
        }
    }

    private fun checkMechs(actions: ActionKeys) {
        for (elem in currentLevel.mechs) {
            elem.check(this, actions, character)
        }
    }

    private fun changeActive(actions: ActionKeys) {
        if (actions.mouseClicked) {
            val pos = VectorInt(actions.mousePos.x / tile.width, actions.mousePos.y / tile.height)
            val item = currentLevel.tryGetItem(pos.x, pos.y)
            character.setMovable(item, actions)
        }
    }

    fun checkInterX(startY: Int, endY: Int, startX: Int, endX: Int) =
            checkInter(startX, endX, startY, endY, getCurInter = {main, other -> getInter(main, other)})

    fun checkInterY(startX: Int, endX: Int, startY: Int, endY: Int) =
        checkInter(startY, endY, startX, endX, getCurInter = {main, other -> getInter(other, main)})


    private fun checkInter(mainStart: Int, mainEnd: Int,
                           otherStart: Int, otherEnd: Int,
                           getCurInter: (Int, Int) -> (Pair<Item, VectorInt>?)): ArrayList<Pair<Item, VectorInt>> {
        val res = ArrayList<Pair<Item, VectorInt>>()
        val mStart = min(mainStart, mainEnd)
        val mEnd = max(mainStart, mainEnd)
        (mStart .. mEnd).forEach { main ->
            (otherStart .. otherEnd).forEach { other ->
                val inter = getCurInter(main, other)
                if (inter != null) res.add(inter)
            }
        }
        return res
    }

    private fun getInter(x: Int, y: Int) = when(currentLevel.getType(x, y)) {
        IType.SOLID -> Pair(currentLevel.getItem(x, y), VectorInt(x, y))
        IType.EMPTY -> {
            if (currentLevel.getItem(x, y).movables.isNotEmpty())
                Pair(currentLevel.getItem(x, y), VectorInt(x, y))
            else null
        }
        IType.MECHANISM -> null
    }

    fun clearPoses(movable: Movable) = updatePoses(movable, action = { list, elem -> list.remove(elem) })

    fun fillPoses(movable: Movable) = updatePoses(movable, action = { list, elem -> list.add(elem) })

    private fun updatePoses(movable: Movable, action: (ArrayList<Movable>, Movable) -> Boolean) {
        val (left, right) = getMapX(movable)
        val ceil = getMapUp(movable)
        val floor = getMapDown(movable)
        (left .. right).forEach { x ->
            (ceil .. floor).forEach { y ->
                val item = currentLevel.getItem(x, y)
                if (movable.name == "character")
                    currentLevel.areas[item.areaNum].isChecked = true
                action(item.movables, movable)
            }
        }
    }

    fun canTeleportTo(realPos: VectorInt): Boolean {
        val pos = VectorInt(realPos.x / tile.width, realPos.y / tile.height)
        val item = currentLevel.tryGetItem(pos.x, pos.y)
        return if (item == null)
            false
        else currentLevel.areas[item.areaNum].isChecked
    }

    fun canTeleportTo(item: Item?) = item!= null && currentLevel.areas[item.areaNum].isChecked

    private fun getMapX(movable: Movable) = Pair(getMapX(movable.getX(Dir.LEFT, true)),
            getMapX(movable.getX(Dir.RIGHT, false)))

    private fun getMapUp(movable: Movable) = getMapY(movable.getUpY(true))
    private fun getMapDown(movable: Movable) = getMapY(movable.getDownY(false))

    private fun getMapX(pos: Double) = pos.toInt() / tile.width
    private fun getMapY(pos: Double) = pos.toInt() / tile.height
}