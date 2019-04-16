package model

/***
 **  @package : model
 **  @author : Chata
 **  @description : 抽象视图
 **  @date : 2019/4/15 08:24
 ***/
interface View {
    val x:Int
    val y:Int

    val width:Int
    val height:Int

    fun draw()

    fun checkCollision(x1:Int,y1:Int,w1:Int,h1:Int,
                       x2:Int,y2:Int,w2:Int,h2:Int):Boolean{
        //两个物体的长宽xy较
          return when {
                y2+h2<=y1 -> //正上方
                    false
                y1+h1<=y2 -> //正下方
                    false
                x2+w2<=x1 -> //左方
                    false
                else -> x1+w1 > x2
            }
    }

}