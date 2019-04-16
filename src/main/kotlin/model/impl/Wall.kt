package model.impl

import business.Attackable
import business.Blockable
import business.Destroyable
import business.Sufferable
import config.Config
import model.View
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter
/***
 **  @package : model
 **  @author : Chata
 **  @description : 墙体
 **  @date : 2019/4/15 08:13
 ***/
class Wall(override val x:Int,override val y:Int) :Blockable,Sufferable,Destroyable{

    override val width: Int= Config.block
    override val height: Int= Config.block

    override var hp: Int=3

    override fun draw() {
        Painter.drawImage("img/wall.gif",x,y)
    }

    //每次被攻击掉血
    override fun notifySuffer(attack: Attackable):Array<View>? {
        hp-=attack.ad
        Composer.play("snd/hit.wav")
        return arrayOf(Blast(x,y))
    }

    //如果生命值小于0则销毁
    override fun isDestroyed(): Boolean =hp<=0
}