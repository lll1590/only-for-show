package com.beiyongjin.byg.module.home.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

import com.beiyongjin.byg.MyApplication;
import com.beiyongjin.byg.common.AppConfig;
import com.beiyongjin.byg.utils.PermissionUtils;
import com.bqs.risk.df.android.BqsDF;
import com.bqs.risk.df.android.BqsParams;
import com.bqs.risk.df.android.OnBqsDFContactsListener;
import com.bqs.risk.df.android.OnBqsDFListener;
import com.erongdu.wireless.tools.utils.ActivityManage;
import com.erongdu.wireless.tools.utils.BitmapUtil;
import com.erongdu.wireless.tools.utils.ContextHolder;
import com.erongdu.wireless.tools.utils.SPUtil;
import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.Routers;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.BaseParams;
import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.common.ui.BaseActivity;

import java.lang.ref.WeakReference;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/30 15:06
 * <p>
 * Description: 启动页
 */
@Router(RouterUrl.AppCommon_Splash)
public class SplashAct extends BaseActivity  {



    //    // 跳转引导页
    private static final int GO_GUIDE      = 0x01;
    // 跳转首页
    private static final int GO_MAIN       = 0x02;
    // 页面跳转逻辑
    private static final int DO_HANDLER    = 0x99;
    // 最小显示时间
    private static final int SHOW_TIME_MIN = 700;
    // 开始时间
    private static long          mStartTime;
    private        SplashHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        imageView.setImageBitmap(BitmapUtil.readBitmap(this, R.drawable.splash));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_guide_in));
        setContentView(imageView);
        mHandler = new SplashHandler(this);
        // 记录开始时间
        mStartTime = System.currentTimeMillis();
        // 初始化一些数据
        somethingToDo();
    }

    private void somethingToDo() {
        // TODO
        mHandler.removeMessages(DO_HANDLER);
        mHandler.sendEmptyMessage(DO_HANDLER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Routers.open(this, RouterUrl.getRouterUrl(String.format(RouterUrl.AppCommon_Main, Constant.NUMBER_6)));
            finish();
        } else {
            finish();
        }
    }


    /**
     * Handler:跳转到不同界面
     */
    private static class SplashHandler extends Handler {
        WeakReference<SplashAct> act;

        SplashHandler(SplashAct act) {
            this.act = new WeakReference<>(act);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 计算一下总共花费的时间
            long loadingTime = System.currentTimeMillis() - mStartTime;
            switch (msg.what) {
                case DO_HANDLER:
                    // 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
                    boolean isFirstIn = (boolean) SPUtil.getValue(SPUtil.getSp(ContextHolder.getContext(), BaseParams.SP_NAME), Constant.IS_FIRST_IN, true);
                    // 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
                    if (!isFirstIn) {
                        sendEmptyMessage(GO_MAIN);
                    } else {
                        sendEmptyMessage(GO_GUIDE);
                    }
                    break;

                case GO_GUIDE:
                    // 如果比最小显示时间还短，就延时进入GuideActivity，否则直接进入
                    if (loadingTime < SHOW_TIME_MIN) {
                        postDelayed(goToGuideActivity, SHOW_TIME_MIN - loadingTime);
                    } else {
                        post(goToGuideActivity);
                    }
                    break;

                case GO_MAIN:
                    // 如果比最小显示时间还短，就延时进入HomeActivity，否则直接进入
                    if (loadingTime < SHOW_TIME_MIN) {
                        postDelayed(goToMainActivity, SHOW_TIME_MIN - loadingTime);
                    } else {
                        post(goToMainActivity);
                    }
                    break;
            }
        }

        // 进入 GuideAct
        Runnable goToGuideActivity = new Runnable() {
            @Override
            public void run() {
                Routers.open(act.get(), RouterUrl.getRouterUrl(RouterUrl.AppCommon_Guide));
                act.get().finish();
            }
        };
        // 进入 MainAct
        Runnable goToMainActivity  = new Runnable() {
            @Override
            public void run() {
                //                OauthTokenMo oauthTokenMo = SharedInfo.getInstance().getEntity(OauthTokenMo.class);
                //                if (null != oauthTokenMo && (boolean) SharedInfo.getInstance().getValue(Constant.IS_GESTURE_OPENED, false)) {
                //                    GestureLogic.getInstance().showLockView(act.get(), oauthTokenMo.getUserId(),
                //                            (boolean) SharedInfo.getInstance().getValue(Constant.IS_LAND, false));
                //                } else {
                Routers.open(act.get(), RouterUrl.getRouterUrl(String.format(RouterUrl.AppCommon_Main, Constant.NUMBER_6)));
                //                    act.get().finish();
                //                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        ActivityManage.onExit();
    }


}
