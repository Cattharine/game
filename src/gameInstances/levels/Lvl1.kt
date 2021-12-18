package gameInstances.levels
import gameInstances.Area
import gameInstances.items.Door
import gameInstances.items.Item
import gameInstances.items.movables.Mechanism
import gameInstances.items.movables.Movable
import gameInstances.items.movables.MovableWall
import gameInstances.states.enums.IType
import graphicInstances.Size
import graphicInstances.VectorD
import graphicInstances.VectorInt

class Lvl1(tile: Size): Level(4, VectorD(270.0, 270.0)) {
    init {
        name = "lvl1"
        lines = arrayOf(
                "s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s1|s3| 3|s3|s3| 3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3|s3",
                "s1| 1| 1| 1| 1|s1| 1| 1|s1| 1| 1| 1|s3| 3| 3| 3| 3| 3| 3|s3| 3| 3| 3| 3| 3| 3|s3| 3| 3| 3| 3| 3| 3|s3| 3|s3| 3| 3| 3| 3| 3| 3|s3| 3| 3| 3| 3| 3| 3|s3| 3|s3| 3| 3| 3| 3| 3| 3|s3| 3| 3| 3| 3| 3| 3|s3| 3|s3| 3| 3| 3| 3| 3| 3|s3| 3| 3| 3| 3| 3| 3|s3| 3|s3",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 1|s3|s3| 3| 3| 3| 3|s3| 3| 3|s3| 3| 3| 3| 3|s3| 3| 3|s3| 3| 3| 3| 3|s3| 3| 3|s3| 3| 3| 3| 3|s3| 3| 3|s3| 3| 3| 3| 3|s3| 3| 3|s3| 3| 3| 3| 3|s3| 3| 3|s3| 3| 3| 3| 3|s3| 3| 3|s3| 3| 3| 3| 3|s3| 3| 3|s3| 3| 3| 3| 3|s3| 3| 3|s3",
                "s1|s1| 1| 1| 1| 1| 1|s1|s1| 2|s3| 3| 3| 3| 3| 3| 3| 3|s3|s3| 3| 3| 3| 3| 3| 3| 3|s3|s3| 3| 3| 3| 3| 3| 3|s3| 3| 3| 3| 3| 3| 3| 3|s3|s3| 3| 3| 3| 3| 3| 3|s3| 3| 3| 3| 3| 3| 3| 3|s3|s3| 3| 3| 3| 3| 3| 3|s3| 3| 3| 3| 3| 3| 3| 3|s3|s3| 3| 3| 3| 3| 3| 3|s3",
                "s1| 1| 1| 1|s1|s1|s1| 1|s1| 2|s3| 3| 3| 3| 3|s3| 3| 3| 3|s3| 3| 3| 3| 3|s3| 3| 3| 3|s3| 3| 3| 3| 3|s3| 3|s3| 3| 3| 3| 3|s3| 3| 3| 3|s3| 3| 3| 3| 3|s3| 3|s3| 3| 3| 3| 3|s3| 3| 3| 3|s3| 3| 3| 3| 3|s3| 3|s3| 3| 3| 3| 3|s3| 3| 3| 3|s3| 3| 3| 3| 3|s3| 3|s3",
                "s1|s1| 1| 1| 1| 1| 1| 1|s1| 2|s3|s3| 3| 3| 3| 3| 3|s3| 3|s3| 3| 3| 3| 3| 3|s3| 3|s3| 3| 3| 3| 3| 3|s3| 3|s3| 3| 3| 3| 3| 3|s3| 3|s3| 3| 3| 3| 3| 3|s3| 3|s3| 3| 3| 3| 3| 3|s3| 3|s3| 3| 3| 3| 3| 3|s3| 3|s3| 3| 3| 3| 3| 3|s3| 3|s3| 3| 3| 3| 3| 3|s3| 3|s3",
                "s1| 1| 1| 1| 1| 1| 1| 1| 1| 2| 2| 3| 3| 3| 3| 3| 3| 3| 3|s3| 3| 3| 3| 3| 3| 3|s3| 3| 3| 3| 3| 3| 3|s3| 3|s3| 3| 3| 3| 3| 3| 3|s3| 3| 3| 3| 3| 3| 3|s3| 3|s3| 3| 3| 3| 3| 3| 3|s3| 3| 3| 3| 3| 3| 3|s3| 3|s3| 3| 3| 3| 3| 3| 3|s3| 3| 3| 3| 3| 3| 3|s3| 3|s3",
                "s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4| 3|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4| 4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4| 4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4| 4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4| 4|s4",
                "s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 3|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4",
                "s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4|s4",
                "s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4",
                "d4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4",
                "s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4",
                "s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4| 4|s4",
                "s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4| 4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4|s4")

        val preMap = lines.map { it
                .split("|")
                .map {char -> val area = Area(Integer.parseInt(char[1].toString()) - 1)
                    if (areas[area.number].number == -1)
                        areas[area.number] = area
                    when(char[0]) {
                        'd' -> {val d = Door(area.number); doors.add(d); d}
                        's' -> Item("", IType.SOLID, area.number)
                        ' ' -> Item("", IType.EMPTY, area.number)
                        else -> Item("", IType.EMPTY, area.number)
                    }}}
        map = preMap + preMap + preMap + preMap

        initializeMovables(tile)
        initializeMMovables(tile)
        initializeMechanisms(tile)
    }

    private fun initializeMovables(tile: Size) {
        movable.add(Movable("", IType.SOLID, Size(50, 15),
                VectorD(180.0, 185.0), tile))
        movable.add(Movable("", IType.SOLID, Size(8, 8),
                VectorD(190.0, 160.0), tile))
    }

    private fun initializeMMovables(tile: Size) {
        movableWalls.add(MovableWall(Size(10, 10), VectorD(210.0, 130.0),
                tile, Size(0, 0), VectorInt(0, 1)))
        movableWalls.add(MovableWall(Size(10, 10), VectorD(170.0, 130.0),
                tile, Size(0,0), VectorInt(0, 1)))
        movableWalls.add(MovableWall(Size(10, 10), VectorD(190.0, 70.0),
                tile, Size(0, 0), VectorInt(1, 0)))
    }

    private fun initializeMechanisms(tile: Size) {
        mechanisms.add(Mechanism(Size(10, 1), VectorD(330.0, 39.0), tile))
        mechanisms.add(Mechanism(Size(10, 1), VectorD(30.0, 59.0), tile))

        movableWalls[0].addMechanism(mechanisms[0]) { bool -> bool }
        movableWalls[1].addMechanism(mechanisms[0]) { bool -> !bool }
        movableWalls[2].addMechanism(mechanisms[1]) { bool -> !bool }
    }
}