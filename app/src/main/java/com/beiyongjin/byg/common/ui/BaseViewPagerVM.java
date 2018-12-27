package com.beiyongjin.byg.common.ui;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.beiyongjin.byg.BR;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/2 17:33
 * <p/>
 * Description: ViewPagerVM基类
 */
public class BaseViewPagerVM extends BaseObservable {
    public final ObservableList<Fragment> items = new ObservableArrayList<>();
    private FragmentManager manager;
    /** title数组 */
    private String[]        mPageTitles;

    public BaseViewPagerVM(String[] pageTitles, FragmentManager manager) {
        mPageTitles = pageTitles;
        this.manager = manager;
        notifyPropertyChanged(BR.manager);
    }

    /**
     * 为TabLayout设置title
     */
    public final BaseViewPagerFAdapter.PageTitles pageTitles = new BaseViewPagerFAdapter.PageTitles() {
        @Override
        public CharSequence getPageTitle(int position) {
            return mPageTitles[position];
        }
    };

    @Bindable
    public FragmentManager getManager() {
        return manager;
    }
}
