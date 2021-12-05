import controller.Controller
import javax.swing.JFrame

fun main() {
    val frame = JFrame()
    frame.isUndecorated = true
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.add(Controller())
    frame.extendedState = JFrame.MAXIMIZED_BOTH
    frame.isResizable = false
    frame.setLocationRelativeTo(null)
    frame.isVisible = true
}