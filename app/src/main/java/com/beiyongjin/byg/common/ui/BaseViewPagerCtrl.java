package com.beiyongjin.byg.common.ui;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.beiyongjin.byg.databinding.CommonViewPagerBinding;

/**
 * Author: Chenming
 * E-mail: cm1@erongdu.com
 * Date: 2016/3/2 17:33
 * <p/>
 * Description: ViewPagerCtrl基类
 */
public abstract class BaseViewPagerCtrl extends BaseObservable {
    /** 基础业务控制器 */
    public BaseViewPagerVM viewPagerVM;
    /** 备注Tips是否显示控制 */
    public ObservableField<Boolean> tipsVisibility = new ObservableField<>(false);

    public BaseViewPagerCtrl(String[] pageTitles, FragmentManager manager) {
        viewPagerVM = new BaseViewPagerVM(pageTitles, manager);
    }

    /**
     * 将页面的viewPager和TabLayout绑定
     *
     * @param binding
     *         公共页面布局
     */
    protected void bindPager(CommonViewPagerBinding binding) {
        BaseViewPagerFAdapter.setAdapter(binding.pager, viewPagerVM.items, viewPagerVM.getManager(), viewPagerVM.pageTitles);
        binding.tabs.setupWithViewPager(binding.pager);
        binding.pager.setOffscreenPageLimit(viewPagerVM.items.size());
        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                viewPagerVM.items.get(position).onHiddenChanged(false);
                onViewPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 页面选中时需要变更
     */
    public abstract void onViewPageSelected(int position);
}
