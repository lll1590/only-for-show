package com.beiyongjin.byg.module.home.viewModel;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.erongdu.wireless.tools.utils.ActivityManage;
import com.erongdu.wireless.tools.utils.ContextHolder;
import com.erongdu.wireless.tools.utils.SPUtil;
import com.github.mzule.activityrouter.router.Routers;
import com.beiyongjin.byg.common.BaseParams;
import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.common.RouterUrl;

import java.util.ArrayList;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/30 16:20
 * <p/>
 * Description: ViewPager适配器，用来绑定数据和view
 */
public class ViewPagerAdapter extends PagerAdapter {
    // 界面列表
    private ArrayList<View> views;

    public ViewPagerAdapter(ArrayList<View> views) {
        this.views = views;
    }

    /**
     * 获得当前界面数
     */
    @Override
    public int getCount() {
        return views == null ? 0 : views.size();
    }

    /**
     * 判断是否由对象生成界面
     */
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

    /**
     * 销毁position位置的界面
     */
    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }

    /**
     * 初始化position位置的界面
     */
    @Override
    public Object instantiateItem(View container, final int position) {
        // 停留在最后一幅页面3秒后跳转
        //logic(container, position, 3000);

        // 点击最后一幅页面1秒后跳转
        views.get(position).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logic(view, position, 1000);
            }
        });
        ((ViewPager) container).addView(views.get(position), 0);
        return views.get(position);
    }

    /**
     * 页面跳转
     */
    private void logic(View view, final int position, final long time) {
        if (position == views.size() - 1) {
            final Activity activity = ActivityManage.peek();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 退出动画
                        SPUtil.saveValue(SPUtil.getSp(ContextHolder.getContext(), BaseParams.SP_NAME), Constant.IS_FIRST_IN, false);
                        Thread.sleep(time);
                        Routers.open(activity, RouterUrl.getRouterUrl(String.format(RouterUrl.AppCommon_Main, Constant.NUMBER_6)));
                        activity.finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        // 退出动画
                        SPUtil.saveValue(SPUtil.getSp(ContextHolder.getContext(), BaseParams.SP_NAME), Constant.IS_FIRST_IN, false);
                        Thread.sleep(time);
                        Routers.open(activity, RouterUrl.getRouterUrl(String.format(RouterUrl.AppCommon_Main, Constant.NUMBER_6)));
                        activity.finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });*/
        }
    }
}
