package com.beiyongjin.byg.common.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.erongdu.wireless.info.SharedInfo;
import com.erongdu.wireless.network.entity.HttpResult;
import com.erongdu.wireless.network.entity.ListData;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.BundleKeys;
import com.beiyongjin.byg.common.CommonType;
import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.common.RequestResultCode;
import com.beiyongjin.byg.module.mine.dataModel.recive.CommonRec;
import com.beiyongjin.byg.network.RDClient;
import com.beiyongjin.byg.network.RequestCallBack;
import com.beiyongjin.byg.network.UrlUtils;
import com.beiyongjin.byg.network.api.CommonService;
import com.beiyongjin.byg.utils.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/25 11:52
 * <p/>
 * Description: webview控制器({@link WebViewAct})
 */
public class WebViewCtrl {
    private Handler mHandler = new Handler();
    private Activity activity;

    public WebViewCtrl(final WebView webView, String url, String html, String postData) {
        System.out.println("url" + url);
        if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(postData)) {
            webView.postUrl(url, postData.getBytes());
        } else {
            String sign = (String) SharedInfo.getInstance().getValue(BundleKeys.SIGN, "");
            if (!TextUtils.isEmpty(sign)) {
                SharedInfo.getInstance().saveValue(BundleKeys.SIGN, "");
                Map map = new HashMap<String, String>();
                map.put(Constant.TOKEN, UrlUtils.getInstance().getToken());
                map.put(Constant.SIGNA, sign);
                webView.loadUrl(url, map);
            } else {
                webView.loadUrl(url);
            }
        }
        activity = Util.getActivity(webView);
        WebSettings setting = webView.getSettings();
        // 支持缩放
        setting.setSupportZoom(false);
        // 设置支持缩放 + -
        // setting.setBuiltInZoomControls(false);
        // 关闭 webView 中缓存
        /**/
        /*webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);*/
        setting.setUseWideViewPort(false);
        setting.setLoadWithOverviewMode(true);
        // 设置WebView属性，能够执行Javascript脚本
        setting.setJavaScriptEnabled(true);
        setting.setSavePassword(false);
        setting.setDomStorageEnabled(true);
        setting.setDefaultTextEncodingName("utf-8");

        webView.addJavascriptInterface(new WebReturn(), "webReturn");

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            dealJavascriptLeak(webView);
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void dealJavascriptLeak(WebView webView) {
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        webView.removeJavascriptInterface("accessibility");
        webView.removeJavascriptInterface("accessibilityTraversal");
    }

    private class WebReturn {
        @JavascriptInterface
        public void webReturn(final String resCode, final String resMsg) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    // 开户  register_success
                    // 授权  auth_success
                    // 充值成功 recharge_success
                    // 提现成功 cash_success
                    // 普通投资 invest_success
                    // 债权转让投资处理成功 bond_success
                    // 变现投资处理成功 realize_success
                    // 解绑 delBindBank_success
                    System.out.println("****************************");
                    System.out.println("resCode = " + resCode);
                    System.out.println("resMsg = " + resMsg);
                    System.out.println("****************************");
                    //ToastUtil.toast(resMsg);

                    /*if ("register_success".equals(resCode)) {
                        Routers.openForResult(activity, RouterUrl.getRouterUrl(RouterUrl.Mine_RealnameSucceed), 0);
                    }*/
                    Intent intent = new Intent();
                    intent.putExtra(BundleKeys.CODE, resCode);
                    intent.putExtra(BundleKeys.MSG, resMsg);
                    activity.setResult(RequestResultCode.RES_ZMXY, intent);
                    activity.finish();
                    activity = null;
                }
            });
        }
    }

    private class MyWebViewClient extends WebViewClient {
        public void onReceivedSslError(WebView view,
                                       SslErrorHandler handler, SslError error) {
            // 　　//handler.cancel(); 默认的处理方式，WebView变成空白页
            handler.proceed(); // 接受证书
            // handleMessage(Message msg); 其他处理

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("api/operatorReturnback.htm")) {
                activity.setResult(RequestResultCode.RES_PHONE);
                activity.finish();
            }
            /*if (url.contains("api/actzm/mine/zhima/AuthCallBack.htm")) {
                activity.setResult(RequestResultCode.RES_ZMXY);
                activity.finish();
            }*/
            if (url.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse(url));
                activity.startActivity(intent);
            } else if (url.startsWith("sms:")) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                String[] sms = url.split("\\?");
                intent.setType("vnd.android-dir/mms-sms");
                intent.setData(Uri.parse(sms[0]));
                if(sms.length >=2){
                    String[] body=sms[1].split("=");
                    intent.putExtra("sms_body",body[1]);
                }
                activity.startActivity(intent);
            } else {
                view.loadUrl(url);
            }
            return false;
        }

        // 开始加载网页时要做的工作
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            System.out.println("url = " + url);
        }

        // 加载完成时要做的工作
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        // JS的提示框
        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            // 构建一个Builder来显示网页中的alert对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle(view.getContext().getString(R.string.dialog_title));
            builder.setMessage(message);
            builder.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            });
            builder.setCancelable(false);
            builder.create();
            builder.show();
            return true;
        }

        // JS调用和反调用
        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            if (message.equals("1")) {
                // 解析参数defaultValue
                // 调用java方法并得到结果
            }
            // 返回结果
            result.confirm("result");

            /*
            function showHtmlCallJava() {
                var ret = prompt( "1", "param1;param2" );
                // ret值即为java传回的”result”
                // 根据返回内容作相应处理
            }
            */
            return true;
        }
    }

}
