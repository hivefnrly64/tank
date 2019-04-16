package model.impl

import business.Destroyable
import config.Config
import model.View
import org.itheima.kotlin.game.core.Painter

/***
 **  @package : model.impl
 **  @author : Chata
 **  @description : boom!!
 **  @date : 2019/4/15 19:13
 ***/
class Blast(override val x: Int, override val y: Int) :Destroyable {
    override val width: Int=Config.block
    override val height: Int=Config.block
    private val imgPaths= arrayListOf<String>()
    private var index:Int=0

    init {
        (1..32).forEach {
            imgPaths.add("img/blast_$it.png")
        }
    }

    override fun draw() {
        val i=index%imgPaths.size
        Painter.drawImage(imgPaths[i],x,y)
        index++
    }


    override fun isDestroyed(): Boolean = index>=imgPaths.size
}