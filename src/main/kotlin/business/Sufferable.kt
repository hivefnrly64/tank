package business

import model.View

/***
 **  @package : business
 **  @author : Chata
 **  @description : 能被攻击的能力
 **  @date : 2019/4/15 18:22
 ***/
interface Sufferable:View {

    //生命值
    val hp:Int

    //通知
    fun notifySuffer(attack:Attackable):Array<View>?
}