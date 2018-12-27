package com.beiyongjin.byg.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Scroller;

import java.util.List;

public class HomeSeekBar extends AppCompatSeekBar {
    private GestureDetector gestureDetector;
    private Drawable mThumb;
    private OnSeekBarChangeListener mOnSeekBarChangeListener;
    private Scroller scroller;
    /**手指抬起时的偏移量*/
    private int upOffset;
    /**偏移量*/
    private int offset;
    /*滑动的节点*/
    private List<String> list;
    /**滑动监听器*/
    private OnRulerChangeListener SeekBarListener;
    /**显示哪个list值*/
    private int distanceInteger;
    /**m每一小格的长度*/
    private int oneItemValue;
    //是否滚动到对应节点
    private boolean isBack;
    /**控件高度*/
    private int viewHeight;
    /**view宽度*/
    private int viewWidth;
    /**屏幕宽度*/
    private int screenWidth;


    public void setList(List<String> list) {
        this.list = list;
    }

    public void setHomeSeekBarListener(OnRulerChangeListener rulerListener) {
        this.SeekBarListener = rulerListener;
    }
    public HomeSeekBar(Context context) {
        super(context);
    }

    public HomeSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
      /*  WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm            = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        oneItemValue = screenWidth/20;
        //滚动计算器
        scroller = new Scroller(context);
        //一定要加，不然只会收到onDown,onShowPress,onLongPress3个事件
        setClickable(true);
        //手势解析
        gestureDetector = new GestureDetector(context, gestureListener);
        //是否允许长点击
        gestureDetector.setIsLongpressEnabled(false);*/


    }



    public HomeSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setThumb(Drawable thumb) {
        super.setThumb(thumb);
        mThumb = thumb;
    }

    public Drawable getSeekBarThumb() {
        return mThumb;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        viewWidth=oneItemValue*(list.size()-1)*5;
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);

        if (progress == getProgress()) {
            if (mOnSeekBarChangeListener != null) {
                mOnSeekBarChangeListener.onProgressChanged(this, getProgress(), false);
                mOnSeekBarChangeListener.onStopTrackingTouch(this);
             //  Log.i("hjc","seekBar的量："+progress);
            }
        }
    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
        mOnSeekBarChangeListener = l;
        super.setOnSeekBarChangeListener(l);
    }

    /*
    * 监听手势动作，在我们手拿起来的时候，检测点所在位置，再进行偏移
    * */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                super.setProgress((int) (event.getX() * 100 / getWidth()));
                return true;
            default:
                return super.onTouchEvent(event);
        }

     /*  gestureDetector.onTouchEvent(event);
        if(event.getAction()==MotionEvent.ACTION_UP){
          //  changeValue();
            upOffset = offset;
            offset = distanceInteger *5*oneItemValue;
            Log.i("hjc","偏移量："+offset);
           *//* if(isBack){
                scroller.startScroll(upOffset,0,-(upOffset-offset),0,1000);
            }else{
                scroller.startScroll(upOffset,0,(offset-upOffset),0,1000);
            }*//*
            invalidate();


        }
        return true;*/
    }

  /*  //改变控件上面显示的额度
    private void changeValue(){
        distanceInteger= offset/(oneItemValue*5);
        int distanceRemainder = offset % (oneItemValue*5);
        if(distanceRemainder >=oneItemValue*5/2){
            distanceInteger=distanceInteger+1;
            isBack=false;
        }else{
            isBack=true;
        }
        if(SeekBarListener != null){
            if(distanceInteger >= list.size()){
                distanceInteger = list.size() -1;
            }
            SeekBarListener.setValue(Integer.valueOf(list.get(distanceInteger)));
        }
    }*/
/*
    //这里是手势滑动监听
    private GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener(){
        *//**
         * @param e1 滑动事件的起点（也就是说onDown()的时候）
         * @param e2 当前滑动位置点(手指的位置)
         * @param distanceX 上次滑动(调用onScroll)到这次滑动的X轴的距离px
         * @param distanceY 上次滑动(调用onScroll)到这次滑动的Y轴的距离px
         *//*
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

        *//***
         * @param e1 拖动动事件的起点（也就是说onDown()的时候）
         * @param e2 onFling()调用时，手指的位置
         * @param velocityX X轴上每秒滑动像素值
         * @param velocityY Y轴上每秒滑动像素值
         * 当拖动速率velocityX或velocityY超过ViewConfiguration.getMinimumFlingVelocity()最小拖动速率时，才会调用onFling()，
         * 也就是如果只拖动一点，或是慢慢的拖动，是不会触发该方法
         *//*
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
    }*/


}





