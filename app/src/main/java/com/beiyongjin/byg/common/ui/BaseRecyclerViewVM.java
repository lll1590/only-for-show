package com.beiyongjin.byg.common.ui;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.erongdu.wireless.tools.utils.ContextHolder;
import com.erongdu.wireless.views.recyclerView.DividerLine;
import com.beiyongjin.byg.R;

import me.tatarka.bindingcollectionadapter.ItemView;
import me.tatarka.bindingcollectionadapter.ItemViewSelector;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/3 10:45
 * <p/>
 * Description: 抽象的共用 RecyclerView
 */
public abstract class BaseRecyclerViewVM<T> extends BaseObservable {
    /** 设置布局 */
    protected abstract void selectView(ItemView itemView, int position, T item);

    /** 绘制区域是否在padding里面,true-不在，false-在 */
    public       boolean             clipToPadding = true;
    /**
     * 分割线类型
     * -1 - 没有分割线
     * 0 - 水平分割线(左缩进)
     * 1 - 垂直分割线
     * 2 - 水平分割线(不缩进)
     */
    public       int                 type          = DividerLine.HORIZONTAL;
    /** 数据源 */
    public final ObservableList<T>   items         = new ObservableArrayList<>();
    public final ItemViewSelector<T> itemView      = new ItemViewSelector<T>() {
        @Override
        public void select(ItemView itemView, int position, T item) {
            selectView(itemView, position, item);
        }

        @Override
        public int viewTypeCount() {
            return 0;
        }
    };
    /** 设置itemView点击事件 */
    private ItemView.OnItemClickListener onItemClickListener;

    protected ItemView.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(ItemView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public float getPaddingTop() {
        if (clipToPadding) {
            return 0;
        } else {
            return ContextHolder.getContext().getResources().getDimension(R.dimen.x0);
        }
    }
}
