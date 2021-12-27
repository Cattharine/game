package gameInstances.levels

import gameInstances.Area
import gameInstances.items.Door
import gameInstances.items.Item
import gameInstances.items.ItemName
import gameInstances.items.movables.Fragment
import gameInstances.items.movables.Mechanism
import gameInstances.items.movables.MovableWall
import gameInstances.states.enums.Ability
import gameInstances.states.enums.IType
import graphicInstances.Size
import graphicInstances.VectorD
import graphicInstances.VectorInt
import java.io.Serializable

class Lvl3(tile: Size) : Level(6, VectorD(30.0, 30.0)), Serializable {
    init {
        name = LevelName.LVL3
        lines = arrayOf(
                "s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s2|s2|s2|s2|s2|s2|s2|s2|s2|s2|s2|s2|s2|s2|s2|s2|s2|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s5|s5|s5|s5|s5|s5|s5|s5|s5|s5|s5|s5|s5|s5|s5|s5|s6|s6|s6|s6|s6|s6|s6|s6|s6|s6|s6|s6|s6|s6|s6|s6|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4",
                "d1| 1| 1| 1| 1|s1| 1| 1|s1| 1| 1| 1|s1| 1| 1| 1| 1| 1| 1|s2| 2| 2| 2| 2| 2| 2|s2| 2| 2| 2| 2| 2| 2|s2| 2|s2| 3| 3| 3| 3| 3| 3|s3| 3| 3| 3| 3| 3| 3|s3| 3|s3| 5| 5| 5| 5| 5| 5|s5| 5| 5| 5| 5| 5| 5|s5| 5|s5| 6| 6| 6| 6| 6| 6|s6| 6| 6| 6| 6| 6| 6|s6| 6|s6|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4|s4| 4| 4| 4| 4|s4| 4| 4|s4| 4| 4| 4| 4|s4| 4| 4|s4| 4| 4| 4| 4|s4| 4| 4|s4| 4| 4| 4| 4|s4| 4| 4|s4| 4| 4| 4| 4|s4| 4| 4|s4| 4| 4| 4| 4|s4| 4| 4|s4| 4| 4| 4| 4|s4| 4| 4|s4| 4| 4| 4| 4|s4| 4| 4|s4| 4| 4| 4| 4|s4| 4| 4|s4",
                "s1|s1| 1| 1| 1| 1| 1|s1|s1| 1|s1| 1| 1| 1| 1| 1| 1| 1|s1|s2| 2| 2| 2| 2| 2| 2| 2|s2|s2| 2| 2| 2| 2| 2| 2|s3| 3| 3| 3| 3| 3| 3| 3|s3|s3| 3| 3| 3| 3| 3| 3|s3| 5| 5| 5| 5| 5| 5| 5|s5|s5| 5| 5| 5| 5| 5| 5|s5| 6| 6| 6| 6| 6| 6| 6|s6|s6| 6| 6| 6| 6| 6| 6|s6|s4| 4| 4| 4|s4|s4|s4| 4|s4| 4|s4| 4| 4| 4| 4|s4| 4| 4| 4|s4| 4| 4| 4| 4|s4| 4| 4| 4|s4| 4| 4| 4| 4|s4| 4|s4| 4| 4| 4| 4|s4| 4| 4| 4|s4| 4| 4| 4| 4|s4| 4|s4| 4| 4| 4| 4|s4| 4| 4| 4|s4| 4| 4| 4| 4|s4| 4|s4| 4| 4| 4| 4|s4| 4| 4| 4|s4| 4| 4| 4| 4|s4| 4|s4",
                "s1|s1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 1| 2| 2| 2| 2| 2| 2| 2| 2| 2| 2| 2| 2| 2| 2| 2| 2| 3| 3| 3| 3| 3| 3| 3| 3| 3| 3| 3| 3| 3| 3| 3| 3| 3| 5| 5| 5| 5| 5| 5| 5| 5| 5| 5| 5| 5| 5| 5| 5| 5| 6| 6| 6| 6| 6| 6| 6| 6| 6| 6| 6| 6| 6| 6| 6| 6| 6| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4|s4| 4|s4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4|s4| 4|s4",
                "s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4| 1|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4| 2|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4| 3|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4| 6|s4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4",
                "s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4|s4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4",
                "s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4",
                "s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4")

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

        initializeMMovables(tile)
        initializeMechanisms(tile)
        initializeFragments(tile)
    }

    private fun initializeMMovables(tile: Size) {
        movableWalls.add(MovableWall({ list -> list[0]}, Size(10, 20), VectorD(370.0, 80.0),
                tile, Size(0, 0), VectorInt(0, 1)))
        movableWalls.add(MovableWall({ list -> list[0] && list[1]}, Size(10, 40),
                VectorD(690.0, 60.0), tile, Size(0,0), VectorInt(0, 1)))
        movableWalls.add(MovableWall({ list -> list[0]}, Size(10, 10), VectorD(1030.0, 70.0),
                tile, Size(0, 0), VectorInt(0, 1)))
        movableWalls.add(MovableWall({ list -> list[0] && list[1] && list[2]}, Size(10, 10),
                VectorD(1350.0, 70.0), tile, Size(0, 0), VectorInt(0, 1)))
    }

    private fun initializeMechanisms(tile: Size) {
        mechanisms.add(Mechanism(Size(10, 1), VectorD(210.0, 39.0), tile))
        mechanisms.add(Mechanism(Size(10, 1), VectorD(550.0, 39.0), tile))
        mechanisms.add(Mechanism(Size(10, 1), VectorD(870.0, 39.0), tile))

        movableWalls[0].addMechanism(mechanisms[0]) { bool -> bool }
        movableWalls[1].addMechanism(mechanisms[0]) { bool -> !bool }
        movableWalls[1].addMechanism(mechanisms[1]) { bool -> bool }
        movableWalls[2].addMechanism(mechanisms[1]) { bool -> !bool }
        movableWalls[3].addMechanism(mechanisms[0]) { bool -> !bool }
        movableWalls[3].addMechanism(mechanisms[1]) { bool -> bool }
        movableWalls[3].addMechanism(mechanisms[2]) { bool -> bool }
    }

    private fun initializeFragments(tile: Size) {
        fragments.add(Fragment(Ability.TELEPORTATION, VectorD(3170.0, 50.0), tile))
    }

    fun addDoors(levels: HashMap<LevelName, Level>) {
        doors[0].nextLevel = levels[LevelName.LVL1] as Level
    }

    fun setConnectedDoors(levels: HashMap<LevelName, Level>) {
        doors[0].connectedDoor = (levels[LevelName.LVL1] as Lvl1).doors[1]
        setExitPos()
    }

    private fun setExitPos() {
        doors[0].exitPos = VectorD(30.0, 30.0)
    }
}