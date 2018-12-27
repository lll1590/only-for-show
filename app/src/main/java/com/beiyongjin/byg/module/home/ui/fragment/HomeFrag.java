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
import com.beiyongjin.byg.databinding.HomeFragBinding;
import com.beiyongjin.byg.module.home.viewControl.HomeCtrl;

/**
 * Author: JinFu
 * E-mail: jf@erongdu.com
 * Date: 2017/2/14 10:16
 * <p>
 * Description:
 */
public class HomeFrag extends BaseFragment {
    public HomeCtrl homeCtrl;
    private boolean first = true;

    public static HomeFrag newInstance() {
        HomeFrag homeFrag = new HomeFrag();
        return homeFrag;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        HomeFragBinding binding = DataBindingUtil.inflate(inflater, R.layout.home_frag, null, false);
        DisplayMetrics  metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        homeCtrl = new HomeCtrl(binding, metrics.widthPixels);
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
