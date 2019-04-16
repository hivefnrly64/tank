package model.impl

import business.*
import config.Config
import enums.Direction
import model.View
import org.itheima.kotlin.game.core.Painter

/***
 **  @package : model.impl
 **  @author : Chata
 **  @description : 敌人
 **  @date : 2019/4/15 19:43
 ***/
class Enemy(override var x: Int, override var y: Int) :Movable,AutoMovable,Blockable,AutoShot,Sufferable,Destroyable {
    override var hp: Int=3
    override val width: Int=Config.block
    override val height: Int=Config.block

    override var curDirection: Direction=Direction.DOWN
    override val speed: Int=7

    //移动和射击频率
    private var lastShotTime=0L
    private var shotFrequency=800
    private var lastMoveTime=0L
    private var moveFrequency=20

    //坦克不能前进的方向
    private var badDirection:Direction?=null

    override fun notifyCollision(direction: Direction?, block: Blockable?) {
        badDirection=direction
    }

    override fun draw() {
        val tankPath = when (curDirection) {
            Direction.UP -> "img/enemy_1_u.gif"
            Direction.DOWN -> "img/enemy_1_d.gif"
            Direction.LEFT -> "img/enemy_1_l.gif"
            Direction.RIGHT -> "img/enemy_1_r.gif"
        }
        Painter.drawImage(tankPath,x,y)
    }

    override fun autoMove() {
        val cur = System.currentTimeMillis()
        if(cur-lastMoveTime<moveFrequency)
            return
        lastMoveTime=cur

        if(curDirection==badDirection){
            curDirection=randomDirection(badDirection)
            return
        }

        when(curDirection){
            Direction.UP-> y-=speed
            Direction.DOWN-> y+=speed
            Direction.LEFT-> x-=speed
            Direction.RIGHT-> x+=speed
        }

        if(x<0) x=0
        if(x>Config.gameWidth-width) x=Config.gameWidth-width
        if(y<0) y=0
        if(y>Config.gameHeight-height) y=Config.gameHeight-height
    }

   tailrec private fun randomDirection(bad:Direction?):Direction{
        val i = java.util.Random().nextInt(4)
        val direction = when (i) {
            0 -> Direction.UP
            1 -> Direction.DOWN
            2 -> Direction.LEFT
            3 -> Direction.RIGHT
            else->Direction.DOWN
        }
        if(direction==bad)
            return randomDirection(bad)
        return direction
    }

    //自动射击
    override fun autoShot(): View? {
        val cur = System.currentTimeMillis()
        if(cur-lastShotTime<shotFrequency)
            return null
        lastShotTime=cur

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
        if(attack.owner is Enemy)
            return null

        hp-=attack.ad
        return arrayOf(Blast(x,y))
    }

    override fun isDestroyed(): Boolean =hp<=0
}