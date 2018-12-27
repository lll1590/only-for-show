package com.beiyongjin.byg.module.home.viewControl;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.erongdu.wireless.info.SharedInfo;
import com.erongdu.wireless.network.entity.HttpResult;
import com.erongdu.wireless.network.entity.ListData;
import com.erongdu.wireless.tools.log.Logger;
import com.erongdu.wireless.tools.utils.TextUtil;
import com.github.mzule.activityrouter.router.Routers;
import com.beiyongjin.byg.MainAct;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.BaseParams;
import com.beiyongjin.byg.common.BundleKeys;
import com.beiyongjin.byg.common.CommonType;
import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.common.RequestResultCode;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.common.SwipeListener;
import com.beiyongjin.byg.common.ui.BaseRecyclerViewCtrl;
import com.beiyongjin.byg.databinding.HomeHtmlFragBinding;
import com.beiyongjin.byg.module.mine.dataModel.recive.CommonRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.InfoRec;
import com.beiyongjin.byg.network.RequestCallBack;
import com.beiyongjin.byg.network.api.CommonService;
import com.beiyongjin.byg.network.api.MineService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.beiyongjin.byg.network.RDClient.getService;

/**
 * Author: 沈志华
 * E-mail: shenzh@erongdu.com
 * Date: 2017/9/6$ 9:48$
 * {@link com.beiyongjin.byg.module.home.ui.fragment.HomeHtmlFrag}
 * <p/>
 */
public class HomeHtmlCtrl extends BaseRecyclerViewCtrl {
    private Handler mHandler = new Handler();
    private HomeHtmlFragBinding binding;
    private MainAct             mainAct;
    private CommonRec           rec;
    private InfoRec             inforec;
    private String              inputUrl;

    public HomeHtmlCtrl(final HomeHtmlFragBinding binding, MainAct mainAct) {
        this.binding = binding;
        this.mainAct = mainAct;
        inputUrl = (String) SharedInfo.getInstance().getValue("input_url", "");
        if (TextUtil.isEmpty(inputUrl)) {
            inputUrl = BaseParams.URI + "/app/index.htm";
        } else {
            inputUrl = "http://" + inputUrl + "/api/app/index.htm";
        }
        WebSettings setting = binding.webView.getSettings();
        listener.set(new SwipeListener() {
            @Override
            public void swipeInit(SwipeToLoadLayout swipeLayout) {
                setSwipeLayout(swipeLayout);
                swipeLayout.setLoadMoreEnabled(false);
            }

            @Override
            public void refresh() {
                binding.webView.loadUrl(inputUrl);
                getSwipeLayout().setRefreshing(false);
            }

            @Override
            public void loadMore() {
            }
        });
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
        binding.webView.addJavascriptInterface(new WebReturn(), "webReturn");
        binding.webView.getSettings().setUserAgentString("Android");

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            dealJavascriptLeak(webView);
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        binding.webView.setWebViewClient(new MyWebViewClient());
        binding.webView.setWebChromeClient(new MyWebChromeClient());
        req_url();
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
                    mainAct.setResult(RequestResultCode.RES_ZMXY, intent);
                    mainAct.finish();
                    mainAct = null;
                }
            });
        }

        @JavascriptInterface
        public void loanNow(final char s) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Logger.i("web", "11111");
                    mainAct.setTab(1);
                }
            });
        }

        @JavascriptInterface
        public void aboutUs(final char s) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (rec != null) {
                        Routers.open(mainAct, RouterUrl.getRouterUrl(String.format(RouterUrl.AppCommon_WebView, rec.getName(), CommonType.getUrl(rec
                                .getValue()), "")));
                    }
                }
            });
        }

        @JavascriptInterface
        public void inviteFriends(final char s) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if ((boolean) SharedInfo.getInstance().getValue(Constant.IS_LAND, false)) {
                        request();
                    } else {
                        Routers.open(mainAct, RouterUrl.getRouterUrl(RouterUrl.UserInfoManage_Login));
                    }
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
                mainAct.setResult(RequestResultCode.RES_PHONE);
                mainAct.finish();
            }
            /*if (url.contains("api/actzm/mine/zhima/AuthCallBack.htm")) {
                activity.setResult(RequestResultCode.RES_ZMXY);
                activity.finish();
            }*/
            if (url.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse(url));
                mainAct.startActivity(intent);
            } else if (url.startsWith("sms:")) {
                Intent   intent = new Intent(Intent.ACTION_SENDTO);
                String[] sms    = url.split("\\?");
                intent.setType("vnd.android-dir/mms-sms");
                intent.setData(Uri.parse(sms[0]));
                if (sms.length >= 2) {
                    String[] body = sms[1].split("=");
                    intent.putExtra("sms_body", body[1]);
                }
                mainAct.startActivity(intent);
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

    private void req_url() {
        Call<HttpResult<ListData<CommonRec>>> call = getService(CommonService.class).h5List();
        call.enqueue(new RequestCallBack<HttpResult<ListData<CommonRec>>>() {
            @Override
            public void onSuccess(Call<HttpResult<ListData<CommonRec>>> call, Response<HttpResult<ListData<CommonRec>>> response) {
                List<CommonRec> list = response.body().getData().getList();
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (CommonType.HELP.equals(list.get(i).getCode())) {
                            rec = list.get(i);
                            return;
                        }
                    }
                }
            }
        });
    }

    public void request() {
        Call<HttpResult<InfoRec>> call = getService(MineService.class).getInfo();
        call.enqueue(new RequestCallBack<HttpResult<InfoRec>>() {
            @Override
            public void onSuccess(Call<HttpResult<InfoRec>> call, Response<HttpResult<InfoRec>> response) {
                if (null != response.body() && null != response.body().getData()) {
                    inforec = response.body().getData();
                    Routers.open(mainAct, RouterUrl.getRouterUrl(String.format(RouterUrl.Mine_Invite, inforec.getProfitRate())));
                }
            }
        });
    }
}
