package com.beiyongjin.byg.common;

import android.os.Environment;

import java.io.File;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/23 15:22
 * <p/>
 * Description:
 */
public class BaseParams {
    /** 是否使用RAP MOCK服务 */
    private static final boolean   IS_RAP            = false;
    /** RAP服务器地址 */
    private static final String    URI_AUTHORITY_RAP = "http://rap.erongdu.erongyun.net/mockjsdata/";
    /** 服务器地址 */
    public static final  String    URI_AUTHORITY     = AppConfig.IS_DEBUG ? (IS_RAP ? URI_AUTHORITY_RAP : AppConfig.URI_AUTHORITY_BETA) : AppConfig
            .URI_AUTHORITY_RELEASE;
    /** 模块名称("接口地址"会拼接在 URL 中最后的"/"之后，故URL必须以"/"结尾) */
    private static final String    URI_PATH          = "/api/";
    /** 请求地址 */
    public static final  String    URI               = URI_AUTHORITY + (IS_RAP ? "80/api/" : URI_PATH);
    //public static final  PaymentType paymentType           = PaymentType.PNR;
    /** ios传“1”，安卓传“2” */
    public static final  String    MOBILE_TYPE       = "2";
    /** 转让专区uuid */
    public static final  String    TRANSFER_ZONE     = "a31bd335e12ac0dced8849a16fd4a894";
    /** 变现uuid */
    public static final  String    REALIZATION       = "090d5d939784fe33aceff143ba1c198c";
    /**
     * 加密是需要使用的密钥
     * DES加解密时KEY必须是16进制字符串,不可小于8位
     * E.G.    6C4E60E55552386C
     * <p/>
     * 3DES加解密时KEY必须是6进制字符串,不可小于24位
     * E.G.    6C4E60E55552386C759569836DC0F83869836DC0F838C0F7
     */
    public static final  String    SECRET_KEY        = "6C4E60E55552386C759569836DC0F83869836DC0F838C0F7";
    /** 根路径 */
    public static final  String    ROOT_PATH         = getSDPath() + "/xjmd";
    /** 照片文件文件保存路径 */
    public static final  String    PHOTO_PATH        = ROOT_PATH + "/photo";
    /** 照片-活体识别 */
    public static        String    PHOTO_ALIVE       = "";
    /** 照片-活体识别byte文件 */
    public static        String    PHOTO_ALIVE_BYTE  = "";
    /** 照片-正面 */
    public static        String    PHOTO_FRONT       = "";
    /** 照片-正面byte文件 */
    public static        String    PHOTO_FRONT_BYTE  = "";
    /** 照片-反面 */
    public static        String    PHOTO_BACK        = "";
    /** 照片-反面byte文件 */
    public static        String    PHOTO_BACK_BYTE   = "";
    /** 照片-头像 */
    public static        String    PHOTO_AVATAR      = "";
    /** SP文件名 */
    public static final  String    SP_NAME           = "basic_params";
    /** 数据库名称 */
    public static final  String    DATABASE_NAME     = "stanley_db";
    /** 发送验证码的短信平台号 */
    public static final  String    SMS_SENDER        = "";
    /** 活体识别 */
    public static final  String    ALIVE_APPID       = "57c8176b8f1d4819b0e1c1c3f09fb8f9";
    public static final  String    ALIVE_SECRET      = "d208b9248b034387a017231cca030682";
    /** 人证列表展示类别 下标 0个人信息 1工作信息 2紧急联系人 3银行卡 4芝麻授信 5 手机运营商 6社保 7公積金 8工作信息  9支付宝人证 10更多信息 */
    public static final  boolean[] CREDIT_TYPE       =
            new boolean[]{CreditTypeFlag.PERSON_FLAG, CreditTypeFlag.BANK_FLAG, CreditTypeFlag.LINKER_FLAG,
                    CreditTypeFlag.PHONE_FLAG, CreditTypeFlag.ZMXY_FLAG, CreditTypeFlag.SECURITY_FLAG,
                    CreditTypeFlag.FUND_FLAG, CreditTypeFlag.WORK_FLAG, CreditTypeFlag.ZFB_FLAG,
                    CreditTypeFlag.MORE_FLAG};
    /** 认证列表展示是否必填类别 下标 0个人信息 1工作信息 2紧急联系人 3银行卡 4芝麻授信 5 手机运营商 6社保 7公積金 8工作信息 9支付宝人证 10更多信息 */
    public static final  boolean[] CREDIT_MUST       =
            new boolean[]{CreditTypeFlag.PERSON_MUST_FLAG, CreditTypeFlag.BANK_MUST_FLAG, CreditTypeFlag.LINKER_MUST_FLAG,
                    CreditTypeFlag.PHONE_MUST_FLAG, CreditTypeFlag.ZMXY_MUST_FLAG, CreditTypeFlag.SECURITY_MUST_FLAG,
                    CreditTypeFlag.FUND_MUST_FLAG, CreditTypeFlag.WORK_MUST_FLAG, CreditTypeFlag.ZFB_MUST_FLAG,
                    CreditTypeFlag.MORE_MUST_FLAG};
    //是否为运营商人证
    public static        boolean   isPhoneState      = false;
    public static        String    phoneStateUrl     = "";
    //连连签约接口
    public static        String    authSign          = "act/mine/bankCard/authSign.htm";

    /**
     * 获取SD卡的根目录
     */
    public static String getSDPath() {
        File sdDir = null;
        // 判断sd卡是否存在
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            // 获取跟目录
            sdDir = Environment.getExternalStorageDirectory();
        }
        if (sdDir == null) {
            return "";
        } else {
            return sdDir.toString();
        }
    }
}
