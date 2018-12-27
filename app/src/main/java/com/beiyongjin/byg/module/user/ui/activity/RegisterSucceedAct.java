package com.beiyongjin.byg.module.user.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.github.mzule.activityrouter.annotation.Router;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.common.ui.BaseActivity;
import com.beiyongjin.byg.databinding.UserRegisterSuccessActBinding;
import com.beiyongjin.byg.module.user.viewControl.RegisterSucceedCtrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/17 17:34
 * <p/>
 * Description: 注册成功
 */
@Router(RouterUrl.UserInfoManage_RegisterSucceed)
public class RegisterSucceedAct extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserRegisterSuccessActBinding binding = DataBindingUtil.setContentView(this, R.layout.user_register_success_act);
        binding.setViewCtrl(new RegisterSucceedCtrl(binding.toolBar));
    }

    @Override
    public void onBackPressed() {
    }
}
