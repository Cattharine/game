package gameInstances

import gameInstances.states.enums.IType
import gameInstances.states.enums.Dir
import gameInstances.states.enums.VertState
import gameInstances.states.MState
import graphicInstances.VectorD
import graphicInstances.Size
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

open class Movable(name: String, type: IType,
                   val halfSize : Size, var pos: VectorD, isActive: Boolean = false) : Item(name, type) {
    var speed: VectorD = VectorD(0.0, 0.0)
    val state = MState(isActive)
    val accel: VectorD = VectorD(2.5, 0.1)
    val maxSp: VectorD = VectorD(2.5, 4.0)

    fun move(hor: Dir, vert: Dir, world: World) {
        moveX(hor, world)
        moveY(vert, world)
//        println("${pos.x}, ${pos.y}")
    }

    fun checkFall(world: World) {
        val notFalling = notFalling(world)
        if (state.vertState != VertState.JUMPING) {
            if (!notFalling)
                state.vertState = VertState.FALLING
            else {
                speed.y = 0.0
                state.vertState = VertState.STANDING
            }
        }
    }

    private fun notFalling(world: World) = world.checkIntersectionY(
            getHPos(Dir.LEFT, false).toInt() / world.tile.width,
            getHPos(Dir.RIGHT, false).toInt() / world.tile.width,
            getFloor(true).toInt() / world.tile.height,
            getFloor(true).toInt() / world.tile.height).first == IType.SOLID

    private fun moveX(hor: Dir, world: World) {
        val hPos = getHor(hor)
        val eXTile = (speed.x + hPos).toInt() / world.tile.width
        val sXTile = hPos.toInt() / world.tile.width
        val sYTile = getCeil(false).toInt() / world.tile.height
        val eYTile = getFloor(false).toInt() / world.tile.height
        val (inter, nearestTile) = world.checkIntersectionX(sYTile, eYTile, min(sXTile, eXTile), max(sXTile, eXTile))
        commitChangesX(hor, inter, nearestTile, world)
    }

    private fun commitChangesX(hor: Dir, inter: IType, nearestTile: Int, world: World) {
        when (inter) {
            IType.EMPTY -> pos.x = pos.x + speed.x
            IType.SOLID -> hitSolidX(hor, nearestTile, world)
        }
    }

    private fun hitSolidX(hor: Dir, nearestTile: Int, world: World) {
        speed.x = 0.0
        pos.x = when (hor) {
            Dir.RIGHT -> nearestTile.toDouble() * world.tile.width - halfSize.width
            Dir.LEFT -> (nearestTile.toDouble() + 1) * world.tile.width + halfSize.width
            else -> pos.x
        }
    }

    private fun moveY(vert: Dir, world: World) {
        val vPos = getVert(vert)
        val eYTile = (speed.y + vPos).toInt() / world.tile.height
        val sYTile = vPos.toInt() / world.tile.height
        val sXTile = getHPos(Dir.LEFT, false).toInt() / world.tile.width
        val eXTile = getHPos(Dir.RIGHT, false).toInt() / world.tile.width
        val (inter, nearestTile) = world.checkIntersectionY(sXTile, eXTile, min(sYTile, eYTile), max(sYTile, eYTile))
        commitChangesY(inter, nearestTile, world)
    }

    private fun commitChangesY(inter: IType, nearestTile: Int, world: World) {
        when (inter) {
            IType.EMPTY -> pos.y = pos.y + speed.y
            IType.SOLID -> hitSolidY(nearestTile, world)
        }
    }

    private fun hitSolidY(nearestTile: Int, world: World) {
        speed.y = 0.0
        pos.y = when(state.vertState) {
            VertState.JUMPING -> (nearestTile.toDouble() + 1) * world.tile.height + halfSize.height
            VertState.FALLING -> nearestTile.toDouble() * world.tile.height - halfSize.height
            else -> nearestTile.toDouble() * world.tile.height - halfSize.height
        }
        state.vertState = when(state.vertState){
            VertState.JUMPING -> VertState.FALLING
            else -> VertState.STANDING
        }
    }

    private fun getHor(hor: Dir) = when(hor) {
        Dir.LEFT -> {
            speed.x = -accel.x
            getHPos(hor, true)
        }
        Dir.RIGHT -> {
            speed.x = accel.x
            getHPos(hor, true)
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
            getCeil(true)
        }
        Dir.DOWN -> {
            state.vertState = VertState.FALLING
            speed.y = accel.y
            getFloor(true)
        }
        else -> {
            state.vertState = VertState.STANDING
            speed.y = 0.0
            getFloor(true)
        }
    }

    private fun getVPJumping() = if (abs(speed.y) > accel.y) {
        speed.y += accel.y
        getCeil(true)
    } else {
        state.vertState = VertState.FALLING
        speed.y += accel.y
        getFloor(true)
    }

    private fun getVPFalling(): Double {
        speed.y += accel.y
        return getFloor(true)
    }

    private fun getCeil(withBorder: Boolean) = pos.y - halfSize.height + if (withBorder) 0 else 1
    private fun getFloor(withBorder: Boolean) = pos.y + halfSize.height - if (withBorder) 0 else 1
    private fun getHPos(hor: Dir, withBorder: Boolean) = pos.x + hor.getValue() * halfSize.width -
            hor.getValue() * if (withBorder) 0 else 1
}