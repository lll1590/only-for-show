package com.beiyongjin.byg.network;

import android.util.Log;

import com.erongdu.wireless.info.SharedInfo;
import com.erongdu.wireless.network.entity.HttpResult;
import com.erongdu.wireless.tools.utils.ActivityManage;
import com.erongdu.wireless.tools.utils.ToastUtil;
import com.github.mzule.activityrouter.router.Routers;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.common.DialogUtils;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.module.user.dataModel.receive.OauthTokenMo;
import com.beiyongjin.byg.module.user.logic.UserLogic;
import com.beiyongjin.byg.network.api.UserService;

import cn.pedant.SweetAlert.OnSweetClickListener;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/5/30 11:53
 * <p/>
 * Description: 异常处理
 */
@SuppressWarnings("unchecked")
final class ExceptionHandling {
    static void operate(final HttpResult result) {
        switch (result.getCode()) {
            case AppResultCode.TOKEN_TIMEOUT:
                OauthTokenMo tokenMo = SharedInfo.getInstance().getEntity(OauthTokenMo.class);
                if (null != tokenMo) {
                    Call<HttpResult<OauthTokenMo>> call = RDClient.getService(UserService.class).refreshToken(tokenMo.getRefreshToken());
                    call.enqueue(new RequestCallBack<HttpResult<OauthTokenMo>>() {
                        @Override
                        public void onSuccess(Call<HttpResult<OauthTokenMo>> call, Response<HttpResult<OauthTokenMo>> response) {
                            SharedInfo.getInstance().saveEntity(response.body().getData());
                        }
                    });
                } else {
                    UserLogic.signOut();
                    Routers.openForResult(ActivityManage.peek(), RouterUrl.getRouterUrl(String.format(RouterUrl.UserInfoManage_Login, Constant.STATUS_3)), 0);
                }
                break;

            case AppResultCode.TOKEN_REFRESH_TIMEOUT:
                UserLogic.signOut();
                Routers.openForResult(ActivityManage.peek(), RouterUrl.getRouterUrl(String.format(RouterUrl.UserInfoManage_Login, Constant.STATUS_3)), 0);
                break;
            case AppResultCode.TOKEN_NOT_UNIQUE:
            case AppResultCode.TOKEN_NOT_EXIT:
                DialogUtils.showDialog(ActivityManage.peek(), R.string.user_login_reset, R.string.user_login_two, new OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        UserLogic.signOut();
                        Routers.openForResult(ActivityManage.peek(), RouterUrl.getRouterUrl(String.format(RouterUrl.UserInfoManage_Login, Constant.STATUS_3))
                                , 0);
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }, new OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        UserLogic.signOut();
                        Routers.openForResult(ActivityManage.peek(), RouterUrl.getRouterUrl(String.format(RouterUrl.UserInfoManage_Login, Constant.STATUS_3))
                                , 0);
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });
                break;

            default:
                break;
        }
        if (result.getCode() != 410 && result.getCode() != 413 && result.getCode() != 412 && result.getCode() != 411&& result.getCode() != 400) {
            Log.i("hjc",result.getCode()+"...."+result.getCode());
            ToastUtil.toast(result.getMsg());
        }
    }
}
