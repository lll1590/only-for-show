package com.beiyongjin.byg.module.user.viewControl;

import android.app.Activity;
import android.view.View;

import com.erongdu.wireless.info.SharedInfo;
import com.erongdu.wireless.network.entity.HttpResult;
import com.erongdu.wireless.tools.utils.ToastUtil;
import com.github.mzule.activityrouter.router.Routers;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.module.user.dataModel.receive.OauthTokenMo;
import com.beiyongjin.byg.module.user.dataModel.submit.ResetPwdSub;
import com.beiyongjin.byg.module.user.viewModel.ResetPwdVM;
import com.beiyongjin.byg.network.RDClient;
import com.beiyongjin.byg.network.RequestCallBack;
import com.beiyongjin.byg.network.api.UserService;
import com.beiyongjin.byg.utils.Util;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/17 18:50
 * <p/>
 * Description: 重置密码页面控制器({@link com.beiyongjin.byg.module.user.ui.activity.ResetPwdAct})
 */
public class ResetPwdCtrl {

    private ResetPwdVM  resetPwdVM;
    /** 重置密码需要提交的数据 */
    private ResetPwdSub sub;

    public ResetPwdCtrl() {
        resetPwdVM = new ResetPwdVM();
        sub = new ResetPwdSub();
    }

    /**
     * 完成点击
     */
    public void submitClick(final View view) {
        OauthTokenMo tokenMo = SharedInfo.getInstance().getEntity(OauthTokenMo.class);
        sub.setToken(tokenMo.getToken());
        sub.setAccountId(tokenMo.getUserId());
        sub.setOldPwd(resetPwdVM.getPwdNew());
        sub.setNewPwd(resetPwdVM.getPwdConfirm());
        sub.setVerifyPwd(resetPwdVM.getPwdConfirm());
        Call<HttpResult> call = RDClient.getService(UserService.class).resetPwd(sub);
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                ToastUtil.toast(response.body().getMsg());
                Activity activity = Util.getActivity(view);
                Routers.open(activity, RouterUrl.getRouterUrl(RouterUrl.UserInfoManage_Login));
                activity.setResult(Activity.RESULT_OK);
                activity.finish();
            }
        });
    }

    public ResetPwdVM getResetPwdVM() {
        return resetPwdVM;
    }
}
