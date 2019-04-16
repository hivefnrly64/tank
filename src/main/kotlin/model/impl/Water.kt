package model.impl

import business.Blockable
import config.Config
import model.View
import org.itheima.kotlin.game.core.Painter
/***
 **  @package : model
 **  @author : Chata
 **  @description : 水
 **  @date : 2019/4/15 08:17
 ***/
class Water (override val x:Int,override val y:Int): Blockable {

    override val width: Int= Config.block
    override val height: Int= Config.block

    override fun draw() {
        Painter.drawImage("img/water.gif",x,y)
    }
}