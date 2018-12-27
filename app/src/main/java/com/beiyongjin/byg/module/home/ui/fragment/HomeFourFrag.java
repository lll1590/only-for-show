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
import com.beiyongjin.byg.databinding.HomeFourFragBinding;
import com.beiyongjin.byg.module.home.viewControl.HomeFourCtrl;


/**
 * Author: 沈志华
 * E-mail: shenzh@erongdu.com
 * Date: 2017/8/9$ 11:29$
 * <p/>
 * {@link com.beiyongjin.byg.MainAct}
 */
public class HomeFourFrag extends BaseFragment{
    public HomeFourCtrl homeCtrl;
    private boolean first = true;
    private HomeFourFragBinding binding;

    public static HomeFourFrag newInstance(){
        HomeFourFrag homeFrag=new HomeFourFrag();
        return homeFrag;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_four_frag, null, false);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        homeCtrl = new HomeFourCtrl(binding,metrics.widthPixels);
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
