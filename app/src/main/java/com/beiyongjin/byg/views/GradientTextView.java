package com.beiyongjin.byg.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by Administrator on 2014/9/9.
 */
public class GradientTextView extends TextView {

    public GradientTextView(Context context) {
        super(context);
    }

    public GradientTextView(Context context,
                            AttributeSet attrs) {
        super(context, attrs);
    }

    public GradientTextView(Context context,
                            AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed,
                            int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            getPaint().setShader(new LinearGradient(
                    0, 0, getWidth(), getHeight(),
                    Color.parseColor("#f5326f"),  Color.parseColor("#ff7944"),
                    Shader.TileMode.MIRROR));
        }
    }
}