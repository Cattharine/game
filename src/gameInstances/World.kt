package gameInstances

import gameInstances.items.Door
import gameInstances.items.Item
import gameInstances.items.ItemName
import gameInstances.items.movables.Character
import gameInstances.items.movables.Movable
import gameInstances.levels.*
import gameInstances.states.ActionKeys
import gameInstances.states.enums.Dir
import gameInstances.states.enums.IType
import graphicInstances.Size
import graphicInstances.VectorD
import graphicInstances.VectorInt
import java.io.*
import kotlin.math.max
import kotlin.math.min


class World(val tile : Size) : Serializable {
    private val allLevels = HashMap<LevelName, Level>()
    val levels = HashMap<LevelName, Level>()

    init {
        val lvl1 = Lvl1(tile)
        allLevels[lvl1.name] = lvl1
        val lvl2 = Lvl2(tile)
        allLevels[lvl2.name] = lvl2
        val lvl3 = Lvl3(tile)
        allLevels[lvl3.name] = lvl3
        lvl1.addDoors(allLevels)
        lvl2.addDoors(allLevels)
        lvl3.addDoors(allLevels)
        lvl1.setConnectedDoors(allLevels)
        lvl2.setConnectedDoors(allLevels)
        lvl3.setConnectedDoors(allLevels)
    }

    var currentLevel : Level = allLevels[LevelName.LVL1] as Level
    val character = Character(Size(9, 9),
            VectorD(currentLevel.initialCharPos.x, currentLevel.initialCharPos.y), tile, Size(5, 5))

    fun update(actions: ActionKeys) {
        checkMechanisms(actions)
        checkMWalls()
        fillFragments()
        for (elem in currentLevel.movable) {
            if (elem != character.movable) {
                elem.checkFall(this)
                elem.move(Dir.NO, Dir.NO, this)
            }
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

    private fun checkMechanisms(actions: ActionKeys) {
        for (elem in currentLevel.mechanisms) {
            elem.check(this, actions, character)
        }
    }

    private fun fillFragments() {
        for (elem in currentLevel.fragments) {
            clearPoses(elem)
            fillPoses(elem)
        }
    }

    private fun changeActive(actions: ActionKeys) {
        if (actions.grabbingObject) {
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
        IType.SOLID, IType.DOOR, IType.FRAGMENT -> Pair(currentLevel.getItem(x, y), VectorInt(x, y))
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
                if (movable.name == ItemName.CHARACTER)
                    currentLevel.areas[item.areaNum].isChecked = true
                action(item.movables, movable)
            }
        }
    }

    fun canTeleportTo(item: Item?) = item!= null && currentLevel.areas[item.areaNum].isChecked

    private fun getMapX(movable: Movable) = Pair(getMapX(movable.getX(Dir.LEFT, true)),
            getMapX(movable.getX(Dir.RIGHT, false)))

    private fun getMapUp(movable: Movable) = getMapY(movable.getUpY(true))
    private fun getMapDown(movable: Movable) = getMapY(movable.getDownY(false))

    private fun getMapX(pos: Double) = pos.toInt() / tile.width
    private fun getMapY(pos: Double) = pos.toInt() / tile.height

    fun getSizeOfCurrentLevel() = currentLevel.getSize(tile)

    fun goToTheNextLevel(door: Door) {
        currentLevel.finalCharPos = character.pos
        levels[currentLevel.name] = currentLevel
        currentLevel = door.nextLevel
        if (!levels.containsKey(currentLevel.name))
            character.pos = VectorD(currentLevel.initialCharPos.x, currentLevel.initialCharPos.y)
        else character.pos = VectorD(door.connectedDoor.exitPos.x, door.connectedDoor.exitPos.y)
    }

    fun save() {
        val objectOutputStream = ObjectOutputStream(FileOutputStream("save1.out"))
        objectOutputStream.writeObject(character.pos)
        for (level in allLevels.keys) {
            objectOutputStream.writeObject(allLevels[level])
        }
        objectOutputStream.writeObject(currentLevel.name)
        for (name in levels.keys) {
            objectOutputStream.writeObject(name)
        }
        objectOutputStream.close()
    }

    fun load() {
        val objectInputStream = ObjectInputStream(FileInputStream("save1.out"))
        try {
            var obj = objectInputStream.readObject()
            character.pos = obj as VectorD
            obj = objectInputStream.readObject()
            while (obj is Level) {
                allLevels[obj.name] = obj
                obj = objectInputStream.readObject()
            }
            currentLevel = allLevels[obj as LevelName] as Level
            obj = objectInputStream.readObject()
            while (obj is LevelName) {
                levels[obj] = allLevels[obj] as Level
                obj = objectInputStream.readObject()
            }
            objectInputStream.close()
        } catch (ex: EOFException) {
            objectInputStream.close()
        }
    }
}