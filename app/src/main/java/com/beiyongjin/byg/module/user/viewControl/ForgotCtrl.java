package com.beiyongjin.byg.module.user.viewControl;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.erongdu.wireless.network.entity.HttpResult;
import com.erongdu.wireless.tools.encryption.MDUtil;
import com.erongdu.wireless.tools.utils.ContextHolder;
import com.erongdu.wireless.tools.utils.RegularUtil;
import com.erongdu.wireless.tools.utils.ToastUtil;
import com.erongdu.wireless.views.TimeButton;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.AppConfig;
import com.beiyongjin.byg.common.CommonType;
import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.common.DialogUtils;
import com.beiyongjin.byg.common.RequestResultCode;
import com.beiyongjin.byg.module.user.dataModel.receive.ProbeSmsRec;
import com.beiyongjin.byg.module.user.dataModel.submit.ForgotSub;
import com.beiyongjin.byg.module.user.dataModel.submit.ValidateCodeSub;
import com.beiyongjin.byg.module.user.ui.activity.ForgotAct;
import com.beiyongjin.byg.module.user.viewModel.ForgotVM;
import com.beiyongjin.byg.network.NetworkUtil;
import com.beiyongjin.byg.network.RDClient;
import com.beiyongjin.byg.network.RequestCallBack;
import com.beiyongjin.byg.network.api.UserService;
import com.beiyongjin.byg.utils.InputCheck;
import com.beiyongjin.byg.utils.Util;

import cn.pedant.SweetAlert.OnSweetClickListener;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.pedant.SweetAlert.SweetAlertType;
import retrofit2.Call;
import retrofit2.Response;

import static com.erongdu.wireless.tools.encryption.MDUtil.encode;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/17 17:25
 * <p/>
 * Description:忘记密码页面控制器({@link ForgotAct})
 */
public class ForgotCtrl {
    public ForgotVM forgotVM;
    public boolean isExit = true;
    public TimeButton timeButton;
    private String phone;

    public ForgotCtrl(final TimeButton timeButton, String phone) {
        forgotVM = new ForgotVM();
        this.phone = phone;

//        forgotVM.setPhone(phone);
        forgotVM.setPhone(Util.phoneBlur(phone));
        forgotVM.setTitle(ContextHolder.getContext().getString(R.string.forgot_pwd_title_step_1));
        this.timeButton = timeButton;
        //        Call<HttpResult<ProbeSmsRec>> phoneCall = RDClient.getService(UserService.class).probeSms(forgotVM.getPhone(), CommonType.FORGOT_CODE);
        //        NetworkUtil.showCutscenes(phoneCall);
        //        phoneCall.enqueue(new RequestCallBack<HttpResult<ProbeSmsRec>>() {
        //            @Override
        //            public void onSuccess(Call<HttpResult<ProbeSmsRec>> call, Response<HttpResult<ProbeSmsRec>> response) {
        //                ProbeSmsRec rec = response.body().getData();
        //                if (!Constant.STATUS_10.equals(rec.getState())) {
        //                    timeButton.setLength(ConverterUtil.getLong(rec.getCountDown()) * 1000);
        //                    timeButton.start();
        //                }
        //            }
        //        });
    }

    /**
     * 获取验证码
     */
    public void getCodeClick(View view) {


        if (TextUtils.isEmpty(phone)) {

            ToastUtil.toast(ContextHolder.getContext().getString(R.string.input) + ContextHolder.getContext().getString(R.string.forgot_phone_hint_step_1));
            return;
        }
//        if (!RegularUtil.isPhone(forgotVM.getPhone())) {
//            ToastUtil.toast(ContextHolder.getContext().getString(R.string.forgot_phone_hint_step_1_error));
//            return;
//        }
        if (!RegularUtil.isPhoneLength(phone)) {

            ToastUtil.toast(ContextHolder.getContext().getString(R.string.forgot_phone_hint_step_1_error));
            return;
        }

        String                        sign     = encode(MDUtil.TYPE.MD5, AppConfig.APP_KEY + phone + CommonType.FORGOT_CODE);
        Call<HttpResult<ProbeSmsRec>> callCode = RDClient.getService(UserService.class).getCode(phone, CommonType.FORGOT_CODE, sign);

        NetworkUtil.showCutscenes(callCode);
        callCode.enqueue(new RequestCallBack<HttpResult<ProbeSmsRec>>() {
            @Override
            public void onSuccess(Call<HttpResult<ProbeSmsRec>> call, Response<HttpResult<ProbeSmsRec>> response) {
                if (!Constant.STATUS_10.equals(response.body().getData().getState())) {
                    ToastUtil.toast(response.body().getData().getMessage());
                } else {
                    timeButton.runTimer();
                    ToastUtil.toast(response.body().getMsg());
                }
            }
        });
    }

    /**
     * 下一步点击
     */
    public void nextClick(final View view) {
        String input = ContextHolder.getContext().getString(R.string.input);
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.toast(input + ContextHolder.getContext().getString(R.string.forgot_phone_hint_step_1));
            return;
        }
//        if (!RegularUtil.isPhone(forgotVM.getPhone())) {
//            ToastUtil.toast(ContextHolder.getContext().getString(R.string.forgot_phone_hint_step_1_error));
//            return;
//        }
        if (!RegularUtil.isPhoneLength(phone)) {
            ToastUtil.toast(ContextHolder.getContext().getString(R.string.forgot_phone_hint_step_1_error));
            return;
        }
        if (TextUtils.isEmpty(forgotVM.getCode())) {
            ToastUtil.toast(input + ContextHolder.getContext().getString(R.string.forgot_pwd_code_step_1));
            return;
        }
        ValidateCodeSub sub = new ValidateCodeSub();
        sub.setPhone(phone);
        sub.setVcode(forgotVM.getCode());
        sub.setType(CommonType.FORGOT_CODE);
        sub.setSignMsg(encode(MDUtil.TYPE.MD5, AppConfig.APP_KEY + phone + CommonType.FORGOT_CODE + forgotVM.getCode()));
        Call<HttpResult<ProbeSmsRec>> call = RDClient.getService(UserService.class).checkCode(sub);
        call.enqueue(new RequestCallBack<HttpResult<ProbeSmsRec>>() {
            @Override
            public void onSuccess(Call<HttpResult<ProbeSmsRec>> call, Response<HttpResult<ProbeSmsRec>> response) {
                if (!Constant.STATUS_10.equals(response.body().getData().getState())) {
                    ToastUtil.toast(response.body().getData().getMessage());
                } else {
                    forgotVM.setIsOne(View.GONE);
                    forgotVM.setIsTwo(View.VISIBLE);
                    forgotVM.setTitle(view.getContext().getResources().getString(R.string.forgot_pwd_title_step_2));
                    isExit = false;
                }
            }
        });
    }

    /**
     * 确认修改
     */
    public void updateClick(final View view) {
        String input = ContextHolder.getContext().getString(R.string.input);
        if (TextUtils.isEmpty(forgotVM.getPwd())) {
            ToastUtil.toast(ContextHolder.getContext().getString(R.string.forgot_pwd_new_hint_step_2));
            return;
        }
        if (TextUtils.isEmpty(forgotVM.getConfirmPwd())) {
            ToastUtil.toast(input + ContextHolder.getContext().getString(R.string.forgot_pwd_confirm_hint_step_2));
            return;
        }
        if (!forgotVM.getConfirmPwd().equals(forgotVM.getPwd())) {
            ToastUtil.toast(R.string.pwd_no_confirm);
            return;
        }
        if (!InputCheck.checkPwd(forgotVM.getPwd()) || !InputCheck.checkPwd(forgotVM.getConfirmPwd())) {
            ToastUtil.toast(ContextHolder.getContext().getString(R.string.settings_pwd_desc));
            return;
        }
        System.out.println("AppConfig.APP_KEY + forgotVM.getPwd() + forgotVM.getPhone() + forgotVM.getCode()" + AppConfig.APP_KEY + MDUtil.encode(MDUtil
                .TYPE.MD5, forgotVM.getPwd()).toUpperCase() + phone + forgotVM.getCode());
        String signMsg = MDUtil.encode(MDUtil.TYPE.MD5, AppConfig.APP_KEY + MDUtil.encode(MDUtil.TYPE.MD5, forgotVM.getPwd()).toUpperCase() + phone + forgotVM.getCode());
        ForgotSub sub = new ForgotSub();
        sub.setSignMsg(signMsg);
        sub.setMobile(phone);
        sub.setPassword(forgotVM.getPwd());
        sub.setVerifyCode(forgotVM.getCode());
        sub.setConfirmPassword(forgotVM.getConfirmPwd());
        Call<HttpResult> call = RDClient.getService(UserService.class).forgetPwd(sub);
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                DialogUtils.showDialog(Util.getActivity(view), SweetAlertType.NORMAL_TYPE, "密码修改成功!", new OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Util.getActivity(view).setResult(RequestResultCode.RES_FORGOT);
                        Util.getActivity(view).finish();
                    }
                }, false);
            }
        });
    }
}
