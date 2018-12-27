package com.beiyongjin.byg.common;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author: Chenming
 * E-mail: cm1@erongdu.com
 * Date: 2016/11/22 下午1:51
 * <p/>
 * Description: 将baseQuickAdapter中getItemPosition方法改为public
 */
public abstract class QuickAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    public QuickAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

    public QuickAdapter(List<T> data) {
        super(data);
    }

    /** 获取当前数据下标 */
    public int getItemPosition(T item) {
        return item != null && this.mData != null && !this.mData.isEmpty()?this.mData.indexOf(item):-1;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    /** 设置itemView点击事件 */
    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
