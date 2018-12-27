package com.beiyongjin.byg.module.user.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.view.inputmethod.InputMethodManager;

import com.amap.api.location.AMapLocation;
import com.erongdu.wireless.tools.utils.ActivityManage;
import com.erongdu.wireless.tools.utils.PermissionCheck;
import com.erongdu.wireless.tools.utils.TextUtil;
import com.github.mzule.activityrouter.annotation.Router;
import com.beiyongjin.byg.MyApplication;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.BundleKeys;
import com.beiyongjin.byg.common.DialogUtils;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.common.ui.BaseActivity;
import com.beiyongjin.byg.databinding.UserRegisterActBinding;
import com.beiyongjin.byg.module.user.viewControl.RegisterCtrl;

import cn.pedant.SweetAlert.OnSweetClickListener;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/16 16:16
 * <p>
 * Description: 注册页面
 */
@Router(value = RouterUrl.UserInfoManage_IRegister, stringParams = BundleKeys.ID)
public class RegisterAct extends BaseActivity {
    private RegisterCtrl registerCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserRegisterActBinding binding            = DataBindingUtil.setContentView(this, R.layout.user_register_act);
        InputMethodManager     inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(binding.getRoot().getRootView(), 0);
        String phone = getIntent().getStringExtra(BundleKeys.ID);
        registerCtrl = new RegisterCtrl(binding, phone, this);
        binding.setViewCtrl(registerCtrl);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PermissionCheck.getInstance().askForPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION}, PermissionCheck.REQUEST_CODE_ALL);
        if (!MyApplication.isOpen(getApplicationContext())) {
            DialogUtils.showDialog(RegisterAct.this, getResources().getString(R.string.register_gps_state), new OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, 0);
                    sweetAlertDialog.dismiss();
                }
            }, new OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    ActivityManage.pop();
                    sweetAlertDialog.dismiss();
                }
            });
        } else {
            if (TextUtil.isEmpty(MyApplication.address) || 0 == MyApplication.lat || 0 == MyApplication.lon) {
                MyApplication.openGps(new MyApplication.OnPosChanged() {
                    @Override
                    public void changed(AMapLocation location) {
                        registerCtrl.address.set(location.getAddress());
                        registerCtrl.coordinate.set(location.getLongitude() + "," + location.getLatitude());
                    }
                }, true);
            } else {
                registerCtrl.address.set(MyApplication.address);
                registerCtrl.coordinate.set(MyApplication.lon + "," + MyApplication.lat);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //MyApplication.closeGps();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //MyApplication.closeGps();
    }
}
