package com.beiyongjin.byg.module.user.viewControl;

import android.view.Gravity;
import android.view.View;

import com.beiyongjin.byg.utils.EventBusUtils;
import com.erongdu.wireless.friday.Friday;
import com.erongdu.wireless.network.entity.HttpResult;
import com.erongdu.wireless.tools.utils.ContextHolder;
import com.erongdu.wireless.tools.utils.RegularUtil;
import com.erongdu.wireless.tools.utils.ToastUtil;
import com.erongdu.wireless.views.appbar.ToolBar;
import com.erongdu.wireless.views.popupWindow.PickPopupWindow;
import com.github.mzule.activityrouter.router.Routers;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.common.FeatureConfig;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.module.user.dataModel.receive.IsExistsRec;
import com.beiyongjin.byg.module.user.dataModel.receive.OauthTokenMo;
import com.beiyongjin.byg.module.user.dataModel.submit.LoginSub;
import com.beiyongjin.byg.module.user.logic.UserLogic;
import com.beiyongjin.byg.module.user.ui.activity.LoginAct;
import com.beiyongjin.byg.module.user.viewModel.LoginVM;
import com.beiyongjin.byg.network.NetworkUtil;
import com.beiyongjin.byg.network.RDClient;
import com.beiyongjin.byg.network.RequestCallBack;
import com.beiyongjin.byg.network.api.UserService;
import com.beiyongjin.byg.utils.FridayConstant;
import com.beiyongjin.byg.utils.InputCheck;
import com.beiyongjin.byg.utils.Util;

import cn.tongdun.android.shell.FMAgent;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/16 16:17
 * <p/>
 * Description: 登录页面控制器({@link LoginAct})
 */
public class LoginCtrl {
    private LoginVM         loginVM;
    private PickPopupWindow popupWindow;
    private String type;

    public LoginCtrl(ToolBar toolBar,String type) {
        this.type=type;
        loginVM = new LoginVM();
        toolBar.setLeftImage(null);
        popupWindow = new PickPopupWindow(toolBar.getRootView().getContext(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                if (view.getId() == R.id.first) {
                    Friday.onEvent(view.getContext(), FridayConstant.LOGIN_MORE_REGISTER);
                    loginVM.setStep(true);
                    loginVM.setPhone("");
                } else if (view.getId() == R.id.second) {
                    Friday.onEvent(view.getContext(), FridayConstant.LOGIN_MORE_SWITCH);
                    loginVM.setStep(true);
                    loginVM.setPhone("");
                }
            }
        }).setFirstText(ContextHolder.getContext().getString(R.string.register_title)).setSecondText(ContextHolder.getContext().getString(R.string
                .login_other));
    }

    /**
     * 登录按钮
     */
    public void submitClick(final View view) {
        EventBusUtils.post(EventBusUtils.LOGIN_EVENT);
        Friday.onEvent(view.getContext(), FridayConstant.LOGIN_SUBMIT);
        if (!InputCheck.checkPwd(loginVM.getPwd())) {
            ToastUtil.toast(ContextHolder.getContext().getString(R.string.settings_pwd_desc));
            return;
        }
        LoginSub sub = new LoginSub(loginVM.getPhone(), loginVM.getPwd());
        if (FeatureConfig.enableFeature(FeatureConfig.enabletongdunModuleFeature)) {
            sub.setBox(FMAgent.onEvent(ContextHolder.getContext()));
        }
        Call<HttpResult<OauthTokenMo>> call = RDClient.getService(UserService.class).doLogin(sub);
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<HttpResult<OauthTokenMo>>() {
            @Override
            public void onSuccess(Call<HttpResult<OauthTokenMo>> call, Response<HttpResult<OauthTokenMo>> response) {
                OauthTokenMo mo = response.body().getData();
                mo.setUsername(loginVM.getPhone());
                UserLogic.login(Util.getActivity(view), mo,type);
                //发送登录的消息

            }
        });
    }

    /**
     * 登录-下一步
     */
    public void nextStep(final View view) {
        Friday.onEvent(view.getContext(), FridayConstant.LOGIN_NEXT);
//        if (!RegularUtil.isPhone(loginVM.getPhone())) {
//            ToastUtil.toast(ContextHolder.getContext().getString(R.string.forgot_phone_hint_step_1_error));
//            return;
//        }
        if (!RegularUtil.isPhoneLength(loginVM.getPhone())) {
            ToastUtil.toast(ContextHolder.getContext().getString(R.string.forgot_phone_hint_step_1_error));
            return;
        }
        Call<HttpResult<IsExistsRec>> phoneCall = RDClient.getService(UserService.class).isPhoneExists(loginVM.getPhone());
        NetworkUtil.showCutscenes(phoneCall);
        phoneCall.enqueue(new RequestCallBack<HttpResult<IsExistsRec>>() {
            @Override
            public void onSuccess(Call<HttpResult<IsExistsRec>> call, Response<HttpResult<IsExistsRec>> response) {
                if (Constant.STATUS_10.equals(response.body().getData().getIsExists())) {
                    Routers.open(view.getContext(), RouterUrl.getRouterUrl(String.format(RouterUrl.UserInfoManage_Register, loginVM.getPhone())));
                    //Routers.open(Util.getActivity(view), RouterUrl.getRouterUrl(RouterUrl.UserInfoManage_Register));
                } else {
                    loginVM.setStep(false);
                }
            }
        });
    }

    /**
     * 更多按钮
     */
    public void more(View view) {
        Friday.onEvent(view.getContext(), FridayConstant.LOGIN_MORE);
        popupWindow.showAtLocation(view.getRootView(), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 忘记密码按钮
     */
    public void forgotClick(View view) {
        Friday.onEvent(view.getContext(), FridayConstant.LOGIN_FORGOT);
        Routers.open(view.getContext(), RouterUrl.getRouterUrl(String.format(RouterUrl.UserInfoManage_ForgotPwd, loginVM.getPhone(), Constant.STATUS_2)));
    }

    public LoginVM getLoginVM() {
        return loginVM;
    }
}
