package com.beiyongjin.byg.module.user.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.erongdu.wireless.tools.utils.ContextHolder;
import com.erongdu.wireless.views.appbar.TitleBar;
import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.Routers;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.BundleKeys;
import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.common.RequestResultCode;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.common.ui.BaseActivity;
import com.beiyongjin.byg.databinding.MineSettingsPayPwdActBinding;
import com.beiyongjin.byg.module.user.viewControl.SettingsPayPwdCtrl;

/**
 * Author: JinFu
 * E-mail: jf@erongdu.com
 * Date: 2017/2/22 10:46
 * <p/>
 * Description: 设置/修改支付密码
 */
@Router(value = RouterUrl.Mine_Settings_Pay_Pwd, intParams = BundleKeys.TYPE)
public class SettingsPayPwdAct extends BaseActivity {
    private int payType = Constant.NUMBER_0;
    private SettingsPayPwdCtrl ctrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        payType = getIntent().getIntExtra(BundleKeys.TYPE, Constant.NUMBER_0);
        MineSettingsPayPwdActBinding binding = DataBindingUtil.setContentView(this, R.layout.mine_settings_pay_pwd_act);
        ctrl = new SettingsPayPwdCtrl(binding, payType);
        binding.setViewCtrl(ctrl);
//        if (payType == Constant.NUMBER_1) {
            binding.toolbar.addAction(new TitleBar.TextAction(ContextHolder.getContext().getString(R.string.settings_pwd_forget)) {
                @Override
                public void performAction(View view) {
                    Routers.openForResult(SettingsPayPwdAct.this, RouterUrl.getRouterUrl(RouterUrl.UserInfoManage_ForgotPayPwd), RequestResultCode
                            .REQ_FORGOT_PAY);
                }
            });
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            ctrl.setPayType(Constant.NUMBER_3);
        }
        if (requestCode == RequestResultCode.REQ_FORGOT_PAY && resultCode == RequestResultCode.RES_FORGOT_PAY) {
            ctrl.setPayType(Constant.NUMBER_3);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 移除关于支付设置交易密码变量
        // SharedInfo.getInstance().remove(Constant.IS_PAY_SETTING);
    }
}
