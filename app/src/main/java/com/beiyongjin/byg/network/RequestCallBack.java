package com.beiyongjin.byg.network;

import android.databinding.ObservableInt;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.erongdu.wireless.network.exception.ApiException;
import com.erongdu.wireless.tools.utils.ToastUtil;
import com.erongdu.wireless.views.PlaceholderLayout;
import com.beiyongjin.byg.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author: hebihe
 * E-mail: hbh@erongdu.com
 * Date: 2016/4/22 19:31
 * <p/>
 * Description: 网络请求回调封装类
 */
public abstract class RequestCallBack<T> implements Callback<T> {
    public abstract void onSuccess(Call<T> call, Response<T> response);

    private SwipeToLoadLayout swipeLayout;
    private ObservableInt     placeholderState;

    public RequestCallBack() {
    }

    public RequestCallBack(SwipeToLoadLayout swipeLayout) {
        this.swipeLayout = swipeLayout;
    }

    public RequestCallBack(SwipeToLoadLayout swipeLayout, ObservableInt placeholderState) {
        this.swipeLayout = swipeLayout;
        this.placeholderState = placeholderState;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        NetworkUtil.dismissCutscenes();
        if (swipeLayout != null && swipeLayout.isRefreshing()) {
            swipeLayout.setRefreshing(false);
        }
        if (swipeLayout != null && swipeLayout.isLoadingMore()) {
            swipeLayout.setLoadingMore(false);
        }
        if (response.isSuccessful() && response.body() != null) {
            if (null != placeholderState) {
                placeholderState.set(PlaceholderLayout.SUCCESS);
            }
            onSuccess(call, response);
        } else {
            onFailed(call, response);

        }
    }

    public void onFailed(Call<T> call, Response<T> response) {
        if (response.code() >= 400) {
            ToastUtil.toast(R.string.app_network_error);
            if (null != placeholderState) {
                placeholderState.set(PlaceholderLayout.NO_NETWORK);
            }
        } else {
            if (null != placeholderState) {
                placeholderState.set(PlaceholderLayout.ERROR);
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        NetworkUtil.dismissCutscenes();
        if (null != placeholderState) {
            placeholderState.set(PlaceholderLayout.ERROR);
        }
        if (swipeLayout != null && swipeLayout.isRefreshing()) {
            swipeLayout.setRefreshing(false);
        }
        if (swipeLayout != null && swipeLayout.isLoadingMore()) {
            swipeLayout.setLoadingMore(false);
        }
        if (t instanceof ApiException) {
            ExceptionHandling.operate(((ApiException) t).getResult());
        }

        if (t instanceof IOException) {
            ToastUtil.toast(R.string.app_network_socket_timeout);
        }
        t.printStackTrace();
    }
}
