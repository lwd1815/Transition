package com.example.propertyanimation.airplaneview.logic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.example.propertyanimation.R;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建者     李文东
 * 创建时间   2017/12/15 12:07
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public class GameView extends View {

  private Paint paint;
  private Paint textPaint;
  /**
   * 战斗机
   */
  private CombatAircraft combatAircraft = null;
  private List<Sprite> sprites = new ArrayList<Sprite>();
  private List<Sprite> spritesNeedAdded = new ArrayList<Sprite>();
  //0:combatAircraft
  //1:explosion
  //2:yellowBullet
  //3:blueBullet
  //4:smallEnemyPlane
  //5:middleEnemyPlane
  //6:bigEnemyPlane
  //7:bombAward
  //8:bulletAward
  //9:pause1
  //10:pause2
  //11:bomb
  /**
   * bitmaps集合中用来收集存放飞机,子弹等一系列图片
   */
  private List<Bitmap> bitmaps = new ArrayList<Bitmap>();
  private float density = getResources().getDisplayMetrics().density;//屏幕密度
  public static final int STATUS_GAME_STARTED = 1;//游戏开始
  public static final int STATUS_GAME_PAUSED = 2;//游戏暂停
  public static final int STATUS_GAME_OVER = 3;//游戏结束
  public static final int STATUS_GAME_DESTROYED = 4;//游戏销毁
  private int status = STATUS_GAME_DESTROYED;//初始为销毁状态
  private long frame = 0;//总共绘制的帧数
  private long score = 0;//总得分
  private float fontSize = 12;//默认的字体大小，用于绘制左上角的文本
  private float fontSize2 = 20;//用于在Game Over的时候绘制Dialog中的文本
  private float borderSize = 2;//Game Over的Dialog的边框
  private Rect continueRect = new Rect();//"继续"、"重新开始"按钮的Rect

  //触摸事件相关的变量
  private static final int TOUCH_MOVE = 1;//移动
  private static final int TOUCH_SINGLE_CLICK = 2;//单击
  private static final int TOUCH_DOUBLE_CLICK = 3;//双击
  //一次单击事件由DOWN和UP两个事件合成，假设从down到up间隔小于200毫秒，我们就认为发生了一次单击事件
  private static final int singleClickDurationTime = 200;
  //一次双击事件由两个点击事件合成，两个单击事件之间小于300毫秒，我们就认为发生了一次双击事件
  private static final int doubleClickDurationTime = 300;
  private long lastSingleClickTime = -1;//上次发生单击的时刻
  private long touchDownTime = -1;//触点按下的时刻
  private long touchUpTime = -1;//触点弹起的时刻
  private float touchX = -1;//触点的x坐标
  private float touchY = -1;//触点的y坐标

  public GameView(Context context) {
    super(context);
    init(null, 0);
  }

  public GameView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(attrs, 0);
  }

  public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs, defStyleAttr);
  }

  private void init(AttributeSet attrs, int defStyle) {
    final TypedArray a =
        getContext().obtainStyledAttributes(attrs, R.styleable.GameView, defStyle, 0);
    a.recycle();
    //初始化paint
    paint = new Paint();
    paint.setStyle(Paint.Style.FILL);
    //设置textPaint，设置为抗锯齿，且是粗体
    textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.FAKE_BOLD_TEXT_FLAG);
    textPaint.setColor(0xff000000);
    fontSize = textPaint.getTextSize();
    fontSize *= density;
    fontSize2 *= density;
    textPaint.setTextSize(fontSize);
    borderSize *= density;
  }

  public void start(int[] bitmapIds) {
    destory();
    for (int bitmapId:bitmapIds) {
      Bitmap bitmap = BitmapFactory.decodeResource(getResources(), bitmapId);
      bitmaps.add(bitmap);
    }

    startWhenBitmapReady();
  }

  private void startWhenBitmapReady() {
    combatAircraft=new CombatAircraft(bitmaps.get(0));
    status=STATUS_GAME_STARTED;
    postInvalidate();
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    int touchType=resolveTouchType(event);
    if (status==STATUS_GAME_STARTED){
      //如果是刚开始状态.且是移动状态
      if (touchType==TOUCH_MOVE){
        //让战斗机显示到居中正下方的位置  touchX touchY 触点的x坐标和y坐标
        if (combatAircraft!=null){
          combatAircraft.centerTo(touchX,touchY);
        }
      }
    }
    return true;
  }

  /**
   * 根据按下和抬起的时间差,合成一个想要的事件类型
   */

  private int resolveTouchType(MotionEvent event){
    int touchType=-1;
    int action=event.getAction();
    touchX=event.getX();
    touchY=event.getY();
    if (action==MotionEvent.ACTION_MOVE){
      //获取手指与屏幕的接触时间
      long deltaTime=System.currentTimeMillis()-touchDownTime;
      if (deltaTime>singleClickDurationTime){
        touchType=TOUCH_MOVE;
      }
    }else if (action==MotionEvent.ACTION_DOWN){
      touchDownTime=System.currentTimeMillis();
    }else if (action==MotionEvent.ACTION_UP){
      //触点弹起时间,获取手指抬起时间
      touchUpTime=System.currentTimeMillis();
      //计算按下到抬起的持续时间
      long downUpDurationTime=touchUpTime-touchDownTime;
      if (downUpDurationTime<singleClickDurationTime){
        //计算这次单击距离上次单击的时间差
       long twoClickDurationTime= touchUpTime-lastSingleClickTime;
        if (twoClickDurationTime<doubleClickDurationTime){
          //由于时间差小于定义的双击的时间间隔,所以可以认为是双击
          touchType=TOUCH_DOUBLE_CLICK;
          //重置变量
          lastSingleClickTime=-1;
          touchDownTime=-1;
          touchUpTime=-1;
        }else {
          //如果是单击事件
          lastSingleClickTime=touchUpTime;
        }
      }
    }
    return touchType;
  }
  public void restart(){
    destroNotRecycleBitmaps();
    startWhenBitmapReady();
  }
  public void pause() {
    status=STATUS_GAME_PAUSED;
  }

  public void resume(){
    status=STATUS_GAME_STARTED;
    postInvalidate();
  }
  public void destory() {
    destroNotRecycleBitmaps();

    for (Bitmap bitmap:bitmaps){
      bitmap.recycle();
    }
    bitmaps.clear();
  }

  /**
   * 销毁没有释放的bitamap'
   */

  public void destroNotRecycleBitmaps() {
    status = STATUS_GAME_DESTROYED;
    frame = 0;
    score = 0;

    if (combatAircraft != null) {
      combatAircraft.destory();
    }
    combatAircraft = null;

    for (Sprite s : sprites) {
      s.destory();
    }
    sprites.clear();
  }
}
