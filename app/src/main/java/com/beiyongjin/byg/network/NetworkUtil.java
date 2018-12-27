package com.beiyongjin.byg.network;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;

import com.erongdu.wireless.tools.utils.ActivityManage;
import com.beiyongjin.byg.views.CutscenesProgress;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/19 16:11
 * <p/>
 * Description: 网络请求工具类
 */
public class NetworkUtil {
    private static CutscenesProgress cutscenesProgress = null;
    /** 请求返回后是否自动关闭 */
    private static boolean           automatic         = true;

    /**
     * @param title
     *         提示框的标题，如果不想要标题，传递null
     * @param content
     *         提示框的提示内容，可传递null
     * @param cancelable
     *         该dialog是否可以cancel
     */
    public static CutscenesProgress init(Context context, String title, String content, boolean cancelable, DialogInterface.OnCancelListener listener) {
        CutscenesProgress dialog = CutscenesProgress.createDialog(context);
        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        }
        if (!TextUtils.isEmpty(content)) {
            dialog.setMessage(content);
        }
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(listener);
        return dialog;
    }

    public static void showCutscenes(String title, String content) {
        showCutscenes(title, content, true, true, null);
    }

    public static void showCutscenes(String title, String content, boolean cancelable, boolean automatic) {
        showCutscenes(title, content, cancelable, automatic, null);
    }

    /**
     * 取消显示的同时，取消网络请求
     *
     * @param call
     *         retrofit2网络请求
     */
    public static void showCutscenes(final retrofit2.Call call) {
        showCutscenes(null, null, true, true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cutscenesProgress.dismiss();
                cutscenesProgress = null;
                if (null != call) {
                    call.cancel();
                }
            }
        });
    }

    /**
     * 显示加载dialog
     *
     * @param title
     *         标题
     * @param content
     *         内容
     * @param cancelable
     *         是否可以取消显示
     * @param listener
     *         取消显示后的监听
     */
    public static void showCutscenes(String title, String content, boolean cancelable, boolean automatic, DialogInterface.OnCancelListener listener) {
        try {
            NetworkUtil.automatic = automatic;
            Context context = ActivityManage.peek();
            if (null != context) {
                if (null == cutscenesProgress) {
                    cutscenesProgress = init(context, title, content, cancelable, listener);
                    cutscenesProgress.show();
                } else {
                    if (cutscenesProgress.isShowing()) {
                        return;
                    } else {
                        cutscenesProgress.show();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示加载dialog
     *
     * @param title
     *         标题
     * @param content
     *         内容
     * @param canCancel
     *         是否可以取消显示
     * @param listener
     *         取消显示后的监听
     */
    public static void showCutscenes(String title, String content, boolean canCancel, DialogInterface.OnCancelListener listener) {
        Context context = ActivityManage.peek();
        if (null != context) {
            if (null == cutscenesProgress) {
                cutscenesProgress = init(context, title, content, canCancel, listener);
                cutscenesProgress.show();
            } else {
                if (cutscenesProgress.isShowing()) {
                    return;
                } else {
                    cutscenesProgress.show();
                }
            }
        }
    }

    /**
     * 显示加载dialog
     *
     * @param listener
     *         取消显示后的监听
     */
    public static void showCutscenes(Context context, DialogInterface.OnCancelListener listener) {
        if (null != context) {
            if (null == cutscenesProgress) {
                cutscenesProgress = init(context, "", "", true, listener);
                cutscenesProgress.show();
            } else {
                if (cutscenesProgress.isShowing()) {
                    return;
                } else {
                    cutscenesProgress.show();
                }
            }
        }
    }

    /**
     * 隐藏加载dialog
     */
    public static void hideCutscenes() {
        if (null != cutscenesProgress && cutscenesProgress.isShowing() && automatic) {
            cutscenesProgress.hide();
        }
    }

    /**
     * 释放加载dialog
     */
    public static void dismissCutscenes() {
        Log.d("dismissCutscenes", "dismissCutscenes");
        if (null != cutscenesProgress && automatic) {
            Log.d("automatic", "dismissCutscenes");
            cutscenesProgress.dismiss();
            cutscenesProgress = null;
        }
    }

    /**
     * 释放加载dialog
     */
    public static void dismissCutscenes(boolean automatic) {
        NetworkUtil.automatic = automatic;
        dismissCutscenes();
    }
}
