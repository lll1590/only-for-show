package com.beiyongjin.byg.module.user.viewControl;

import android.Manifest;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.amap.api.location.AMapLocation;
import com.erongdu.wireless.friday.Friday;
import com.erongdu.wireless.info.SharedInfo;
import com.erongdu.wireless.network.entity.HttpResult;
import com.erongdu.wireless.network.entity.ListData;
import com.erongdu.wireless.tools.encryption.MDUtil;
import com.erongdu.wireless.tools.utils.ContextHolder;
import com.erongdu.wireless.tools.utils.PermissionCheck;
import com.erongdu.wireless.tools.utils.TextUtil;
import com.erongdu.wireless.tools.utils.ToastUtil;
import com.github.mzule.activityrouter.router.Routers;
import com.beiyongjin.byg.MyApplication;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.AppConfig;
import com.beiyongjin.byg.common.CommonType;
import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.common.FeatureConfig;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.databinding.UserRegisterActBinding;
import com.beiyongjin.byg.module.mine.dataModel.recive.CommonRec;
import com.beiyongjin.byg.module.user.dataModel.receive.OauthTokenMo;
import com.beiyongjin.byg.module.user.dataModel.receive.ProbeSmsRec;
import com.beiyongjin.byg.module.user.dataModel.submit.RegisterSub;
import com.beiyongjin.byg.module.user.ui.activity.RegisterAct;
import com.beiyongjin.byg.module.user.viewModel.RegisterVM;
import com.beiyongjin.byg.network.NetworkUtil;
import com.beiyongjin.byg.network.RDClient;
import com.beiyongjin.byg.network.RequestCallBack;
import com.beiyongjin.byg.network.api.CommonService;
import com.beiyongjin.byg.network.api.UserService;
import com.beiyongjin.byg.utils.FridayConstant;
import com.beiyongjin.byg.utils.InputCheck;
import com.beiyongjin.byg.utils.Util;

import java.util.ArrayList;
import java.util.List;

import cn.tongdun.android.shell.FMAgent;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/17 14:02
 * <p/>
 * Description: 注册页面控制器({@link RegisterAct})
 */
public class RegisterCtrl {
    public  RegisterVM   viewModel;
    /** 图形验证码校验时需要的sid */
    private String       sid;
    private LinearLayout protocolLayout;
    public ObservableField<String> address    = new ObservableField<>();
    public ObservableField<String> coordinate = new ObservableField<>();
    private UserRegisterActBinding binding;
    private RegisterAct            activity;
    private String phone;
    /** 是否显示邀请好友 */
    public boolean isInvite = FeatureConfig.enableFeature(FeatureConfig.enableinviteFeature);

    public RegisterCtrl(UserRegisterActBinding binding, String phone, RegisterAct activity) {
        viewModel = new RegisterVM();
        this.phone = phone;
        this.binding = binding;
        this.activity = activity;
        viewModel.setProtocolLayout(binding.protocol);
        viewModel.setPhone(Util.phoneBlur(phone));
//        viewModel.setPhone(phone);
        reqData(binding);
        getCodeClick(binding.getRoot());
    }

    /**
     * 获取验证码
     */
    public void getCodeClick(final View view) {
        Friday.onEvent(view.getContext(), FridayConstant.REGISTER_CODE);
        String                        sign     = MDUtil.encode(MDUtil.TYPE.MD5, AppConfig.APP_KEY + phone+ CommonType.REGISTER_CODE);
        Call<HttpResult<ProbeSmsRec>> callCode = RDClient.getService(UserService.class).getCode(phone, CommonType.REGISTER_CODE, sign);
        callCode.enqueue(new RequestCallBack<HttpResult<ProbeSmsRec>>() {
            @Override
            public void onSuccess(Call<HttpResult<ProbeSmsRec>> call, Response<HttpResult<ProbeSmsRec>> response) {
                if (!Constant.STATUS_10.equals(response.body().getData().getState())) {
                    ToastUtil.toast(response.body().getData().getMessage());
                    //binding.timeButton.setLength()
                    //binding.timeButton.runTimer();
                } else {
                    binding.timeButton.runTimer();
                    ToastUtil.toast(response.body().getMsg());
                }
            }
        });
    }

    /** 初始化协议 */
    private void reqData(final UserRegisterActBinding binding) {
        Call<HttpResult<ListData<CommonRec>>> call = RDClient.getService(CommonService.class).protocolList();
        call.enqueue(new RequestCallBack<HttpResult<ListData<CommonRec>>>() {
            @Override
            public void onSuccess(Call<HttpResult<ListData<CommonRec>>> call, Response<HttpResult<ListData<CommonRec>>> response) {
                protocolInit(response.body().getData().getList());
            }
        });
        //        Call<HttpResult<ProbeSmsRec>> phoneCall = RDClient.getService(UserService.class).probeSms(viewModel.getPhone(), CommonType.REGISTER_CODE);
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
    }

    private void protocolInit(List<CommonRec> list) {
        List<CommonRec> temp = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getCode().indexOf(CommonType.PROTOCOL_REGISTER) != -1) {
                    temp.add(list.get(i));
                }
            }
        }
        viewModel.setProtocolList(temp);
    }

    /** 根据是否获得焦点 更改高粱图片 */
    public void editFocusChange(View view, boolean focus) {
        switch (view.getId()) {
            case R.id.code:
                if (focus) {
                    viewModel.setCodeSel(true);
                } else {
                    viewModel.setCodeSel(false);
                }
                break;
            case R.id.newPwd:
                if (focus) {
                    viewModel.setPwdSel(true);
                } else {
                    viewModel.setPwdSel(false);
                }
                break;
            case R.id.invite:
                if (focus) {
                    viewModel.setInviteSel(true);
                } else {
                    viewModel.setInviteSel(false);
                }
                break;
        }
    }

    /**
     * 注册协议点击
     */
    public void protocolClick(View view) {
        ToastUtil.toast("注册协议");
    }

    /**
     * 注册_信息提交
     */
    public void submitClick(final View view) {
        Friday.onEvent(view.getContext(), FridayConstant.REGISTER_SUBMIT);
        if (PermissionCheck.getInstance().hasAlwaysDeniedPermission(activity, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
            PermissionCheck.getInstance().askForPermissions(activity, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionCheck.REQUEST_CODE_ALL);
            return;
        }
        if (TextUtils.isEmpty(viewModel.getCode())) {
            ToastUtil.toast(R.string.register_code_hint);
            return;
        }
        if (TextUtils.isEmpty(viewModel.getPwd())) {
            ToastUtil.toast(ContextHolder.getContext().getString(R.string.login_pwd_hint));
            return;
        }
        if (!InputCheck.checkPwd(viewModel.getPwd())) {
            ToastUtil.toast(ContextHolder.getContext().getString(R.string.forgot_pwd_tips));
            return;
        }

        if (MyApplication.isOpen(binding.getRoot().getContext())) {

        }
        System.out.println("login action1");
        if (TextUtil.isEmpty(address.get()) || TextUtil.isEmpty(coordinate.get())) {
            NetworkUtil.showCutscenes("", "");
            MyApplication.openGps(new MyApplication.OnPosChanged() {
                @Override
                public void changed(AMapLocation location) {
                    address.set(location.getAddress());
                    coordinate.set(location.getLongitude() + "," + location.getLatitude());
                    NetworkUtil.dismissCutscenes();
                }
            }, true);
            return;
        }
        RegisterSub sub = new RegisterSub();
        sub.setPhone(phone);
        sub.setPwd(viewModel.getPwd());
        sub.setCode(viewModel.getCode());
        sub.setInviter(viewModel.getInvite());
        sub.setRegisterAddr(address.get());
        sub.setRegisterCoordinate(coordinate.get());
        sub.setClient("android");
        sub.setExtToken(((MyApplication)Util.getActivity(view).getApplication()).getBaiQiToken());
        Log.i("hjc","baiqishiToken:::"+sub.toString());
        if (FeatureConfig.enableFeature(FeatureConfig.enabletongdunModuleFeature)) {
            sub.setBox(FMAgent.onEvent(ContextHolder.getContext()));
        }
        System.out.println("login action2");
        Call<HttpResult<OauthTokenMo>> call = RDClient.getService(UserService.class).doRegister(sub);
        call.enqueue(new RequestCallBack<HttpResult<OauthTokenMo>>() {
            @Override
            public void onSuccess(Call<HttpResult<OauthTokenMo>> call, Response<HttpResult<OauthTokenMo>> response) {
                SharedInfo.getInstance().saveValue(Constant.IS_LAND, true);
                OauthTokenMo mo = response.body().getData();
                mo.setUsername(phone);
                SharedInfo.getInstance().saveEntity(mo);
                System.out.println("login Success");
                Routers.open(view.getContext(), RouterUrl.getRouterUrl(RouterUrl.UserInfoManage_UserHomePage));
                Util.getActivity(view).finish();
            }
        });
    }
}
