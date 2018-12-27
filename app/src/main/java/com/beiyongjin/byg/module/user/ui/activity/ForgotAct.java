package com.beiyongjin.byg.module.user.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.erongdu.wireless.tools.utils.ContextHolder;
import com.github.mzule.activityrouter.annotation.Router;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.BundleKeys;
import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.common.ui.BaseActivity;
import com.beiyongjin.byg.databinding.UserForgotActBinding;
import com.beiyongjin.byg.module.user.viewControl.ForgotCtrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/17 13:57
 * <p/>
 * Description: 忘记密码
 */
@Router(value = RouterUrl.UserInfoManage_IForgotPwd, stringParams = {BundleKeys.ID, BundleKeys.TYPE})
public class ForgotAct extends BaseActivity {
    private ForgotCtrl forgotCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserForgotActBinding binding = DataBindingUtil.setContentView(this, R.layout.user_forgot_act);
        String               phone   = getIntent().getStringExtra(BundleKeys.ID);
        String               type    = getIntent().getStringExtra(BundleKeys.TYPE);
        if (Constant.STATUS_1.equals(type)) {
            binding.phoneEdit.setKeyListener(null);
        }
        forgotCtrl = new ForgotCtrl(binding.timeButton, phone);
        binding.setViewCtrl(forgotCtrl);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (!forgotCtrl.isExit) {
            forgotCtrl.isExit = true;
            forgotCtrl.forgotVM.setIsOne(View.VISIBLE);
            forgotCtrl.forgotVM.setIsTwo(View.GONE);
            forgotCtrl.forgotVM.setTitle(ContextHolder.getContext().getString(R.string.forgot_pwd_title_step_1));
        } else {
            super.onBackPressed();
        }
    }


}
