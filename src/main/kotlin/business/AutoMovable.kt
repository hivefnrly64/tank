package business

import enums.Direction
import model.View

/***
 **  @package : business
 **  @author : Chata
 **  @description : 自动移动
 **  @date : 2019/4/15 17:54
 ***/
interface AutoMovable:View {

    //方向
    val curDirection:Direction
    //速度
    val speed:Int

    fun autoMove()
}