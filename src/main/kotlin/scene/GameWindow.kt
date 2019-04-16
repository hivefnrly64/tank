package scene

import business.*
import config.Config
import enums.Direction
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import model.View
import model.impl.*
import org.itheima.kotlin.game.core.Window
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.concurrent.CopyOnWriteArrayList

/***
  **  @author : Chata
  **  @description : 主窗体
  **  @date : 2019/4/15 08:01
***/
class GameWindow:Window(title = "Tank War",icon = "img/tank_u.gif",width = Config.gameWidth,height = Config.gameHeight) {

//    private val views= arrayListOf<View>()
    //改用线程安全的集合
    private val views= CopyOnWriteArrayList<View>()

    private lateinit var tank: Tank

    private var gameOver:Boolean=false

    private var enemyTotalSize=2
    private var enemyActiveSize=1
    private val enemyBornLocation= arrayListOf<Pair<Int,Int>>()
    private var bornIndex=0

    override fun onCreate() {
        //读取地图配置文件
//        val file = File(javaClass.getResource("/map/1.map").path)
        val resourceAsStream = javaClass.getResourceAsStream("/map/1.map")
        val reader = BufferedReader(InputStreamReader(resourceAsStream, "utf-8"))
        val lines:List<String> = reader.readLines()
        var lineNum=0
        lines.forEach{line->
            var colNum=0
            line.toCharArray().forEach {col->
                when(col){
                    '砖'->views.add(Wall(colNum*Config.block,lineNum*Config.block))
                    '草'->views.add(Grass(colNum*Config.block,lineNum*Config.block))
                    '水'->views.add(Water(colNum*Config.block,lineNum*Config.block))
                    '铁'->views.add(Steel(colNum*Config.block,lineNum*Config.block))
                    '敌'->enemyBornLocation.add(Pair(colNum*Config.block,lineNum*Config.block))
//                    else-> println("地图绘制中...")
                }
                colNum++
            }
            lineNum++
        }

        //渲染坦克
        tank = Tank(Config.block * 10, Config.block * 12)
        views.add(tank)

        //大本营
        views.add(Camp(Config.gameWidth/2-Config.block,Config.gameHeight-96))
    }

    override fun onDisplay() {
        views.forEach {
            it.draw()
        }

//        println("${views.size}")
    }

    override fun onKeyPressed(event: KeyEvent) {
        if(!gameOver){
            when(event.code){
                KeyCode.W->{
                    tank.move(Direction.UP)
                }
                KeyCode.S->{
                    tank.move(Direction.DOWN)
                }
                KeyCode.A->{
                    tank.move(Direction.LEFT)
                }
                KeyCode.D->{
                    tank.move(Direction.RIGHT)
                }
                KeyCode.J->{
                    val bullet = tank.shot()
                    views.add(bullet)
                }
            }
        }
    }

    //实现业务逻辑
    override fun onRefresh() {
        //回收无用的东西
        views.filter { it is Destroyable }.forEach {
            if((it as Destroyable).isDestroyed()){
                views.remove(it)
                if(it is Enemy)
                    enemyTotalSize--
                val destroy = it.showDestroy()
                destroy?.let {
                    views.addAll(destroy)
                }
            }
        }

        if(gameOver)
            return

        //碰撞
        views.filter { it is Movable }.forEach {m->
            m as Movable
            var badDirection:Direction?=null
            var badBlock:Blockable?=null

            //不和自己比较
            views.filter { (it is Blockable)&&(m!=it) }.forEach bTag@{ b->
                //判断是否发生碰撞
                b as Blockable
                val d:Direction? = m.collision(b)
                //如果发生碰撞
                d?.let {
                    badDirection=d
                    badBlock=b
                    return@bTag
                }
            }
            m.notifyCollision(badDirection,badBlock)
        }

        //子弹自动移动
        views.filter { it is AutoMovable }.forEach {
            (it as AutoMovable).autoMove()
        }


        //子弹是否产生碰撞
        views.filter { it is Attackable }.forEach {att->
            att as Attackable
            views.filter { (it is Sufferable) and (att.owner!=it) and (att!=it)}.forEach sufTag@{suf->
                suf as Sufferable
                if(att.isCollision(suf)){
                    //通知攻击与被攻击者
                    att.notifyAttack(suf)
                    val suffer = suf.notifySuffer(att)
                    suffer?.let {
                        //展示被攻击的效果
                        views.addAll(suffer)
                    }
                    return@sufTag
                }
            }
        }

        //自动射击检测
        views.filter { it is AutoShot }.forEach {
            it as AutoShot
            val shot = it.autoShot()
            shot?.let {
                views.add(shot)
            }
        }

        //游戏是否结束
        if((views.filter { it is Camp }.isEmpty()) or (enemyTotalSize<=0)){
            gameOver=true
        }

        //敌人产生位置
        if((enemyTotalSize>0) and (views.filter { it is Enemy }.size<enemyActiveSize)){
            val index=bornIndex%enemyBornLocation.size
            val pair = enemyBornLocation[index]
            views.add(Enemy(pair.first,pair.second))
            bornIndex++
        }
    }
}