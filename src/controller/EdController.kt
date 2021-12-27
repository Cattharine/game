package controller

import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D

class EdController {
    private val off = 20

    fun draw(g2: Graphics2D?, height : Int, width: Int) {
        g2?.color = Color.WHITE
        g2?.font = Font(Font.MONOSPACED, Font.BOLD, 30)
        g2?.drawString("Стрелочки - передвижение в соответствующем напрвлении", width / 2 - off, height / 2)
        g2?.drawString("ПКМ - начать перемещать объект", width / 2 - off, height / 2 + 30)
        g2?.drawString("(необходимо получить умение)", width / 2 - off + 10, height / 2 + 60)
        g2?.drawString("ЛКМ - телепортация (необходимо получить умение)",
                width / 2 - off, height / 2 + 90)
        g2?.drawString("M - вызвать карту / свернуть карту", width / 2 - off, height / 2 + 120)
        g2?.drawString("Esc - возврат в главное меню", width / 2 - off, height / 2 + 150)


    }
}