package business

import model.View

/***
 **  @package : business
 **  @author : Chata
 **  @description : 具备攻击的能力
 **  @date : 2019/4/15 18:21
 ***/
interface Attackable:View {
    //拥有者
    val owner:View
    //攻击力
    val ad:Int
    //是否碰撞
    fun isCollision(suffer:Sufferable):Boolean
    //通知
    fun notifyAttack(suffer:Sufferable)
}