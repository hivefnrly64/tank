package ext

import model.View

/***
 **  @package : ext
 **  @author : Chata
 **  @description : 拓展view
 **  @date : 2019/4/15 18:42
 ***/
fun View.checkCollision(view:View):Boolean{
    return checkCollision(x,y,width,height,
        view.x,view.y,view.width,view.height)
}