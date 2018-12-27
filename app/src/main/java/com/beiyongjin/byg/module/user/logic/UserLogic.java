package com.beiyongjin.byg.module.user.logic;

import android.app.Activity;
import android.text.TextUtils;

import com.beiyongjin.byg.utils.EventBusUtils;
import com.erongdu.wireless.info.SharedInfo;
import com.erongdu.wireless.logic.GestureLogic;
import com.github.mzule.activityrouter.router.Routers;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.common.DialogUtils;
import com.beiyongjin.byg.common.FeatureConfig;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.module.user.dataModel.receive.OauthTokenMo;
import com.ypy.eventbus.EventBus;

import cn.pedant.SweetAlert.OnSweetClickListener;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/16 17:55
 * <p/>
 * Description:
 */
public class UserLogic {
    /**
     * 用户登录
     */
    public static void login(Activity activity, OauthTokenMo oauthTokenMo,String type) {
        /** 登录逻辑处理 */
        SharedInfo.getInstance().saveValue(Constant.IS_LAND, true);
        SharedInfo.getInstance().saveEntity(oauthTokenMo);
        if(Constant.STATUS_1.equals(type)){
            Routers.open(activity, RouterUrl.getRouterUrl(String.format(RouterUrl.AppCommon_Main, Constant.NUMBER_1)));
        }else{
            if(FeatureConfig.enableFeature(FeatureConfig.enablewebHomeFeature)){
                if(Constant.STATUS_4.equals(type)){
                    Routers.open(activity, RouterUrl.getRouterUrl(String.format(RouterUrl.AppCommon_Main, Constant.NUMBER_4)));
                }else{
                    Routers.open(activity, RouterUrl.getRouterUrl(String.format(RouterUrl.AppCommon_Main, Constant.NUMBER_0)));
                }
            }else{
                Routers.open(activity, RouterUrl.getRouterUrl(String.format(RouterUrl.AppCommon_Main, Constant.NUMBER_0)));
            }
        }
        activity.setResult(Activity.RESULT_OK);
        activity.finish();
    }

    /**
     * 登出必须执行的操作
     */
    public static void signOut() {
        OauthTokenMo tokenMo = SharedInfo.getInstance().getEntity(OauthTokenMo.class);
        if (null != tokenMo) {
            // 删除保存的手势密码信息
            //GestureLogic.getInstance().removeEntity(tokenMo.getUserId());
        }
        // 标记未启用手势密码
        SharedInfo.getInstance().remove(Constant.IS_GESTURE_OPENED);
        // 标记未登录
        SharedInfo.getInstance().remove(Constant.IS_LAND);
        // 删除保存的OauthToken信息
        SharedInfo.getInstance().remove(OauthTokenMo.class);
        // 清楚缓存的手势密码信息
        GestureLogic.getInstance().clean();

    }

    /**
     * 用户被动登出
     */
    public static void signOutForcibly(Activity activity) {
        signOut();
        Routers.open(activity, RouterUrl.getRouterUrl(String.format(RouterUrl.AppCommon_Main, Constant.NUMBER_0)));

    }

    /**
     * 用户主动登出(到主界面)
     */
    public static void signOutToMain(final Activity activity) {
        DialogUtils.showDialog(activity, R.string.user_login_out, new OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog dialog) {
                dialog.dismiss();
                signOutForcibly(activity);
            }
        });
    }

    /**
     * 用户主动登出(到登录界面)
     */
    public static void signOutToLogin(final Activity activity) {
        DialogUtils.showDialog(activity, R.string.user_login_out, new OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog dialog) {
                signOut();
                Routers.openForResult(activity, RouterUrl.getRouterUrl(String.format(RouterUrl.UserInfoManage_Login, Constant.STATUS_3)), 0);
                activity.finish();
            }
        });
    }

    /**
     * 是否已经登录
     */
    public static boolean isLand() {
        boolean      isLand   = (boolean) SharedInfo.getInstance().getValue(Constant.IS_LAND, false);
        OauthTokenMo tokenRec = SharedInfo.getInstance().getEntity(OauthTokenMo.class);
        // 是否已经登录
        return isLand && null != tokenRec && !TextUtils.isEmpty(tokenRec.getUserId());
    }
}
