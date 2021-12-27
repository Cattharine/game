package gameInstances

import java.io.Serializable

class Area(val number: Int): Serializable {
    var isChecked = false

    override fun equals(other: Any?): Boolean {
        if (other == null)
            return false
        if (other.javaClass != this.javaClass)
            return false
        val oth = other as Area
        return oth.number == number
    }

    override fun hashCode(): Int {
        return number
    }
}