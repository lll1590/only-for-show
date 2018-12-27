package com.beiyongjin.byg.router.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.beiyongjin.byg.module.user.logic.UserLogic;
import com.beiyongjin.byg.router.RouterExtras;
import com.beiyongjin.byg.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/3/16 11:05
 * <p/>
 * Description: 是否需要登录拦截
 */
@Interceptor(priority = 1, name = "登录拦截器")
public class LoginInterceptor implements IInterceptor {
    /**
     * The operation of this tollgate.
     *
     * @param postcard
     *         meta
     * @param callback
     *         callback
     */
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        // 是否需要登录
        if (RouterExtras.EXTRA_LOGIN == postcard.getExtra()) {
            // 是否已经登录
            if (UserLogic.isLand()) {
                callback.onContinue(postcard);
            } else {
                ARouter.getInstance().build(RouterUrl.UserInfoManage_Login).navigation();
                callback.onInterrupt(null);
            }
        } else {
            callback.onContinue(postcard);
        }
    }

    /**
     * Do your init work in this method, it well be call when processor has been load.
     *
     * @param context
     *         context
     */
    @Override
    public void init(Context context) {
    }
}
