package com.beiyongjin.byg.module.home.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.ui.BaseFragment;
import com.beiyongjin.byg.databinding.HomeTwoFragBinding;
import com.beiyongjin.byg.module.home.viewControl.HomeTwoCtrl;

/**
 * Created by chenming
 * Created Date 17/4/6 14:05
 * mail:cm1@erongdu.com
 * Describe: 第二个首页fragment
 */
public class HomeTwoFrag extends BaseFragment {
    public HomeTwoCtrl homeCtrl;
    private boolean first = true;

    public static HomeTwoFrag newInstance() {
        HomeTwoFrag homeFrag = new HomeTwoFrag();
        return homeFrag;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        HomeTwoFragBinding binding = DataBindingUtil.inflate(inflater, R.layout.home_two_frag, null, false);
        DisplayMetrics     metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        homeCtrl = new HomeTwoCtrl(binding, metrics.widthPixels);
        System.out.println("metrics.widthPixels" + metrics.widthPixels);
        //StatusBarCompat.compat(getActivity(), getResources().getColor(R.color.app_color_principal));
        binding.setViewCtrl(homeCtrl);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            binding.getRoot().getRootView().setFitsSystemWindows(true);
            binding.getRoot().getRootView().requestApplyInsets();
        }
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        homeCtrl.reqHomeData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (homeCtrl.LOAN == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                homeCtrl.reqHomeData();
            }
        } else {
            homeCtrl.reqHomeData();
        }
    }

}
