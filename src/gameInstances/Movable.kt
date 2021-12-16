package gameInstances

import gameInstances.states.enums.IType
import gameInstances.states.enums.Dir
import gameInstances.states.enums.VertState
import gameInstances.states.MState
import graphicInstances.VectorD
import graphicInstances.Size
import kotlin.math.abs

open class Movable(name: String, type: IType,
                   val halfSize : Size, var pos: VectorD,
                   private val tile: Size, isActive: Boolean = false) : Item(name, type) {
    var speed: VectorD = VectorD(0.0, 0.0)
    val state = MState(isActive)
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
        val floorTile = getFloorTile(world)
        if (state.vertState != VertState.JUMPING || speed.y.equals(0.0)) {
            when (floorTile.type) {
                IType.SOLID -> stopFall()
                IType.EMPTY -> checkFallTileEmpty(floorTile)
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

    private fun checkFallTileEmpty(floorTile: Item) {
        if (floorTile.movables.isEmpty())
            startFall()
        else hitMovableFalling(floorTile.movables, hitAct = {stopFall()}, freeAct = {startFall()})
    }

    private fun getFloorTile(world: World) = world.checkInterY(
            toMapX(getX(Dir.LEFT, false)), toMapX(getX(Dir.RIGHT, false)),
            toMapY(getDownY(true)), toMapY(getDownY(true))).first

    private fun moveX(hor: Dir, world: World) {
        val hPos = getHor(hor)
        val eXTile = toMapX(speed.x + hPos)
        val sXTile = toMapX(hPos)
        val sYTile = toMapY(getUpY(false))
        val eYTile = toMapY(getDownY(false))
        val (inter, nearestTile) = world.checkInterX(sYTile, eYTile, sXTile, eXTile)
        changeX(hor, inter, nearestTile)
    }

    private fun changeX(hor: Dir, inter: Item, nearestTile: Int) {
        when (inter.type) {
            IType.EMPTY -> hitEmptyX(hor, inter, nearestTile)
            IType.SOLID -> hitSolidX(hor, nearestTile)
        }
    }

    private fun hitEmptyX(hor: Dir, inter: Item, nearestTile: Int) {
        if (nearestTile == -1)
            pos.x = pos.x + speed.x
        else hitMovableX(hor, inter.movables)
    }

    private fun hitMovableX(hor: Dir, others: ArrayList<Movable>) {
        val sign = hor.getValue()
        var res = others[0]
        val otherDir = hor.getOpposite()
        var resB = res.getX(otherDir, true)
        val border = getX(hor, true)
        others.indices.forEach { i ->
            val otherB = others[i].getX(otherDir, true)
            if (hasInterY(others[i]) && sign * otherB < sign * resB) {
                res = others[i]
                resB = otherB
            }
        }
        if (hasInterY(res) && sign * (resB - border) < sign * speed.x) {
            speed.x = 0.0
            pos.x = resB - sign * halfSize.width
        }
        else pos.x = pos.x + speed.x
    }

    private fun hasInterY(other: Movable) = abs(pos.y - other.pos.y) < halfSize.height + other.halfSize.height

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
        val (inter, nearestTile) = world.checkInterY(sXTile, eXTile, sYTile, eYTile)
        changeY(inter, nearestTile)
    }

    private fun changeY(inter: Item, nearestTile: Int) {
        when (inter.type) {
            IType.EMPTY -> hitEmptyY(inter, nearestTile)
            IType.SOLID -> hitSolidY(nearestTile)
        }
    }

    private fun hitEmptyY(inter: Item, nearestTile: Int) {
        if (nearestTile == -1)
            pos.y = pos.y + speed.y
        else hitMovableY(inter.movables)
    }

    private fun hitMovableY(others: ArrayList<Movable>) {
        when(state.vertState) {
            VertState.JUMPING -> hitMovableJumping(others)
            VertState.FALLING -> hitMovableFalling(others,
                    hitAct = { resB -> hitMovableAct(resB) },
                    freeAct = { pos.y += speed.y})
            else -> {}
        }
    }
    private fun hitMovableJumping(others: ArrayList<Movable>) {
        hitMovableY(others,
                getOtherBorder = { other -> other.getDownY(true) },
                getThisBorder = { cur -> cur.getUpY(true) },
                finalCond = {resB, border -> border - resB <= -speed.y},
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
        speed.y = 0.0
        pos.y = resB + state.vertState.getValue() * halfSize.height
    }

    private fun hitMovableY(others: ArrayList<Movable>, getOtherBorder: (Movable) -> Double,
                            getThisBorder: (Movable) -> Double,
                            finalCond: (Double, Double) -> Boolean,
                            hitAct: (Double) -> Unit, freeAct: () -> Unit) {
        val sign = state.vertState.getValue()
        var res = others[0]
        var resB = getOtherBorder(res)
        val border = getThisBorder(this)
        others.indices.forEach { i ->
            val otherB = getOtherBorder(others[i])
            if (hasInterX(others[i]) && sign * otherB > sign * resB) {
                res = others[i]
                resB = otherB
            }
        }
        if (hasInterX(res) && finalCond(resB, border))
            hitAct(resB)
        else freeAct()
    }

    private fun hasInterX(other: Movable) = abs(pos.x - other.pos.x) < halfSize.width + other.halfSize.width

    private fun hitSolidY(nearestTile: Int) {
        speed.y = 0.0
        pos.y = when(state.vertState) {
            VertState.JUMPING -> (nearestTile.toDouble() + 1) * tile.height + halfSize.height
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

    fun getUpY(withBorder: Boolean) = pos.y - halfSize.height + if (withBorder) 0 else 1
    fun getDownY(withBorder: Boolean) = pos.y + halfSize.height - if (withBorder) 0 else 1
    fun getX(hor: Dir, withBorder: Boolean) = pos.x + hor.getValue() * halfSize.width -
            hor.getValue() * if (withBorder) 0 else 1

    private fun toMapX(pos: Double) = pos.toInt() / tile.width
    private fun toMapY(pos: Double) = pos.toInt() / tile.height
}