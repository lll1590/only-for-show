package com.beiyongjin.byg.module.user.viewControl;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.erongdu.wireless.info.SharedInfo;
import com.erongdu.wireless.network.entity.HttpResult;
import com.erongdu.wireless.tools.encryption.MDUtil;
import com.erongdu.wireless.tools.utils.ChineseUtil;
import com.erongdu.wireless.tools.utils.ContextHolder;
import com.erongdu.wireless.tools.utils.RegularUtil;
import com.erongdu.wireless.tools.utils.ToastUtil;
import com.github.mzule.activityrouter.router.Routers;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.AppConfig;
import com.beiyongjin.byg.common.CommonType;
import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.common.RequestResultCode;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.databinding.UserForgotPayActBinding;
import com.beiyongjin.byg.module.mine.dataModel.recive.PassRec;
import com.beiyongjin.byg.module.user.dataModel.receive.OauthTokenMo;
import com.beiyongjin.byg.module.user.dataModel.receive.ProbeSmsRec;
import com.beiyongjin.byg.module.user.dataModel.submit.ForgotPaySub;
import com.beiyongjin.byg.module.user.viewModel.ForgotPayVM;
import com.beiyongjin.byg.network.RDClient;
import com.beiyongjin.byg.network.RequestCallBack;
import com.beiyongjin.byg.network.api.MineService;
import com.beiyongjin.byg.network.api.UserService;
import com.beiyongjin.byg.utils.Util;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: Hubert
 * E-mail: hbh@erongdu.com
 * Date: 2017/2/25 下午7:45
 * <p/>
 * Description:找回交易密码 @{@link com.beiyongjin.byg.module.user.ui.activity.ForgotPayAct}
 */
public class ForgotPayCtrl {
    public  ForgotPayVM             forgotPayVM;
    private UserForgotPayActBinding binding;
    private String                  type;

    public ForgotPayCtrl(UserForgotPayActBinding binding, String type) {
        forgotPayVM = new ForgotPayVM();
        this.type = type;
        this.binding = binding;
        String phone = SharedInfo.getInstance().getEntity(OauthTokenMo.class).getUsername();
        forgotPayVM.setPhone(phone);
        //        reqinit();
    }

    public void nextClick(final View v) {
        if (TextUtils.isEmpty(forgotPayVM.getName())) {
            ToastUtil.toast(ContextHolder.getContext().getString(R.string.settings_forgot_pay_name_tip));
            return;
        }
        if (!ChineseUtil.isChineseName(forgotPayVM.getName())) {
            ToastUtil.toast(ContextHolder.getContext().getString(R.string.settings_forgot_pay_name_not_chinese));
            return;
        }
        if (TextUtils.isEmpty(forgotPayVM.getNo())) {
            ToastUtil.toast(ContextHolder.getContext().getString(R.string.settings_forgot_pay_no_tip));
            return;
        }
        if (TextUtils.isEmpty(forgotPayVM.getCode())) {
            ToastUtil.toast(R.string.settings_forgot_pay_code_tip);
            return;
        }
        ForgotPaySub sub = new ForgotPaySub();
        sub.setIdNo(forgotPayVM.getNo());
        sub.setRealName(forgotPayVM.getName());
        sub.setVcode(forgotPayVM.getCode());
        Call<HttpResult<PassRec>> call = RDClient.getService(MineService.class).validateUser(sub);
        call.enqueue(new RequestCallBack<HttpResult<PassRec>>() {
            @Override
            public void onSuccess(Call<HttpResult<PassRec>> call, Response<HttpResult<PassRec>> response) {
                if (response.body().getData().isPass()) {
                    Activity activity = Util.getActivity(v);
                    if (Constant.STATUS_1.equals(type)) {
                        Routers.open(activity, RouterUrl.getRouterUrl(String.format(RouterUrl.Mine_Settings_Pay_Pwd, Constant.NUMBER_3)));
                        activity.finish();
                    } else {
                        activity.setResult(RequestResultCode.RES_FORGOT_PAY);
                        activity.finish();
                    }
                } else {
                    ToastUtil.toast(response.body().getMsg());
                }
            }
        });
    }

    /** 请求个人信息 */
    //    private void reqinit() {
    //        String phone = SharedInfo.getInstance().getEntity(OauthTokenMo.class).getUsername();
    //        forgotPayVM.setPhone(phone);
    //        Call<HttpResult<ProbeSmsRec>> phoneCall = RDClient.getService(UserService.class).probeSms(forgotPayVM.getPhone(), CommonType.PAY_CODE);
    //        NetworkUtil.showCutscenes(phoneCall);
    //        phoneCall.enqueue(new RequestCallBack<HttpResult<ProbeSmsRec>>() {
    //            @Override
    //            public void onSuccess(Call<HttpResult<ProbeSmsRec>> call, Response<HttpResult<ProbeSmsRec>> response) {
    //                ProbeSmsRec rec = response.body().getData();
    //                if (!Constant.STATUS_10.equals(rec.getState())) {
    //                    binding.timeButton.setLength(ConverterUtil.getLong(rec.getCountDown()) * 1000);
    //                    binding.timeButton.start();
    //                } else {
    //                    binding.timeButton.runTimer();
    //                }
    //            }
    //        });
    //    }

    /**
     * 获取验证码
     */
    public void getCodeClick(View view) {
        if (!RegularUtil.isPhoneLength(forgotPayVM.getPhone())) {
            ToastUtil.toast(ContextHolder.getContext().getString(R.string.forgot_phone_hint_step_1_error));
            return;
        }
        String                        sign     = MDUtil.encode(MDUtil.TYPE.MD5, AppConfig.APP_KEY + forgotPayVM.getPhone() + CommonType.PAY_CODE);
        Call<HttpResult<ProbeSmsRec>> callCode = RDClient.getService(UserService.class).getCode(forgotPayVM.getPhone(), CommonType.PAY_CODE, sign);
        callCode.enqueue(new RequestCallBack<HttpResult<ProbeSmsRec>>() {
            @Override
            public void onSuccess(Call<HttpResult<ProbeSmsRec>> call, Response<HttpResult<ProbeSmsRec>> response) {
                if (!Constant.STATUS_10.equals(response.body().getData().getState())) {
                    ToastUtil.toast(response.body().getData().getMessage());
                } else {
                    binding.timeButton.runTimer();
                    ToastUtil.toast(response.body().getMsg());
                }
            }
        });
    }
}
