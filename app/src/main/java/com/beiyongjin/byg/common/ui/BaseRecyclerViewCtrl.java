package com.beiyongjin.byg.common.ui;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.erongdu.wireless.network.entity.PageMo;
import com.erongdu.wireless.views.PlaceholderLayout;
import com.beiyongjin.byg.common.SwipeListener;
import com.beiyongjin.byg.common.ViewClick;

/**
 * Author: Chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/11/7 下午4:04
 * <p/>
 * Description:公共列表控制器
 */
public abstract class BaseRecyclerViewCtrl {
    /** 页面布局显示 */
    public ObservableField<BaseRecyclerViewVM> viewModel = new ObservableField<>();
    /** 页面adapter显示 */
    public ObservableField<BaseQuickAdapter>   adapter   = new ObservableField<>();
    /** adapter中控件点击监听 */
    public ViewClick click;
    //******************公共控件*************************
    /** 下拉刷新事件 */
    public ObservableField<SwipeListener> listener         = new ObservableField<>();
    /** 分页类 */
    public PageMo                         pageMo           = new PageMo();
    /** 备注Tips是否显示控制 */
    public ObservableField<Boolean>       tipsVisibility   = new ObservableField<>(false);
    /** 占位图状态 */
    public ObservableInt                  placeholderState = new ObservableInt(PlaceholderLayout.LOADING);
    /** 占位图 - reload事件 */
    public  PlaceholderLayout.OnReloadListener placeholderListener;
    /** 下拉刷新控件 */
    private SwipeToLoadLayout swipeLayout;

    public SwipeToLoadLayout getSwipeLayout() {
        return swipeLayout;
    }

    public void setSwipeLayout(SwipeToLoadLayout swipeToLoadLayout) {
        this.swipeLayout = swipeToLoadLayout;
    }


}
