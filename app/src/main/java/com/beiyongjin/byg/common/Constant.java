package com.beiyongjin.byg.common;

import com.beiyongjin.byg.BuildConfig;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/18 16:23
 * <p/>
 * Description: 常量类
 */
public class Constant {
    /** number */
    public static final int    NUMBER__2         = -2;
    public static final int    NUMBER__1         = -1;
    public static final int    NUMBER_0          = 0;
    public static final int    NUMBER_1          = 1;
    public static final int    NUMBER_2          = 2;
    public static final int    NUMBER_3          = 3;
    public static final int    NUMBER_4          = 4;
    public static final int    NUMBER_5          = 5;
    public static final int    NUMBER_6          = 6;
    public static final int    NUMBER_7          = 7;
    public static final int    NUMBER_8          = 8;
    public static final int    NUMBER_9          = 9;
    public static final int    NUMBER_10         = 10;
    public static final int    NUMBER_20         = 20;
    public static final int    SEEK_MAX         = 1000;
    /** status */
    public static final String STATUS__2         = "-2";
    public static final String STATUS__1         = "-1";
    public static final String STATUS_0          = "0";
    public static final String STATUS_1          = "1";
    public static final String STATUS_2          = "2";
    public static final String STATUS_3          = "3";
    public static final String STATUS_4          = "4";
    public static final String STATUS_5          = "5";
    public static final String STATUS_6          = "6";
    public static final String STATUS_7          = "7";
    public static final String STATUS_8          = "8";
    public static final String STATUS_9          = "9";
    public static final String STATUS_10         = "10";
    public static final String STATUS_20         = "20";
    public static final String STATUS_21         = "21";
    public static final String STATUS_22         = "22";
    public static final String STATUS_23         = "23";
    public static final String STATUS_24         = "24";
    public static final String STATUS_25         = "25";
    public static final String STATUS_26         = "26";
    public static final String STATUS_27         = "27";
    public static final String STATUS_28         = "28";
    public static final String STATUS_29         = "29";
    public static final String STATUS_30         = "30";
    public static final String STATUS_40         = "40";
    public static final String STATUS_50         = "50";
    /** 符号 */
    public static final String SYMBOL_PLUS       = "+";
    public static final String SYMBOL_MINUS      = "-";
    /** true or false */
    public static final String TRUE              = "true";
    public static final String FALSE             = "false";
    /** network params */
    // 公共参数
    public static final String APP_KEY           = "appkey";
    public static final String SIGNA             = "signMsg";
    public static final String TS                = "ts";
    public static final String MOBILE_TYPE       = "mobileType";
    public static final String VERSION_NUMBER    = "versionNumber";
    // SP 字段
    public static final String IS_LAND           = "isLand";
    public static final String IS_FIRST_IN       = "isFirstIn";
    public static final String IS_GESTURE_OPENED = "isGestureOpened";
    public static final String IS_PAY_SETTING    = "isPaySetting";
    // 登录参数
    public static final String TOKEN             = "token";
    public static final String REFRESH_TOKEN     = "refreshToken";
    public static final String USER_ID           = "userId";
    public final static boolean DEBUG = !BuildConfig.isRelease;
    //adb command
    public final static String ADB_LOGCAT = "logcat *:e *:w | grep \"(" + android.os.Process.myPid() + ")\"";
    //白骑士商户名称
    public final static String BaiQiShiProduct="haitoubao";
    //白骑士的token值
    public final static String BaiQiShiToken="";
    //客服电话
    public final static String ServiceTelPhone="027-87835785";
}
