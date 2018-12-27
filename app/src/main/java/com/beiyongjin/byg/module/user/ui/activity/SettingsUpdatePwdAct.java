package com.beiyongjin.byg.module.user.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.erongdu.wireless.info.SharedInfo;
import com.erongdu.wireless.tools.utils.ContextHolder;
import com.erongdu.wireless.views.appbar.TitleBar;
import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.Routers;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.common.RequestResultCode;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.common.ui.BaseActivity;
import com.beiyongjin.byg.databinding.MineSettingsUpdatePwdActBinding;
import com.beiyongjin.byg.module.user.viewControl.SettingsUpdatePwdCtrl;
import com.beiyongjin.byg.module.user.dataModel.receive.OauthTokenMo;
import com.beiyongjin.byg.utils.Util;

/**
 * Author: JinFu
 * E-mail: jf@erongdu.com
 * Date: 2017/2/21 14:43
 * <p/>
 * Description: 修改密码
 */
@Router(value = RouterUrl.Mine_Settings_Update)
public class SettingsUpdatePwdAct extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MineSettingsUpdatePwdActBinding binding = DataBindingUtil.setContentView(this, R.layout.mine_settings_update_pwd_act);
        binding.setViewCtrl(new SettingsUpdatePwdCtrl());
        binding.toolbar.addAction(new TitleBar.TextAction(ContextHolder.getContext().getString(R.string.mine_settings_update_pwd_forget)) {
            @Override
            public void performAction(View view) {
                String phone = SharedInfo.getInstance().getEntity(OauthTokenMo.class).getUsername();
                Routers.openForResult(Util.getActivity(view), RouterUrl.getRouterUrl(String.format(RouterUrl.UserInfoManage_ForgotPwd, phone, Constant.STATUS_1)), RequestResultCode.REQ_FORGOT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RequestResultCode.REQ_FORGOT && resultCode == RequestResultCode.RES_FORGOT){
            this.finish();
        }
    }
}
