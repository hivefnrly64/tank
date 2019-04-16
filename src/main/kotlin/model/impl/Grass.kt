package model.impl

import config.Config
import model.View
import org.itheima.kotlin.game.core.Painter
/***
 **  @package : model
 **  @author : Chata
 **  @description : 草
 **  @date : 2019/4/15 08:17
 ***/
class Grass (override val x:Int,override val y:Int):View{

    override val width: Int= Config.block
    override val height: Int= Config.block

    override fun draw() {
        Painter.drawImage("img/grass.gif",x,y)
    }
}