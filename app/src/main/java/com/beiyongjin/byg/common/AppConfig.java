package com.beiyongjin.byg.common;

/**
 * Author: Hubert
 * E-mail: hbh@erongdu.com
 * Date: 2017/3/31 下午8:08
 * <p/>
 * Description:
 */
public class AppConfig {
    /** 是否是debug模式 */
    public static final boolean IS_DEBUG = true;
    /** 测试服务器地址 */
//    public static final String  URI_AUTHORITY_BETA = "https://web.beiyj.com/";    //测试服务器 http://demo.cashloan-web.rdtest.cn/
    //public static final String  URI_AUTHORITY_BETA = "https://web.beiyj.com/";       //林时凯
//    public static final String  URI_AUTHORITY_BETA = "https://web.beiyj.com/";       //杨雷
    //public static final String  URI_AUTHORITY_BETA = "https://web.beiyj.com/";
    //public static final String  URI_AUTHORITY_BETA = "https://web.beiyj.com/";         //高才
    //public static final String  URI_AUTHORITY_BETA = "https://web.beiyj.com/";        //徐星
    //public static final String  URI_AUTHORITY_BETA = "https://web.beiyj.com/";       //丹丹
    //public static final String  URI_AUTHORITY_BETA = "https://web.beiyj.com/";    //蔡婷婷
//    public static final String  URI_AUTHORITY_BETA = "https://web.beiyj.com/";       //蒋业辉好信
//    public static final  String  URI_AUTHORITY_BETA = "https://web.beiyj.com/";
//    public static final String  URI_AUTHORITY_BETA = "https://web.beiyj.com/";
//    public static final String  URI_AUTHORITY_BETA = "https://web.beiyj.com/";
//    public static final String  URI_AUTHORITY_BETA = "https://web.beiyj.com/";//袋鼠
//    public static final String  URI_AUTHORITY_BETA = "https://web.beiyj.com/";
//    public static final String  URI_AUTHORITY_BETA = "https://web.beiyj.com/";//小额宝
//public static final String  URI_AUTHORITY_BETA = "https://web.beiyj.com/";

  //测试环境
  public static final String  URI_AUTHORITY_BETA = "https://web.beiyj.com/";
//    public static final String  URI_AUTHORITY_BETA = "https://web.beiyj.com/";//贷不贷
    /** 正式服务器地址 */
    public static final String  URI_AUTHORITY_RELEASE = "https://web.beiyj.com/";
    /** app_key */
//    public static final String  APP_KEY = "89251DFF41316A11184CB7ED3E8ADFCE";
//    public static final String  APP_KEY = "89251DFF41316A11184CB7ED3E8ADFCE";//好信
//    public static final String  APP_KEY = "89251DFF41316A11184CB7ED3E8ADFCE";
//        public static final String  APP_KEY = "89251DFF41316A11184CB7ED3E8ADFCE";//袋鼠
    public static final String  APP_KEY = "89251DFF41316A11184CB7ED3E8ADFCE";//小额宝
//public static final String  APP_KEY = "89251DFF41316A11184CB7ED3E8ADFCE";//贷不贷
    /** app_secret */
    public static final String  APP_SECRET = "D0BFDF5B8F9A93F94EF8BFCD145FC9D8";
    /** friday 数据行为分析key */
    public static final String  BEHAVIOR_GATHER_KEY = "";
    /** 微信分享Key **/
    public final static String  WX_APP_ID = "wx02eaf6d88ca3c5f2";
    public final static String  WX_APP_SECRET = "366cb9dce0912c048ea34b7bb27a0f29";
    /** 小视账号密码 **/
    public static final String  REGARD_ACCOUNT = "";
    public static final String  REGARD_PASSWORD = "";
    /** QQKey **/
    public static final String  QQ_ID = "1106450616";
    public static final String  QQ_APP_KEY = "gT8FaSEIp0EjUlSd";
    /** 新浪分享Key **/
    public static final String  SINA_APP_ID = "";
    public static final String  SINA_APP_SECRET = "";
    /** 引导页数量 */
    public static final int     GUIDE_COUNT = 3;
    /**
     * 首页下标
     * 1 展现认证数
     * 2 展现借款数
     * 3 天数按钮展示
     * 4 闪现
     */
    public static final int     HOME_INDEX_NUM = 4;
    /**
     * 认证中心下标
     * 1列表
     * 2九宫格
     * 3选题 必填区分
     */
    public static final int     CREDIT_CENTER_INDEX_NUM = 1;
    /**
     * 人像识别方案
     * 1商汤
     * 2face++
     * 3小视
     */
    public static final int     IDENTIFICATION_TYPE = 1;
    /**
     * 紧急联系人个数
     */
    public static final int     LINKER_NUMBER = 2;
}
