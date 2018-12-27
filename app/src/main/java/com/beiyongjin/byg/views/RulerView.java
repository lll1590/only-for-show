package com.beiyongjin.byg.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Scroller;

import com.beiyongjin.byg.R;

import java.util.List;

/**
 * Author: 沈志华
 * E-mail: shenzh@erongdu.com
 * Date: 2017/8/9$ 11:26$
 * 标尺控件
 */
public class RulerView extends View {
    /**屏幕宽度*/
    private int screenWidth;
    /**控件高度*/
    private int viewHeight;
    /**view宽度*/
    private int viewWidth;
    /**m每一小格的长度*/
    private int oneItemValue;
    /**端点节点*/
    private List<String> list;
    /**滑动监听器*/
    private OnRulerChangeListener rulerListener;
    /**滚动计算类*/
    private Scroller scroller;
    /**手势监听*/
    private GestureDetector gestureDetector;
    /**偏移量*/
    private int offset;
    /**绘制的开始位置*/
    private int location;
    /**显示哪个list值*/
    private int distanceInteger;
    /**游标颜色*/
    private int cursorColor;
    /**刻度颜色*/
    private int scaleColor;
    /**刻度字体颜色*/
    private int scaleTextColor;
    /**刻度字体大小*/
    private int scaleTextSize;
    /**手指抬起时的偏移量*/
    private int upOffset;
    /**是否标尺往回走*/
    private boolean isBack;

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setRulerListener(OnRulerChangeListener rulerListener) {
        this.rulerListener = rulerListener;
    }

    public RulerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager  windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm            = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.RulerView);
        cursorColor = typedArray.getColor(R.styleable.RulerView_cursor_color, Color.rgb(255,0,0));
        scaleColor = typedArray.getColor(R.styleable.RulerView_scale_color,Color.argb(255,102,102,102));
        scaleTextColor = typedArray.getColor(R.styleable.RulerView_scale_text_color,Color.argb(255,102,102,102));
        scaleTextSize = typedArray.getDimensionPixelOffset(R.styleable.RulerView_scale_text_size,36);
        screenWidth = dm.widthPixels;
        oneItemValue = screenWidth/20;
        //滚动计算器
        scroller = new Scroller(context);
        //一定要加，不然只会收到onDown,onShowPress,onLongPress3个事件
        setClickable(true);
        //手势解析
        gestureDetector = new GestureDetector(context, gestureListener);
        //是否允许长点击
        gestureDetector.setIsLongpressEnabled(false);
        location=screenWidth/2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        viewWidth=oneItemValue*(list.size()-1)*5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBottomLine(canvas);
        drawCursor(canvas);
        drawScale(canvas);
    }
    /**绘制刻度*/
    private void drawScale(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(3);
        paint.setColor(scaleColor);
        Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setTextSize(scaleTextSize);
        paintText.setColor(scaleTextColor);
        for(int i=0;i <= (list.size()-1)*5;i++){
            int currentLocation=location+i*oneItemValue;
            if(i % 5 == 0){
                String drawStr;
                drawStr = list.get(i/5);
                Rect bounds = new Rect();
                paintText.getTextBounds(drawStr, 0, drawStr.length(), bounds);
                canvas.drawText(drawStr, currentLocation - bounds.width() / 2, viewHeight-viewHeight/4, paintText);
                canvas.drawLine(currentLocation, viewHeight-viewHeight/5, currentLocation, viewHeight, paint);
            }else{
                canvas.drawLine(currentLocation, viewHeight-viewHeight/8, currentLocation, viewHeight, paint);
            }
        }
    }

    /**绘制游标*/
    private void drawCursor(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(3);
        paint.setColor(cursorColor);
        if(scroller.computeScrollOffset()){
            canvas.drawLine(screenWidth/2+scroller.getCurrX(), 0, screenWidth/2+scroller.getCurrX(), viewHeight, paint);
        }else{
            canvas.drawLine(screenWidth/2+offset, 0, screenWidth/2+offset, viewHeight, paint);
        }
    }

    /**绘制底部横线*/
    private void drawBottomLine(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(3);
        paint.setColor(Color.rgb(102,102,102));
        if(scroller.computeScrollOffset()){
            canvas.drawLine(scroller.getCurrX(),viewHeight,screenWidth+scroller.getCurrX(), viewHeight, paint);
        }else{
            canvas.drawLine(offset, viewHeight, screenWidth+offset, viewHeight, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        if(event.getAction() == MotionEvent.ACTION_UP){
            changeValue();
            upOffset = offset;
            offset = distanceInteger *5*oneItemValue;
            if(isBack){
                scroller.startScroll(upOffset,0,-(upOffset-offset),0,1000);
            }else{
                scroller.startScroll(upOffset,0,(offset-upOffset),0,1000);
            }
            invalidate();
        }
        return super.onTouchEvent(event);
    }
    /**改变textView*/
    private void changeValue(){
        distanceInteger= offset/(oneItemValue*5);
        int distanceRemainder = offset % (oneItemValue*5);
        if(distanceRemainder >=oneItemValue*5/2){
            distanceInteger=distanceInteger+1;
            isBack=false;
        }else{
            isBack=true;
        }
        if(rulerListener != null){
            if(distanceInteger >= list.size()){
                distanceInteger = list.size() -1;
            }
            rulerListener.setValue(Integer.valueOf(list.get(distanceInteger)));
        }
    }
    private GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener(){
        /**
         * @param e1 滑动事件的起点（也就是说onDown()的时候）
         * @param e2 当前滑动位置点(手指的位置)
         * @param distanceX 上次滑动(调用onScroll)到这次滑动的X轴的距离px
         * @param distanceY 上次滑动(调用onScroll)到这次滑动的Y轴的距离px
         */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(offset >= viewWidth && distanceX >= 0){
                distanceX =0;
                offset=viewWidth;
            }else if(offset <= 0 && distanceX <= 0){
                distanceX = 0;
                offset =0;
            }
            scroller.forceFinished(true);
            offset=offset+(int)distanceX;
            scrollTo(offset,0);
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        /***
         * @param e1 拖动动事件的起点（也就是说onDown()的时候）
         * @param e2 onFling()调用时，手指的位置
         * @param velocityX X轴上每秒滑动像素值
         * @param velocityY Y轴上每秒滑动像素值
         * 当拖动速率velocityX或velocityY超过ViewConfiguration.getMinimumFlingVelocity()最小拖动速率时，才会调用onFling()，
         * 也就是如果只拖动一点，或是慢慢的拖动，是不会触发该方法
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            scroller.fling(offset, 0, (int) (-velocityX / 1.5), 0, 0, viewWidth, 0, 0);
            return super.onFling(e1, e2, velocityX, velocityY);

        }
    };
    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),0);
            invalidate();
        }else{
            scrollTo(offset,0);
        }
    }
}
