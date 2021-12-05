package gameInstances

import gameInstances.states.ActionKeys
import gameInstances.states.enums.IType
import graphicInstances.Size

class World(val tile : Size) {
    val currentLevel = Level()
    val character = Character(tile / 2, tile.toDouble() * 1.5)

    fun update(actions: ActionKeys) {
        character.checkFall(this)
        character.act(actions, this)
    }

    fun checkIntersectionX(sY: Int, eY: Int, start: Int, end: Int): Pair<IType, Int> {
        //end >= start!
        (start .. end).forEach { x ->
            (sY .. eY).forEach { y -> when(currentLevel.getType(x, y)) {
                IType.SOLID -> return Pair(IType.SOLID, x)
                else -> {}
            }}}
        return Pair(IType.EMPTY, 0)
    }

    fun checkIntersectionY(sX: Int, eX: Int, start: Int, end: Int): Pair<IType, Int> {
        //end >= start!
        (start .. end).forEach { y ->
            (sX .. eX).forEach { x ->  when(currentLevel.getType(x, y)) {
                IType.SOLID -> return Pair(IType.SOLID, y)
                else -> {}
        }}}
        return Pair(IType.EMPTY, 0)
    }
}