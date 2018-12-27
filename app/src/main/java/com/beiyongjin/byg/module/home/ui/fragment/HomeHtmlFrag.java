package com.beiyongjin.byg.module.home.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beiyongjin.byg.MainAct;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.ui.BaseFragment;
import com.beiyongjin.byg.databinding.HomeHtmlFragBinding;
import com.beiyongjin.byg.module.home.viewControl.HomeHtmlCtrl;

/**
 * Author: 沈志华
 * E-mail: shenzh@erongdu.com
 * Date: 2017/9/5$ 17:02$
 * Description:HTML首页
 * <p/>
 */
public class HomeHtmlFrag extends BaseFragment{
    public HomeHtmlFragBinding binding;
    public static HomeHtmlFrag newInstance(){
        HomeHtmlFrag homeFrag=new HomeHtmlFrag();
        return homeFrag;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =DataBindingUtil.inflate(inflater, R.layout.home_html_frag,null,false);
        HomeHtmlCtrl viewCtrl=new HomeHtmlCtrl(binding,(MainAct)getActivity());
        binding.setViewCtrl(viewCtrl);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            binding.getRoot().getRootView().setFitsSystemWindows(true);
            binding.getRoot().getRootView().requestApplyInsets();
        }
        return binding.getRoot();
    }

}
