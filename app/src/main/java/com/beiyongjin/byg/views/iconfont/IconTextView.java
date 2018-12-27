package com.beiyongjin.byg.views.iconfont;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.beiyongjin.byg.MyApplication;
import com.beiyongjin.byg.R;

/**
 * Created by chenming
 * Created Date 17/4/19 22:29
 * mail:cm1@erongdu.com
 * Describe:
 */
public class IconTextView extends TextView {

    public IconTextView(Context context) {
        this(context, null);
    }

    public IconTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //设置字体图标
    private void init() {
        this.setTypeface(MyApplication.getInstance().getIconTypeFace());
    }
}
