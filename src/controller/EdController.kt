package controller

import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D

class EdController {
    private val off = 20

    fun draw(g2: Graphics2D?, height : Int, width: Int) {
        g2?.color = Color.WHITE
        g2?.font = Font(Font.MONOSPACED, Font.BOLD, 30)
        g2?.drawString("Стрелочки - передвижение в соответствующем напрвлении", 0, height / 2)
        g2?.drawString("ПКМ - начать перемещать объект", 0, height / 2 + 30)
        g2?.drawString("(необходимо получить умение)", 10, height / 2 + 60)
        g2?.drawString("ЛКМ - телепортация (необходимо получить умение)",
                0, height / 2 + 90)
        g2?.drawString("M - вызвать карту / свернуть карту", 0, height / 2 + 120)
        g2?.drawString("Esc - возврат в главное меню", 0, height / 2 + 150)


    }
}