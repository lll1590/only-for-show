package com.beiyongjin.byg.common.ui;

import android.net.Uri;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/3/16 10:41
 * <p/>
 * Description: App Links
 */
public class SchemeFilterActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 直接通过 ARouter 处理外部 Uri
        Uri uri = getIntent().getData();
        ARouter.getInstance().build(uri).navigation(this, new NavigationCallback() {
            @Override
            public void onFound(Postcard postcard) {
                finish();
            }

            @Override
            public void onLost(Postcard postcard) {
                finish();
            }

            @Override
            public void onArrival(Postcard postcard) {
            }

            @Override
            public void onInterrupt(Postcard postcard) {
            }
        });
    }
}
