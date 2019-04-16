package model.impl

import business.Attackable
import business.Blockable
import business.Destroyable
import business.Sufferable
import config.Config
import model.View
import org.itheima.kotlin.game.core.Painter

/***
 **  @package : model.impl
 **  @author : Chata
 **  @description : 大本营
 **  @date : 2019/4/15 21:33
 ***/
class Camp(override var x: Int, override var y: Int) :Blockable,Sufferable,Destroyable {
    override var width: Int=Config.block*2
    override var height: Int=Config.block+32
    override var hp: Int=12
    override fun notifySuffer(attack: Attackable): Array<View>? {
        println(hp)
        hp-=attack.ad
        if(hp==3 || hp==6)
            return arrayOf(
                Blast(x,y),
                Blast(x+32,y),
                Blast(x+64,y),
                Blast(x+96,y),
                Blast(x+96,y+32),
                Blast(x+96,y+64),
                Blast(x,y+32),
                Blast(x,y+64)
            )
        return null
    }

    override fun isDestroyed(): Boolean =hp<=0
    override fun showDestroy(): Array<View>? {
        return arrayOf(
            Blast(x-32,y-32),
            Blast(x,y-32),
            Blast(x+32,y-32),

            Blast(x-32,y),
            Blast(x,y),
            Blast(x+32,y),

            Blast(x-32,y+32),
            Blast(x,y+32),
            Blast(x+32,y+32)
        )
    }

    override fun draw() {

        val img = when (hp) {
            in 1..3 -> ""
            in 3..6 -> "wall_small.gif"
            0->"boom"
            else -> "steel_small.gif"
        }

        armor(img)


    }

    private fun armor(img:String){

        if(img=="") {
            width=Config.block
            height=Config.block
            x=(Config.gameWidth-Config.block)/2
            y=Config.gameHeight-Config.block
            Painter.drawImage("img/camp.gif", x, y)
        } else{
            Painter.drawImage("img/camp.gif",x+32,y+32)
            //绘制外围的砖块
            Painter.drawImage("img/$img",x,y)
            Painter.drawImage("img/$img",x+32,y)
            Painter.drawImage("img/$img",x+64,y)
            Painter.drawImage("img/$img",x+96,y)

            Painter.drawImage("img/$img",x+96,y+32)
            Painter.drawImage("img/$img",x+96,y+64)
            Painter.drawImage("img/$img",x,y+32)
            Painter.drawImage("img/$img",x,y+64)
        }
    }
}