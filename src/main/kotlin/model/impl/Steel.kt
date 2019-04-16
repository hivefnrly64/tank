package model.impl

import business.Attackable
import business.Blockable
import business.Sufferable
import config.Config
import model.View
import org.itheima.kotlin.game.core.Painter
/***
 **  @package : model
 **  @author : Chata
 **  @description : 铁墙
 **  @date : 2019/4/15 08:17
 ***/
class Steel (override val x:Int,override val y:Int):Blockable,Sufferable{
    override val hp: Int=1

    override val width: Int= Config.block

    override val height: Int= Config.block
    override fun draw() {
        Painter.drawImage("img/steel.gif",x,y)
    }

    override fun notifySuffer(attack: Attackable): Array<View>? {
        return null
    }
}