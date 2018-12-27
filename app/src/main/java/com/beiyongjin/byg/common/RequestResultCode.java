package com.beiyongjin.byg.common;

/**
 * Author: Chenming
 * E-mail: cm1@erongdu.com
 * Date: 2016/12/7 下午4:03
 * <p>
 * Description: 跳转回掉code类
 */
public class RequestResultCode {
    /** 可转让列表跳转转让 */
    public static int REQ_CAN_TRANSFER_TO_TRANSFER = 1001;
    public static int RES_CAN_TRANSFER_TO_TRANSFER = 1001;
    /** 重新绑定银行卡 */
    public static int REQ_AGAIN_BIND               = 1002;
    public static int RES_AGAIN_BIND               = 1002;
    /** 高德地图 */
    public static int REQ_GD_MAP                   = 1003;
    public static int RES_GD_MAP                   = 1003;
    /** 手机运营商认证 */
    public static int REQ_PHONE                    = 1004;
    public static int RES_PHONE                    = 1004;
    /** 找回交易密码 */
    public static int REQ_FORGOT_PAY               = 1004;
    public static int RES_FORGOT_PAY               = 1004;
    /** 找回密码 */
    public static int REQ_FORGOT                   = 1005;
    public static int RES_FORGOT                   = 1005;
    /** 芝麻信用认证 */
    public static int REQ_ZMXY                     = 1006;
    public static int RES_ZMXY                     = 1006;
    /** 工作照片 */
    public static int REQ_WORK_IMG                 = 1007;
    public static int RES_WORK_IMG                 = 1007;
    /** 公积金认证 */
    public static int REQ_ACCUMULATION_FUND        = 1008;
    public static int RES_ACCUMULATION_FUND        = 1008;
    /** 提交借款设置交易密码 */
    public static int REQ_PAY_SETTING_PWD          = 1009;
    public static int RES_PAY_SETTING_PWD          = 1009;

    /**续借或主动还款*/
    public static int RENEW_REQUEST                =1010;
    public static int RENEW_SUCCESS                =1010;
}
