package com.beiyongjin.byg.module.user.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.github.mzule.activityrouter.annotation.Router;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.common.ui.BaseActivity;
import com.beiyongjin.byg.databinding.UserResetPwdActBinding;
import com.beiyongjin.byg.module.user.viewControl.ResetPwdCtrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/17 18:48
 * <p/>
 * Description: 修改密码
 */
@Router(value = RouterUrl.UserInfoManage_ResetPwd)
public class ResetPwdAct extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserResetPwdActBinding binding = DataBindingUtil.setContentView(this, R.layout.user_reset_pwd_act);
        binding.setViewCtrl(new ResetPwdCtrl());
    }
}
