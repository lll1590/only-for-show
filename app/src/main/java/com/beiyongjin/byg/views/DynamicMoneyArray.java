package com.beiyongjin.byg.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erongdu.wireless.tools.utils.DensityUtil;
import com.erongdu.wireless.tools.utils.TextUtil;
import com.beiyongjin.byg.R;

/**
 * Created by chenming
 * Created Date 17/6/12 19:33
 * mail:cm1@erongdu.com
 * Describe: 动态进入数组
 */
public class DynamicMoneyArray extends LinearLayout {
    /** 标题颜色 */
    private int            titleColor;
    /** 标题字体大小 */
    private float          titleSize;
    /** 数组字体颜色 */
    private int            arrColor;
    /** 数组字体大小 */
    private float          arrSize;
    /** 展示文案提示 */
    private String         tips;
    private RelativeLayout arrLayout;
    private TextView       titleTv;
    private int            arrLayoutWidth;

    public DynamicMoneyArray(Context context) {
        this(context, null);
    }

    public DynamicMoneyArray(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicMoneyArray(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        //设置水平布局
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.DynamicMoneyArray, 0, 0);
        tips = typeArray.getString(R.styleable.DynamicMoneyArray_tips);
        titleColor = typeArray.getColor(R.styleable.DynamicMoneyArray_tipColor, 0xFFFFFF);
        titleSize = typeArray.getDimension(R.styleable.DynamicMoneyArray_tipSize, DensityUtil.sp2px(getContext(), 16));
        arrColor = typeArray.getColor(R.styleable.DynamicMoneyArray_arrColor, 0xFFFFFF);
        arrSize = typeArray.getDimension(R.styleable.DynamicMoneyArray_arrSize, DensityUtil.sp2px(getContext(), 20));

        titleTv = new TextView(context);
        titleTv.setTextColor(titleColor);
        titleTv.setTextSize(DensityUtil.px2sp(getContext(), titleSize));
        if (TextUtil.isEmpty(tips)) {
            titleTv.setText(tips);
        } else {
            titleTv.setText("今日待抢额度：");
        }
        LayoutParams tvLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams
                .WRAP_CONTENT);
        titleTv.setLayoutParams(tvLp);
        addView(titleTv, 0);

        arrLayout = new RelativeLayout(context);
        titleTv.measure(0, 0);
        arrLayoutWidth = metrics.widthPixels - titleTv.getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(arrLayoutWidth, RelativeLayout.LayoutParams.MATCH_PARENT);
        arrLayout.setLayoutParams(lp);
        arrLayout.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
        addView(arrLayout, 1);
        typeArray.recycle();
    }

    /** 设置数组 */
    public void setArrayValue(String value) {
        if(TextUtil.isEmpty(value))
            return;
        arrLayout.removeAllViews();
        LinearLayout ll = new LinearLayout(getContext());
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(lp);
        ll.setOrientation(HORIZONTAL);
        ll.setGravity(Gravity.CENTER_VERTICAL);
        String[] v          = value.split("");
        TextView tv;
        float    widthAvarg = arrLayoutWidth / v.length;
        for (int i = 0; i < v.length; i++) {
            tv = new TextView(getContext());
            tv.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            LayoutParams tvLp = new LayoutParams(((int) widthAvarg - 10), LayoutParams.WRAP_CONTENT);
            tvLp.setMargins(5, 0, 5, 0);
            tv.setLayoutParams(tvLp);
            tv.setGravity(Gravity.CENTER);
            if (i == 0)
                tv.setText("￥");
            else
                tv.setText(v[i]);
            tv.setTextSize(DensityUtil.px2sp(getContext(), arrSize));
            tv.setTextColor(arrColor);
            ll.addView(tv);
        }
        arrLayout.addView(ll);
    }

    @BindingAdapter({"arrayValue"})
    public static void arrayValue(View view, String value) {
        ((DynamicMoneyArray) view).setArrayValue(value);
    }
}
