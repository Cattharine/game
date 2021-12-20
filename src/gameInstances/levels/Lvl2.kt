package gameInstances.levels

import gameInstances.Area
import gameInstances.items.Door
import gameInstances.items.Item
import gameInstances.items.ItemName
import gameInstances.items.movables.Fragment
import gameInstances.items.movables.Mechanism
import gameInstances.items.movables.Movable
import gameInstances.items.movables.MovableWall
import gameInstances.states.enums.Ability
import gameInstances.states.enums.IType
import graphicInstances.Size
import graphicInstances.VectorD
import graphicInstances.VectorInt

class Lvl2(tile: Size) : Level(1,  VectorD(450.0, 750.0)) {
    init {
        name = LevelName.LVL2
        lines = arrayOf(
                "s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1|s1| 1| 1|s1| 1| 1| 1|s1| 1| 1| 1|s1| 1| 1| 1|s1| 1| 1| 1|s1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1|s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1|s1| 1| 1| 1| 1| 1|s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1| 1| 1| 1| 1|s1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1|s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1| 1| 1| 1| 1| 1| 1|s1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1|s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1|s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1| 1| 1| 1| 1|s1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1| 1| 1| 1| 1| 1|s1| 1| 1|s1",
                "s1| 1| 1| 1|s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1| 1| 1|s1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1|s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s1",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1|d1",
                "s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1")

        map = lines.map { it
                .split("|")
                .map {char -> val area = Area(Integer.parseInt(char[1].toString()) - 1)
                    if (areas[area.number].number == -1)
                        areas[area.number] = area
                    when(char[0]) {
                        'd' -> {val d = Door(area.number); doors.add(d); d}
                        's' -> Item(ItemName.EMPTY, IType.SOLID, area.number)
                        ' ' -> Item(ItemName.EMPTY, IType.EMPTY, area.number)
                        else -> Item(ItemName.EMPTY, IType.EMPTY, area.number)
                    }}}

        initializeFragments(tile)
    }

    private fun initializeFragments(tile: Size) {
        fragments.add(Fragment(Ability.TELEPORTATION, VectorD(30.0, 90.0), tile))
    }

    fun addDoors(levels: HashMap<LevelName, Level>) {
        doors[0].nextLevel = levels[LevelName.LVL1] as Level
    }
}