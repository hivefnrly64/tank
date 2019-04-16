package business

import model.View

/***
 **  @package : business
 **  @author : Chata
 **  @description : 销毁
 **  @date : 2019/4/15 18:08
 ***/
interface Destroyable:View {
    //是否被销毁
    fun isDestroyed():Boolean
    fun showDestroy():Array<View>?{return null}
}