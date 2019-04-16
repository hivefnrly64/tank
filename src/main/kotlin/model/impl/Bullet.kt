package model.impl

import business.Attackable
import business.AutoMovable
import business.Destroyable
import business.Sufferable
import config.Config
import enums.Direction
import ext.checkCollision
import model.View
import org.itheima.kotlin.game.core.Painter

/***
 **  @package : model.impl
 **  @author : Chata
 **  @description : 子弹
 **  @date : 2019/4/15 16:57
 ***/
class Bullet(override val owner: View,override val curDirection: Direction, create:(width:Int,height:Int)->Pair<Int,Int>)
    :AutoMovable,Destroyable,Attackable,Sufferable {
    override val hp: Int=1

    //子弹碰撞
    override fun notifySuffer(attack: Attackable): Array<View>? {
        return arrayOf(Blast(x,y))
    }

    //攻击力
    override val ad: Int=1
    //默认速度
    override val speed: Int=16

    override var x: Int = 0
    override var y: Int = 0

    override val width: Int
    override val height: Int

    private var isDestroyed=false

    private val imgPath:String = when (curDirection) {
        Direction.UP -> "img/shot_top.gif"
        Direction.DOWN -> "img/shot_bottom.gif"
        Direction.LEFT -> "img/shot_left.gif"
        Direction.RIGHT -> "img/shot_right.gif"
    }

    init {
        val size = Painter.size(imgPath)
        width=size[0]
        height=size[1]

        val pair = create.invoke(width,height)
        x=pair.first
        y=pair.second
    }

    override fun draw() {
        Painter.drawImage(imgPath,x,y)
    }

    override fun autoMove() {
        when(curDirection){
            Direction.UP->y-=speed
            Direction.DOWN->y+=speed
            Direction.LEFT->x-=speed
            Direction.RIGHT->x+=speed
        }
    }

    override fun isDestroyed(): Boolean {

        if(isDestroyed) return true
        //子弹在什么位置时被销毁
        if(x<-width) return true
        if(x>Config.gameWidth) return true
        if(y<-height) return true
        if(y>Config.gameHeight) return true
        return false
    }

    override fun isCollision(suffer: Sufferable): Boolean {
        return checkCollision(suffer)
    }

    override fun notifyAttack(suffer: Sufferable) {
        isDestroyed=true
    }
}