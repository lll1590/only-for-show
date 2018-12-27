package com.beiyongjin.byg.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.beiyongjin.byg.R;

/**
 *
 */
public class TasksCompletedView extends View {
    // 画实心圆的画笔
    private Paint mCirclePaint;
    //画外层圆环画笔
    private Paint mRoundPaint;
    // 画圆环的画笔
    private Paint mRingPaint;
    // 画字体的画笔
    private Paint mTextPaint;
    // 圆形颜色
    private int   mCircleColor;
    //最外层圆环颜色
    private int   mRoundColor;
    //最外层圆环半径
    private int   mRoundWidth;
    private Paint mLimitPaint;
    //额度文字
    // 圆环颜色
    private int   mRingColor;
    // 半径
    private float mRadius;
    // 圆环半径
    private float mRingRadius;
    // 圆环宽度
    private float mStrokeWidth;
    // 圆心x坐标
    private int   mXCenter;
    // 圆心y坐标
    private int   mYCenter;
    // 字的长度
    private float mTxtWidth;
    private float mLimitTextWidth;
    // 字的高度
    private float mTxtHeight;
    // 总进度
    private int mTotalProgress = 100;
    // 当前进度
    private double   mProgress;
    private String   txt;
    private String   text;
    private TextView textView;

    public TasksCompletedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取自定义的属性
        initAttrs(context, attrs);
        initVariable();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TasksCompletedView, 0, 0);
        mRadius = typeArray.getDimension(R.styleable.TasksCompletedView_radius, 80);
        mStrokeWidth = typeArray.getDimension(R.styleable.TasksCompletedView_strokeWidth, 10);
        mCircleColor = typeArray.getColor(R.styleable.TasksCompletedView_circleColor, 0xFFFFFFFF);
        mRingColor = typeArray.getColor(R.styleable.TasksCompletedView_ringColor, 0xFFFFFFFF);
        mRoundColor = typeArray.getColor(R.styleable.TasksCompletedView_roundColor, 0xFFFFFFFF);
        mRoundWidth = (int) typeArray.getDimension(R.styleable.TasksCompletedView_strokeWidth, 150);
        mRingRadius = mRadius + mStrokeWidth / 2;
    }

    private void initVariable() {

        mRoundPaint = new Paint();
        mRoundPaint.setAntiAlias(true);
        mRoundPaint.setColor(mRoundColor);
        mRoundPaint.setStyle(Paint.Style.STROKE);
        mRoundPaint.setStrokeWidth(2 * mStrokeWidth);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mStrokeWidth);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(getResources().getColor(R.color.text_dark));
        mTextPaint.setTextSize(spToPx(45));

        mLimitPaint = new Paint();
        mLimitPaint.setAntiAlias(true);
        mLimitPaint.setStyle(Paint.Style.FILL);
        mLimitPaint.setColor(getResources().getColor(R.color.text_grey));
        mLimitPaint.setTextSize(spToPx(14));

        FontMetrics fm = mTextPaint.getFontMetrics();
        mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;

        canvas.drawCircle(mXCenter, mYCenter, mRadius, mRoundPaint);
        String limitText = "总额度(元)";
        canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);
        mLimitTextWidth = mLimitPaint.measureText(limitText, 0, limitText.length());
        canvas.drawText(limitText, mXCenter - mLimitTextWidth / 2, (mYCenter - mTxtHeight / 2) + 30, mLimitPaint);
        if (txt == null || txt.equals("")) {
            txt = "0";
            mTxtWidth = mTextPaint.measureText(txt, 0, txt.length());
            canvas.drawText(txt, mXCenter - mTxtWidth / 2, mYCenter + mTxtHeight / 2, mTextPaint);
        } else {
            mTxtWidth = mTextPaint.measureText(txt, 0, txt.length());
            canvas.drawText(txt, mXCenter - mTxtWidth / 2, mYCenter + mTxtHeight / 2, mTextPaint);
        }

        if (mProgress > 0) {
            RectF oval = new RectF();
            oval.left = (mXCenter - mRingRadius);
            oval.top = (mYCenter - mRingRadius);
            oval.right = mRingRadius * 2 + (mXCenter - mRingRadius);
            oval.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
            canvas.drawArc(oval, -90, ((float) mProgress / mTotalProgress) * 360, false, mRingPaint); //
            //			canvas.drawCircle(mXCenter, mYCenter, mRadius + mStrokeWidth / 2, mRingPaint);
            //			String txt = mProgress + "%";

        } else {
            if (text == null || text.equals("")) {
                text = "0";
            }
            //			mTxtWidth = mTextPaint.measureText(text, 0, text.length());
            //			canvas.drawText(text, mXCenter - mTxtWidth / 2, mYCenter + mTxtHeight / 2, mTextPaint);
        }
    }

    public void setProgress(final double progress) {
        mProgress = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (progress != 0) {
                        while (mProgress <= progress) {
                            Thread.sleep(20);
                            mProgress += 2;
                            postInvalidate();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private int spToPx(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    public void setText(String text) {
        this.txt = text;
        invalidate();
    }
}
