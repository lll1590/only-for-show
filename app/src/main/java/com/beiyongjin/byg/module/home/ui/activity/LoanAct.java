package com.beiyongjin.byg.module.home.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;

import com.amap.api.location.AMapLocation;
import com.erongdu.wireless.info.SharedInfo;
import com.erongdu.wireless.tools.utils.ActivityManage;
import com.erongdu.wireless.tools.utils.PermissionCheck;
import com.erongdu.wireless.tools.utils.TextUtil;
import com.github.mzule.activityrouter.annotation.Router;
import com.beiyongjin.byg.MyApplication;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.BundleKeys;
import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.common.DialogUtils;
import com.beiyongjin.byg.common.RequestResultCode;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.common.ui.BaseActivity;
import com.beiyongjin.byg.databinding.HomeLoanActBinding;
import com.beiyongjin.byg.module.home.viewControl.LoanCtrl;
import com.beiyongjin.byg.utils.Util;

import cn.pedant.SweetAlert.OnSweetClickListener;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Author: JinFu
 * E-mail: jf@erongdu.com
 * Date: 2017/2/14 16:09
 * <p/>
 * Description: 借款
 */
@Router(value = RouterUrl.Loan_Details, stringParams = {BundleKeys.LOANMONEY, BundleKeys.LOANLIMIT, BundleKeys.REALMONEY, BundleKeys.FEE,
        BundleKeys.CARDNAME, BundleKeys.CARDNO, BundleKeys.CARDID})
public class LoanAct extends BaseActivity {
    private LoanCtrl loanCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeLoanActBinding binding = DataBindingUtil.setContentView(this, R.layout.home_loan_act);
        Intent             intent  = getIntent();

        loanCtrl = new LoanCtrl(binding,binding.apply, intent.getStringExtra(BundleKeys.LOANMONEY), intent.getStringExtra
                (BundleKeys.LOANLIMIT), intent.getStringExtra(BundleKeys.REALMONEY), intent.getStringExtra(BundleKeys.FEE), intent.getStringExtra(BundleKeys
                .CARDNAME), intent.getStringExtra(BundleKeys.CARDNO), intent.getStringExtra(BundleKeys.CARDID),((MyApplication)getApplication()).getBaiQiToken());
        binding.setViewCtrl(loanCtrl);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PermissionCheck.getInstance().askForPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION}, PermissionCheck.REQUEST_CODE_ALL);
        if (!MyApplication.isOpen(getApplicationContext())) {
            DialogUtils.showDialog(LoanAct.this, getResources().getString(R.string.loan_gps_state), new OnSweetClickListener() {
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
                        loanCtrl.address.set(location.getAddress());
                        loanCtrl.coordinate.set(location.getLongitude() + "," + location.getLatitude());
                    }
                }, true);
            } else {
                loanCtrl.address.set(MyApplication.address);
                loanCtrl.coordinate.set(MyApplication.lon + "," + MyApplication.lat);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RequestResultCode.REQ_PAY_SETTING_PWD && resultCode == RequestResultCode.RES_PAY_SETTING_PWD){
            // 移除关于支付设置交易密码变量
            SharedInfo.getInstance().remove(Constant.IS_PAY_SETTING);
            String pwd = data.getStringExtra(BundleKeys.DATA);
            loanCtrl.requestLoan(pwd);
        }
        if (loanCtrl.popView.isShowing()) {
            Util.showKeyboard(getApplicationContext());
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
