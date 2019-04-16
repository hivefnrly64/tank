package business

import config.Config
import enums.Direction
import model.View

/***
 **  @package : business
 **  @author : Chata
 **  @description : 移动接口
 **  @date : 2019/4/15 09:37
 ***/
interface Movable:View {
    val curDirection:Direction
    val speed:Int
    /**
     * 碰撞检测
     */
    fun collision(block:Blockable):Direction?{
        //在碰撞之前检测坐标
        var x=this.x
        var y=this.y

        when(curDirection){
            Direction.UP-> y-=speed
            Direction.DOWN-> y+=speed
            Direction.LEFT-> x-=speed
            Direction.RIGHT-> x+=speed
        }

        if(x<0) return Direction.LEFT
        if(x> Config.gameWidth-width) return Direction.RIGHT
        if(y<0) return Direction.UP
        if(y> Config.gameHeight-height) return Direction.DOWN

        return if (checkCollision(
                block.x, block.y, block.width, block.height,
                x, y, width, height)) curDirection else null
    }

    /**
     * 碰撞通知
     */
    fun notifyCollision(direction:Direction?,block:Blockable?)
}