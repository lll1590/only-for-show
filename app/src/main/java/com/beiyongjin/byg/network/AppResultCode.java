package com.beiyongjin.byg.network;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/10/31 13:53
 * <p/>
 * Description:
 */
class AppResultCode {
    /** 常规错误 */
    public static final int ERROR                 = 0x0000;
    /** 开通托管账户 */
    public static final int USER_PAYMENT          = 0x6001;
    /** 风险评测 */
    public static final int USER_RISK             = 0x6002;
    /** 绑定邮箱 */
    public static final int USER_EMAIL            = 0x6003;
    /** 提示更新 */
    public static final int UPDATE_NORMAL         = 0x7001;
    /** 强制更新 */
    public static final int UPDATE_FORCED         = 0x7002;
    /** Token过期 - APP调用RefreshToken接口 */
    public static final int TOKEN_TIMEOUT         = 411;
    /** RefreshToken过期 - APP提示需要登录，跳转到登录页面 */
    public static final int TOKEN_REFRESH_TIMEOUT = 412;
    /** Token不唯一 - APP提示被顶号，跳转到登录页面 */
    public static final int TOKEN_NOT_UNIQUE      = 413;
    /** Token不存在 - APP提示需要登录，跳转到登录页面 */
    public static final int TOKEN_NOT_EXIT        = 410;
    /** Token从其他设备登录 */
    public static final int TOKEN_NOT_OTHER_LOGIN = 400;
    /** 成功 */
    public static final int SUCCESS               = 0x9999;
    /** 账户被锁定 需要退出 */
    public static final int USER_LOCK             = 0x5001;
    /** 充值功能被冻结 */
    public static final int USER_FREEZE_RECHARGE  = 0x5002;
    /** 功能被冻结 */
    public static final int USER_FREEZE_CASH      = 0x5003;
    /** 投资功能被冻结 */
    public static final int USER_FREEZE_INVEST    = 0x5004;
    /** 变现能被冻结 */
    public static final int USER_FREEZE_REALIZE   = 0x5005;
    /** 债权功能被冻结 */
    public static final int USER_FREEZE_BOND      = 0x5006;
    /** 借款功能被冻结 */
    public static final int USER_FREEZE_LOAN      = 0x5007;
}
