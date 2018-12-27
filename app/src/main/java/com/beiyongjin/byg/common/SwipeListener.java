package com.beiyongjin.byg.common;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

/**
 * Author: Chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/11/7 下午5:46
 * <p/>
 * Description:下拉刷新
 */
public abstract class SwipeListener implements OnRefreshListener, OnLoadMoreListener {
    @Override
    public void onLoadMore() {
        loadMore();
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    public abstract void swipeInit(SwipeToLoadLayout swipeLayout);

    public abstract void refresh();

    public abstract void loadMore();
}
