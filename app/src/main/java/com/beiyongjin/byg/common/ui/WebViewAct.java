package com.beiyongjin.byg.common.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.erongdu.wireless.info.SharedInfo;
import com.github.mzule.activityrouter.annotation.Router;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.BundleKeys;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.databinding.WebViewActBinding;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/25 11:57
 * <p/>
 * Description: webview
 */
@Router(value = RouterUrl.AppCommon_IWebView, stringParams = {BundleKeys.TITLE, BundleKeys.URL, BundleKeys.POST_DATA})
public class WebViewAct extends BaseActivity {
    private WebViewActBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent   = getIntent();
        String title    = intent.getStringExtra(BundleKeys.TITLE);
        String url      = intent.getStringExtra(BundleKeys.URL);
        String postData = intent.getStringExtra(BundleKeys.POST_DATA);
        String html     = intent.getStringExtra("html");
        binding = DataBindingUtil.setContentView(this, R.layout.web_view_act);
        String urlTemp = (String) SharedInfo.getInstance().getValue(BundleKeys.URL, "");
        if (!TextUtils.isEmpty(urlTemp)) {
            url = urlTemp;
            SharedInfo.getInstance().saveValue(BundleKeys.URL, "");
        }
        binding.toolBar.setTitle(title);
        binding.setViewCtrl(new WebViewCtrl(binding.webView, url, html, postData));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
        // if (binding.webView.canGoBack()) {
        //     // 后退
        //     binding.webView.goBack();
        // } else {
        //     setResult(RESULT_OK);
        //     super.onBackPressed();
        // }
    }
}
