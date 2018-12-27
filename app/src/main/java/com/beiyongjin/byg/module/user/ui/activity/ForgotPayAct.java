package com.beiyongjin.byg.module.user.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.github.mzule.activityrouter.annotation.Router;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.BundleKeys;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.common.ui.BaseActivity;
import com.beiyongjin.byg.databinding.UserForgotPayActBinding;
import com.beiyongjin.byg.module.user.viewControl.ForgotPayCtrl;

/**
 * Author: Hubert
 * E-mail: hbh@erongdu.com
 * Date: 2017/2/25 下午7:33
 * <p/>
 * Description:找回交易密码
 */
@Router(value = RouterUrl.UserInfoManage_ForgotPayPwd,stringParams = BundleKeys.TYPE)
public class ForgotPayAct extends BaseActivity {
    private ForgotPayCtrl forgotPayCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserForgotPayActBinding binding = DataBindingUtil.setContentView(this, R.layout.user_forgot_pay_act);
        String type = getIntent().getStringExtra(BundleKeys.TYPE);
        forgotPayCtrl = new ForgotPayCtrl(binding,type);

        binding.setViewCtrl(forgotPayCtrl);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            this.finish();
        }
    }
}
