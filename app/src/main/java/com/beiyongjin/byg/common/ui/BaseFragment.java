package com.beiyongjin.byg.common.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beiyongjin.byg.utils.EventBusUtils;
import com.erongdu.wireless.tools.utils.PermissionCheck;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/25 15:25
 * <p/>
 * Description: Fragment基类
 */
public class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(isNeedBusEvent()){
            EventBusUtils.register(this);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public boolean isNeedBusEvent() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(isNeedBusEvent()){
            EventBusUtils.unregister(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCheck.getInstance().onRequestPermissionsResult(getActivity(), requestCode, permissions, grantResults);
    }
}
