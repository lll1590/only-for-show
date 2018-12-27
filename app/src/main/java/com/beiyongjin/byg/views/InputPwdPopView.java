package com.beiyongjin.byg.views;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.beiyongjin.byg.R;

/**
 * Author: JinFu
 * E-mail: jf@erongdu.com
 * Date: 2017/2/14 18:12
 * <p>
 * Description:
 */
public class InputPwdPopView extends PopupWindow {
    public InputPwdPopView(Context context, View.OnClickListener itemsOnClick, View.OnClickListener cancelClick, TextWatcher watcher) {
        super(context);
        LayoutInflater     inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View         mMenuView = inflater.inflate(R.layout.input_pwd_pop, null);
        final LinearLayout layout    = (LinearLayout) mMenuView.findViewById(R.id.layout);
        mMenuView.findViewById(R.id.icon_close).setOnClickListener(cancelClick);
        EditText edit = (EditText) mMenuView.findViewById(R.id.pwd);
        edit.addTextChangedListener(watcher);
        mMenuView.findViewById(R.id.forget).setOnClickListener(itemsOnClick);
        // 设置PickPopupWindow的View
        this.setContentView(mMenuView);
        // 设置PickPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置PickPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置PickPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置PickPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
        // 设置PickPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //         mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = layout.getTop();
                int y      = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
}
