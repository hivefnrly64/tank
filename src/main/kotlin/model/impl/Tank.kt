package model.impl

import business.Attackable
import business.Blockable
import business.Movable
import business.Sufferable
import config.Config
import enums.Direction
import model.View
import org.itheima.kotlin.game.core.Painter

/***
 **  @package : model.impl
 **  @author : Chata
 **  @description : 坦克
 **  @date : 2019/4/15 08:56
 ***/
class Tank(override var x: Int, override var y: Int) :Movable,Blockable,Sufferable {
    override var hp: Int=300
    override val width: Int= Config.block
    override val height: Int= Config.block
    //默认tank方向朝上
    override var curDirection:Direction=Direction.UP
    //默认速度
    override val speed:Int=8
    //坦克不能前进的方向
    private var badDirection:Direction?=null
    //渲染坦克
    override fun draw() {
        val tankPath = when (curDirection) {
            Direction.UP -> "img/tank_u.gif"
            Direction.DOWN -> "img/tank_d.gif"
            Direction.LEFT -> "img/tank_l.gif"
            Direction.RIGHT -> "img/tank_r.gif"
        }
        Painter.drawImage(tankPath,x,y)
    }

    fun move(direction:Direction){

        //是否往错误的方向移动
        if(direction==badDirection)
            return

        if(this.curDirection!=direction){
            this.curDirection=direction
            return
        }

        when(curDirection){
            Direction.UP-> y-=speed
            Direction.DOWN-> y+=speed
            Direction.LEFT-> x-=speed
            Direction.RIGHT-> x+=speed
        }

        //边界判断
        if(x<0) x=0
        if(x>Config.gameWidth-width) x=Config.gameWidth-width
        if(y<0) y=0
        if(y>Config.gameHeight-height) y=Config.gameHeight-height

    }

    override fun notifyCollision(direction: Direction?, block: Blockable?) {
        this.badDirection=direction
    }

    /**
     * 射击子弹
     */
    fun shot( ) :Bullet{

        return Bullet(this,curDirection,{bulletWidth,bulletHeight->
            val tankX=x
            val tankY=y
            val tankWidth=width
            val tankHeight=height
            //计算射击时子弹的初始位置
            var bulletX=0
            var bulletY=0
            when(curDirection){
                Direction.UP->{
                    bulletX=tankX+(tankWidth-bulletWidth)/2
                    bulletY=tankY-bulletHeight/2
                }
                Direction.DOWN->{
                    bulletX=tankX+(tankWidth-bulletWidth)/2
                    bulletY=tankY+tankHeight-bulletHeight/2
                }
                Direction.LEFT->{
                    bulletX=tankX-bulletWidth/2
                    bulletY=tankY+(tankHeight-bulletHeight)/2
                }
                Direction.RIGHT->{
                    bulletX=tankX+tankWidth-bulletWidth/2
                    bulletY=tankY+(tankHeight-bulletHeight)/2
                }
            }
            Pair(bulletX,bulletY)
        })
    }

    override fun notifySuffer(attack: Attackable): Array<View>? {
        hp-=attack.ad
        return arrayOf(Blast(x,y))
    }
}