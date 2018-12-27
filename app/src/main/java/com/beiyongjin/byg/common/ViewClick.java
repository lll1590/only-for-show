package com.beiyongjin.byg.common;

import android.view.View;

/**
 * Author: Chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/11/11 下午2:10
 * <p/>
 * Description:adapter Click事件控件接收类
 */
public abstract class ViewClick implements View.OnClickListener{
    /** adapter中的position */
    private int position;
    private Object object;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
