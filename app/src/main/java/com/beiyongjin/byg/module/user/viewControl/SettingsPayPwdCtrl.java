package com.beiyongjin.byg.module.user.viewControl;

import android.content.Intent;
import android.databinding.ObservableField;
import android.text.Editable;

import com.erongdu.wireless.info.SharedInfo;
import com.erongdu.wireless.network.entity.HttpResult;
import com.erongdu.wireless.tools.utils.ActivityManage;
import com.erongdu.wireless.tools.utils.ContextHolder;
import com.erongdu.wireless.tools.utils.TextUtil;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.BundleKeys;
import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.common.DialogUtils;
import com.beiyongjin.byg.common.RequestResultCode;
import com.beiyongjin.byg.databinding.MineSettingsPayPwdActBinding;
import com.beiyongjin.byg.module.mine.dataModel.recive.PassRec;
import com.beiyongjin.byg.module.mine.dataModel.submit.UpdatePwdSub;
import com.beiyongjin.byg.module.user.ui.activity.SettingsPayPwdAct;
import com.beiyongjin.byg.network.RDClient;
import com.beiyongjin.byg.network.RequestCallBack;
import com.beiyongjin.byg.network.api.MineService;

import cn.pedant.SweetAlert.OnSweetClickListener;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.pedant.SweetAlert.SweetAlertType;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: JinFu
 * E-mail: jf@erongdu.com
 * Date: 2017/2/22 10:39
 * <p/>
 * Description: 设置/修改支付密码  @{@link SettingsPayPwdAct}
 */
public class SettingsPayPwdCtrl {
    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> tips  = new ObservableField<>();
    private int payType; // 0: 设置交易密码 1:修改交易密码 2:修改交易密码(不可忘记密码) 3:重置交易密码
    private int step = Constant.NUMBER_0; //0: 输入旧密码 1: 输入新密码 : 2: 确认新密码
    private String                       oldpwd;
    private String                       newpwd;
    private MineSettingsPayPwdActBinding binding;

    public SettingsPayPwdCtrl(MineSettingsPayPwdActBinding binding, int payType) {
        this.payType = payType;
        this.binding = binding;
        initView();
    }

    public void setPayType(int payType) {
        this.payType = payType;
        initView();
    }

    public void input(Editable e) {
        if (!TextUtil.isEmpty(e.toString()) && e.toString().length() == 6) {
            switch (step) {
                case Constant.NUMBER_0:
                    reqCheckPwd(e.toString());
                    break;
                case Constant.NUMBER_1:
                    newpwd = e.toString();
                    step = Constant.NUMBER_2;
                    title.set(ContextHolder.getContext().getString(R.string.settints_pay_update_confirm_title));
                    tips.set(ContextHolder.getContext().getString(R.string.settints_pay_update_confirm_tips));
                    e.clear();
                    break;
                case Constant.NUMBER_2:
                    if (newpwd.equals(e.toString())) {
                        if (payType == Constant.NUMBER_0) {
                            reqSetPwd(e.toString());
                        } else if (payType == Constant.NUMBER_3) {
                            resetTradePwd(e.toString());
                        } else {
                            reqUpdatePwd();
                        }
                    } else {
                        SweetAlertDialog dialog = new SweetAlertDialog(ActivityManage.peek(), SweetAlertType.NORMAL_TYPE)
                                .setContentText(ContextHolder.getContext().getResources().getString(R.string.seetings_pwd_tips))
                                .setCancelText(ContextHolder.getContext().getResources().getString(R.string.seetings_pwd_reset))
                                .setConfirmText(ContextHolder.getContext().getResources().getString(R.string.seetings_pwd_again))
                                .setConfirmClickListener(new OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        binding.pwd.setText("");
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                                .setCancelClickListener(new OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        binding.pwd.setText("");
                                        title.set(ContextHolder.getContext().getString(R.string.mine_settings_set_pwd));
                                        tips.set(ContextHolder.getContext().getString(R.string.settints_pay_set_tips));
                                        step = Constant.NUMBER_1;
                                        sweetAlertDialog.dismiss();
                                    }
                                });
                        dialog.setCancelable(false);
                        dialog.show();
                    }
                    break;
            }
        }
    }

    //设置交易密码
    private void reqSetPwd(final String pwd) {
        UpdatePwdSub sub = new UpdatePwdSub();
        sub.setPwd(pwd);
        Call<HttpResult> call = RDClient.getService(MineService.class).setTradePwd(sub);
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                int payseting = (int) SharedInfo.getInstance().getValue(Constant.IS_PAY_SETTING, 0);
                if (payseting == 1) {
                    Intent intent = new Intent();
                    intent.putExtra(BundleKeys.DATA, pwd);
                    ActivityManage.peek().setResult(RequestResultCode.RES_PAY_SETTING_PWD, intent);
                    ActivityManage.pop();
                } else {
                    DialogUtils.showDialog(ActivityManage.peek(), SweetAlertType.NORMAL_TYPE, ContextHolder.getContext().getString(R.string
                                    .settints_pay_success)
                            , new
                                    OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                            ActivityManage.pop();
                                        }
                                    }, false);
                }
            }
        });
    }

    //修改交易密码
    private void reqUpdatePwd() {
        Call<HttpResult> call = RDClient.getService(MineService.class).updatePayPwd(new UpdatePwdSub(newpwd, oldpwd));
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                DialogUtils.showDialog(ActivityManage.peek(), SweetAlertType.NORMAL_TYPE, ContextHolder.getContext().getString(R.string.settints_pay_success)
                        , new
                                OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        ActivityManage.pop();
                                    }
                                }, false);
            }
        });
    }

    //校验交易密码
    private void reqCheckPwd(final String pwd) {
        Call<HttpResult<PassRec>> call = RDClient.getService(MineService.class).validateTradePwd(new UpdatePwdSub(pwd));
        call.enqueue(new RequestCallBack<HttpResult<PassRec>>() {
            @Override
            public void onSuccess(Call<HttpResult<PassRec>> call, Response<HttpResult<PassRec>> response) {
                if (response.body().getData().isPass()) {
                    oldpwd = pwd;
                    step = Constant.NUMBER_1;
                    title.set(ContextHolder.getContext().getString(R.string.settints_pay_update_new_title));
                    tips.set(ContextHolder.getContext().getString(R.string.settints_pay_update_new_tips));
                    binding.pwd.setText("");
                } else {
                    DialogUtils.showDialog(ActivityManage.peek(), SweetAlertType.NORMAL_TYPE, ContextHolder.getContext().getResources().getString(R.string
                            .settints_pay_update_old_pwd_error), new OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            binding.pwd.setText("");
                            sweetAlertDialog.dismiss();
                        }
                    }, false);
                }
            }
        });
    }

    //重置交易密码
    private void resetTradePwd(String pwd) {
        UpdatePwdSub sub = new UpdatePwdSub();
        sub.setNewPwd(pwd);
        Call<HttpResult> call = RDClient.getService(MineService.class).resetTradePwd(sub);
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                DialogUtils.showDialog(ActivityManage.peek(), SweetAlertType.NORMAL_TYPE, ContextHolder.getContext().getString(R.string.settints_pay_success)
                        , new OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                ActivityManage.pop();
                            }
                        }, false);
            }
        });
    }

    private void initView() {
        binding.pwd.setText("");
        if (payType == Constant.NUMBER_0 || payType == Constant.NUMBER_3) {
            title.set(ContextHolder.getContext().getString(R.string.mine_settings_set_pwd));
            tips.set(ContextHolder.getContext().getString(R.string.settints_pay_set_tips));
            step = Constant.NUMBER_1;
            if (this.binding.toolbar.getTitleBar().getActionCount() > 0) {
                this.binding.toolbar.getTitleBar().removeActionAt(0);
            }
        } else {
            title.set(ContextHolder.getContext().getString(R.string.mine_settings_update_paypaw));
            tips.set(ContextHolder.getContext().getString(R.string.settints_pay_update_tips));
            step = Constant.NUMBER_0;
        }
    }
}
