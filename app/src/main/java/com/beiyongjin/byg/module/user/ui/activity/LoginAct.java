package com.beiyongjin.byg.module.user.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.beiyongjin.byg.utils.Util;
import com.erongdu.wireless.info.SharedInfo;
import com.erongdu.wireless.tools.utils.ActivityManage;
import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.Routers;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.AppConfig;
import com.beiyongjin.byg.common.BundleKeys;
import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.common.DialogUtils;
import com.beiyongjin.byg.common.FeatureConfig;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.common.ui.BaseActivity;
import com.beiyongjin.byg.databinding.UserLoginActBinding;
import com.beiyongjin.byg.module.user.viewControl.LoginCtrl;

import cn.pedant.SweetAlert.OnSweetClickListener;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.pedant.SweetAlert.SweetAlertType;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/16 16:16
 * <p/>
 * Description: 登录页面
 */
@Router(value = RouterUrl.UserInfoManage_Login, stringParams = BundleKeys.TYPE)
public class LoginAct extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserLoginActBinding binding = DataBindingUtil.setContentView(this, R.layout.user_login_act);
        String type = getIntent().getStringExtra(BundleKeys.TYPE);
        binding.setViewCtrl(new LoginCtrl(binding.toolBar,type));
        if (AppConfig.IS_DEBUG) {
            binding.toolBar.getTitleBar().setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    DialogUtils.showEditDialog(view.getContext(), SweetAlertType.EDITTEXT_TYPE, "修改服务器地址并自动退出,再次启动后生效",
                            view.getContext().getString(R.string.dialog_confirm),
                            new OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    SharedInfo.getInstance().saveValue("input_url", sweetAlertDialog.getEditStr());
                                    System.out.println("inputUrl:" + sweetAlertDialog.getEditStr());
                                    sweetAlertDialog.dismiss();
                                    ActivityManage.finishAll();
                                    System.exit(0);

                                }
                            });
                }

            });
        }
    }

    @Override
    public void onBackPressed() {
                String type = getIntent().getStringExtra(BundleKeys.TYPE);
                System.out.println("type="+type);
                if ("1".equals(type)) {
                    Routers.open(getApplicationContext(), RouterUrl.getRouterUrl(String.format(RouterUrl.AppCommon_Main, Constant.NUMBER_1)));
                } else {
                    if(FeatureConfig.enableFeature(FeatureConfig.enablewebHomeFeature)){
                        if("4".equals(type)){
                            Routers.open(getApplicationContext(), RouterUrl.getRouterUrl(String.format(RouterUrl.AppCommon_Main, Constant.NUMBER_4)));
                        }else{
                            Routers.open(getApplicationContext(), RouterUrl.getRouterUrl(String.format(RouterUrl.AppCommon_Main, Constant.NUMBER_0)));
                        }
                    }else{
                        Routers.open(getApplicationContext(), RouterUrl.getRouterUrl(String.format(RouterUrl.AppCommon_Main, Constant.NUMBER_0)));
                    }

                }
            }

}
