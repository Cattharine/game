package gameInstances.movables

import gameInstances.Item
import gameInstances.World
import gameInstances.states.enums.IType
import gameInstances.states.enums.Dir
import gameInstances.states.enums.VertState
import gameInstances.states.MState
import graphicInstances.VectorD
import graphicInstances.Size
import graphicInstances.VectorInt
import kotlin.math.abs
import kotlin.math.sign

open class Movable(name: String, type: IType,
                   var halfSize : Size, var pos: VectorD,
                   val tile: Size,
                   isAvailable: Boolean = true,
                   initialVertState: VertState = VertState.STANDING) : Item(name, type) {
    var speed: VectorD = VectorD(0.0, 0.0)
    val state = MState(isAvailable, initialVertState)
    val accel: VectorD = VectorD(2.5, 0.1)
    val maxSp: VectorD = VectorD(2.5, 4.0)

    fun move(hor: Dir, vert: Dir, world: World) {
        world.clearPoses(this)
        moveX(hor, world)
        moveY(vert, world)
        world.fillPoses(this)
//        println("${pos.x}, ${pos.y}")
    }

    fun checkFall(world: World) {
        world.clearPoses(this)
        val floorTiles = getFloorTiles(world)
        if ((state.vertState != VertState.JUMPING || speed.y.equals(0.0)) &&
                state.vertState != VertState.NOT_FALLING) {
            if (floorTiles.isEmpty())
                checkFallTileEmpty(floorTiles)
            else when (floorTiles[0].first.type) {
                IType.SOLID -> stopFall()
                IType.EMPTY -> checkFallTileEmpty(floorTiles)
                IType.MECHANISM -> checkFallTileEmpty(floorTiles)
            }
        }
        world.fillPoses(this)
    }

    private fun stopFall() {
        speed.y = 0.0
        state.vertState = VertState.STANDING
    }

    private fun startFall() {
        state.vertState = VertState.FALLING
    }

    private fun checkFallTileEmpty(floorTiles: ArrayList<Pair<Item, VectorInt>>) {
        if (floorTiles.isEmpty())
            startFall()
        else {
            val movables = ArrayList<Movable>()
            floorTiles.forEach { (item, _) ->
                item.movables.forEach { movable -> if (!movables.contains(movable)) movables.add(movable) }
            }
            if (movables.isEmpty())
                startFall()
            else hitMovableFalling(movables, hitAct = { stopFall() }, freeAct = { startFall() })
        }
    }

    private fun getFloorTiles(world: World) = world.checkInterY(
            toMapX(getX(Dir.LEFT, false)), toMapX(getX(Dir.RIGHT, false)),
            toMapY(getDownY(true)), toMapY(getDownY(true)))

    private fun moveX(hor: Dir, world: World) {
        val hPos = getHor(hor)
        val eXTile = toMapX(speed.x + hPos)
        val sXTile = toMapX(hPos)
        val sYTile = toMapY(getUpY(false))
        val eYTile = toMapY(getDownY(false))
        val inters = world.checkInterX(sYTile, eYTile, sXTile, eXTile)
        changeX(hor, inters)
    }

    private fun changeX(hor: Dir, inters: ArrayList<Pair<Item, VectorInt>>) {
        if (inters.isEmpty())
            hitEmptyX(hor, inters)
        else when (inters[0].first.type) {
            IType.EMPTY -> hitEmptyX(hor, inters)
            IType.SOLID -> hitSolidX(hor, inters[0].second.x)
            IType.MECHANISM -> hitEmptyX(hor, inters)
        }
    }

    private fun hitEmptyX(hor: Dir, inters: ArrayList<Pair<Item, VectorInt>>) {
        if (inters.isEmpty())
            pos.x = pos.x + speed.x
        else {
            val movables = ArrayList<Movable>()
            inters.forEach{ (item, _) ->
                item.movables.forEach{ movable ->
                    if (!movables.contains(movable) && movable.type != IType.MECHANISM && item.type != IType.MECHANISM)
                        movables.add(movable)}}
            if (movables.isEmpty())
                pos.x = pos.x + speed.x
            else hitMovableX(hor, movables)
        }
    }

    private fun hitMovableX(hor: Dir, others: ArrayList<Movable>) {
        val sign = hor.getValue()
        var res: Movable? = null
        val otherDir = hor.getOpposite()
        var resB = Double.MAX_VALUE
        val border = getX(hor, true)
        others.indices.forEach { i ->
            val otherB = others[i].getX(otherDir, true)
            if (hasInterY(others[i]) && sign * border <= otherB * sign &&
                    (res == null || sign * otherB < sign * resB)) {
                res = others[i]
                resB = otherB
            }
        }
        if (res != null && hasInterY(res as Movable) && sign * (resB - border) < sign * speed.x) {
            speed.x = 0.0
            pos.x = resB - sign * halfSize.width
        }
        else pos.x = pos.x + speed.x
    }

    fun hasInterY(other: Movable, withMech: Boolean = false) =
            abs(pos.y - other.pos.y) < halfSize.height + other.halfSize.height &&
            (other.type != IType.MECHANISM || withMech)

    private fun hitSolidX(hor: Dir, nearestTile: Int) {
        speed.x = 0.0
        pos.x = when (hor) {
            Dir.RIGHT -> nearestTile.toDouble() * tile.width - halfSize.width
            Dir.LEFT -> (nearestTile.toDouble() + 1) * tile.width + halfSize.width
            else -> pos.x
        }
    }

    private fun moveY(vert: Dir, world: World) {
        val vPos = getVert(vert)
        val eYTile = toMapY(speed.y + vPos)
        val sYTile = toMapY(vPos)
        val sXTile = toMapX(getX(Dir.LEFT, false))
        val eXTile = toMapX(getX(Dir.RIGHT, false))
        val inters  = world.checkInterY(sXTile, eXTile, sYTile, eYTile)
        changeY(inters)
    }

    private fun changeY(inters: ArrayList<Pair<Item, VectorInt>>) {
        if (inters.isEmpty())
            hitEmptyY(inters)
        else when (inters[0].first.type) {
            IType.EMPTY -> hitEmptyY(inters)
            IType.SOLID -> hitSolidY(inters[0].second.y)
            IType.MECHANISM -> hitEmptyY(inters)
        }
    }

    private fun hitEmptyY(inters: ArrayList<Pair<Item, VectorInt>>) {
        if (inters.isEmpty())
            pos.y = pos.y + speed.y
        else {
            val movables = ArrayList<Movable>()
            inters.forEach{ (item, _) ->
                item.movables.forEach{ movable ->
                    if (!movables.contains(movable) && movable.type != IType.MECHANISM && item.type != IType.MECHANISM)
                        movables.add(movable)}}
            if (movables.isEmpty())
                pos.y = pos.y + speed.y
            else hitMovableY(movables)
        }
    }

    private fun hitMovableY(others: ArrayList<Movable>) {
        when(state.vertState) {
            VertState.JUMPING -> hitMovableJumping(others)
            VertState.FALLING -> hitMovableFalling(others,
                    hitAct = { resB -> hitMovableAct(resB) },
                    freeAct = { pos.y += speed.y})
            VertState.NOT_FALLING -> when {
                speed.y > 0 -> hitMovableFalling(others,
                        hitAct = { resB -> hitMovableAct(resB) },
                        freeAct = { pos.y += speed.y})
                speed.y < 0 -> hitMovableJumping(others)
                else -> {}
            }
            else -> {}
        }
    }
    private fun hitMovableJumping(others: ArrayList<Movable>) {
        hitMovableY(others,
                getOtherBorder = { other -> other.getDownY(true) },
                getThisBorder = { cur -> cur.getUpY(true) },
                finalCond = {resB, border -> border - resB < -speed.y},
                hitAct = { resB -> hitMovableAct(resB)},
                freeAct = {pos.y += speed.y})
    }

    private fun hitMovableFalling(others: ArrayList<Movable>,
                                  hitAct: (Double) -> Unit, freeAct: () -> Unit) {
        hitMovableY(others,
                getOtherBorder = { other -> other.getUpY(true) },
                getThisBorder = { current -> current.getDownY(true) },
                finalCond = {resB, border -> resB - border <= speed.y},
                hitAct = hitAct, freeAct = freeAct)
    }

    private fun hitMovableAct(resB: Double) {
        when(state.vertState) {
            VertState.NOT_FALLING -> pos.y = resB - sign(speed.y) * halfSize.height
            else -> {
                speed.y = 0.0
                pos.y = resB + state.vertState.getValue() * halfSize.height
            }
        }
    }

    private fun hitMovableY(others: ArrayList<Movable>, getOtherBorder: (Movable) -> Double,
                            getThisBorder: (Movable) -> Double,
                            finalCond: (Double, Double) -> Boolean,
                            hitAct: (Double) -> Unit, freeAct: () -> Unit) {
        val sign = if (state.vertState != VertState.NOT_FALLING) state.vertState.getValue() else -1 * sign(speed.y)
        var res: Movable? = null
        var resB = Double.MAX_VALUE
        val border = getThisBorder(this)
        others.indices.forEach { i ->
            val otherB = getOtherBorder(others[i])
            if (sign * border >= sign * otherB && hasInterX(others[i]) &&
                    (res == null || sign * otherB > sign * resB)) {
                res = others[i]
                resB = otherB
            }
        }
        if (res != null && hasInterX(res as Movable) && finalCond(resB, border))
            hitAct(resB)
        else freeAct()
    }

    fun hasInterX(other: Movable, withMech: Boolean = false) =
            abs(pos.x  - other.pos.x) < halfSize.width + other.halfSize.width &&
                    (other.type != IType.MECHANISM || withMech)

    private fun hitSolidY(nearestTile: Int) {
        if (state.vertState != VertState.NOT_FALLING)
            speed.y = 0.0
        pos.y = when(state.vertState) {
            VertState.JUMPING -> (nearestTile.toDouble() + 1) * tile.height + halfSize.height
            VertState.NOT_FALLING -> when {
                speed.y < 0 -> (nearestTile.toDouble() + 1) * tile.height + halfSize.height
                else -> nearestTile.toDouble() * tile.height - halfSize.height
            }
            else -> nearestTile.toDouble() * tile.height - halfSize.height
        }
    }

    private fun getHor(hor: Dir) = when(hor) {
        Dir.LEFT -> {
            speed.x = -accel.x
            getX(hor, true)
        }
        Dir.RIGHT -> {
            speed.x = accel.x
            getX(hor, true)
        }
        else -> {
            speed.x = 0.0
            pos.x
        }
    }

    private fun getVert(vert: Dir) = when(state.vertState) {
        VertState.STANDING -> getVPStanding(vert)
        VertState.JUMPING -> getVPJumping()
        VertState.FALLING -> getVPFalling()
        VertState.NOT_FALLING -> getVPNotFalling(vert)
    }

    //VP = Vertical Position
    private fun getVPStanding(vert: Dir) = when(vert) {
        Dir.JUMP -> {
            state.vertState = VertState.JUMPING
            speed.y = -maxSp.y
            getUpY(true)
        }
        Dir.DOWN -> {
            state.vertState = VertState.FALLING
            speed.y = accel.y
            getDownY(true)
        }
        else -> {
            state.vertState = VertState.STANDING
            speed.y = 0.0
            getDownY(true)
        }
    }

    private fun getVPJumping() = if (abs(speed.y) > accel.y) {
        speed.y += accel.y
        getUpY(true)
    } else {
        state.vertState = VertState.FALLING
        speed.y += accel.y
        getDownY(true)
    }

    private fun getVPFalling(): Double {
        speed.y += accel.y
        return getDownY(true)
    }

    private fun getVPNotFalling(vert: Dir) = when(vert) {
        Dir.JUMP -> {
            speed.y = -accel.y
            getUpY(true)
        }
        Dir.DOWN -> {
            speed.y = accel.y
            getDownY(true)
        }
        else -> {
            if (speed.y > 0)
                getDownY(true)
            else getUpY(true)
        }
    }

    fun getUpY(withBorder: Boolean) = pos.y - halfSize.height + if (withBorder) 0 else 1
    fun getDownY(withBorder: Boolean) = pos.y + halfSize.height - if (withBorder) 0 else 1
    fun getX(hor: Dir, withBorder: Boolean) = pos.x + hor.getValue() * halfSize.width -
            hor.getValue() * if (withBorder) 0 else 1

    private fun toMapX(pos: Double) = pos.toInt() / tile.width
    private fun toMapY(pos: Double) = pos.toInt() / tile.height

    fun hasPoint(point: VectorInt) = getX(Dir.LEFT, true).toInt() <= point.x &&
            point.x <= getX(Dir.RIGHT, true).toInt() &&
            getUpY(true).toInt() <= point.y &&
            point.y <= getDownY(true).toInt()
}