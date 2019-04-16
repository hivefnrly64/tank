import javafx.application.Application
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter
import org.itheima.kotlin.game.core.Window
import java.util.*

class TestWindow:Window() {
    override fun onCreate() {
        println("go")
    }

    override fun onDisplay() {
        Painter.drawImage("img/tank_u.gif",0,0)
        Painter.drawColor(Color.WHITE,20,20,300,400)
        Painter.drawText("hhhhhhaaaa",50,50)
    }

    override fun onKeyPressed(event: javafx.scene.input.KeyEvent) {
        when(event.code){
            KeyCode.ENTER -> println("ddddian")
            KeyCode.L -> Composer.play("snd/hit.wav")
        }
    }

    override fun onRefresh() {
    }
}

fun main() {
    while (true){
        println(Random().nextInt(4))
    }
    /*(0..31).forEach {
        println("${it%32}")
    }
    println(32%32)*/
//    Application.launch(TestWindow::class.java)
}
