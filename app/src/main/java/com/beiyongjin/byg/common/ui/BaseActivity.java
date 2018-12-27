package com.beiyongjin.byg.common.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.beiyongjin.byg.utils.EventBusUtils;
import com.bugtags.library.Bugtags;
import com.erongdu.wireless.tools.utils.PermissionCheck;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.network.NetworkUtil;
import com.beiyongjin.byg.views.SystemBarTintManager;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/18 10:11
 * <p/>
 * Description: Activity基类
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isToolBarTransparence()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //透明状态栏
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                SystemBarTintManager tintManager = new SystemBarTintManager(this);
                tintManager.setStatusBarTintEnabled(false);
                tintManager.setStatusBarTintResource(R.color.app_color_principal);
            }


        }

        if(isNeedEventBus()){
            EventBusUtils.register(this);
        }

    /*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.app_color_principal);
        }*/
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }*/

    }

    public boolean isNeedEventBus() {
        return false;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setTranslucentStatus(boolean on) {
        Window                     win       = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int                  bits      = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Bugtags.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /*for(int i = 0; i< permissions.length;i++){
            System.out.println("permissions" + permissions[i]);
            System.out.println("permissions" + grantResults[i]);
        }*/
        Log.e("permission", permissions.toString());
        PermissionCheck.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    public void finish() {
        super.finish();
        // overridePendingTransition(R.anim.slide_out_from_left, R.anim.slide_out_to_right);
    }

    @Override
    protected void onDestroy() {
        NetworkUtil.dismissCutscenes();
        super.onDestroy();

        if(isNeedEventBus()){
            //取消EventBus的注册
            EventBusUtils.unregister(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        FragmentManager fm    = getSupportFragmentManager();
        int             index = requestCode >> 16;
        if (index != 0) {
            index--;
            if (fm.getFragments() == null || index < 0 || index >= fm.getFragments().size()) {
                return;
            }
            Fragment frag = fm.getFragments().get(index);
            if (frag != null) {
                handleResult(frag, requestCode, resultCode, data);
            } else {
            }
        }
    }

    /**
     * 递归调用，对所有子 Fragment 生效
     */
    private void handleResult(Fragment frag, int requestCode, int resultCode, Intent data) {
        frag.onActivityResult(requestCode & 0xffff, resultCode, data);
        List<Fragment> frags = frag.getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null)
                    handleResult(f, requestCode, resultCode, data);
            }
        }
    }

    public  boolean isToolBarTransparence(){
        return true;

    }
}
