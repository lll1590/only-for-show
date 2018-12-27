package com.beiyongjin.byg.module.user.viewControl;

import android.view.View;

import com.erongdu.wireless.views.appbar.ToolBar;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/17 17:35
 * <p/>
 * Description: 注册成功页面控制器({@link RegisterSucceedAct})
 */
public class RegisterSucceedCtrl {
    public RegisterSucceedCtrl(ToolBar toolBar) {
        toolBar.setLeftListener(null);
    }

    /** 进入 我的 */
    public void mineClick(View view) {
        //Routers.open(view.getContext(), RouterUrl.getRouterUrl(String.format(RouterUrl.Mine_Account, BundleKeys.HOME)));
    }
}
