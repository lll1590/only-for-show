package com.beiyongjin.byg.common;

import com.beiyongjin.byg.R;

/**
 * Author: Chenming
 * E-mail: cm1@erongdu.com
 * Date: 2017/2/16 下午2:00
 * <p/>
 * Description: 授信类型标示
 */
public class CreditTypeFlag {
    /** 个人信息是否需要展示 */
    public static boolean PERSON_FLAG        = FeatureConfig.enableFeature(FeatureConfig.enablescanFeature);
    /** 工作信息是否需要展示 */
    public static boolean WORK_FLAG          = true;
    /** 联系人信息是否需要展示 */
    public static boolean LINKER_FLAG        = true;
    /** 银行卡是否需要展示 */
    public static boolean BANK_FLAG          = true;
    /** 芝麻信用是否需要展示 */
    public static boolean ZMXY_FLAG          = FeatureConfig.enableFeature(FeatureConfig.enablesesameFeature);
    /** 公積金是否需要展示 */
    public static boolean FUND_FLAG          = FeatureConfig.enableFeature(FeatureConfig.enableaccumulationFundFeature);
    /** 社保是否需要展示 */
    public static boolean SECURITY_FLAG      = FeatureConfig.enableFeature(FeatureConfig.enablesecurityFeature);
    /** 手机运营商是否需要展示 */
    public static boolean PHONE_FLAG         = FeatureConfig.enableFeature(FeatureConfig.enablemobileServerFeature);
    /** 支付宝是否需要展示 */
    public static boolean ZFB_FLAG           = FeatureConfig.enableFeature(FeatureConfig.enablealipayIdentifyFeature);
    /** 更多信息是否需要展示 */
    public static boolean MORE_FLAG          = true;
    /** 个人信息是否必填 */
    public static boolean PERSON_MUST_FLAG   = true;
    /** 工作信息是否必填 */
    public static boolean WORK_MUST_FLAG     = false;
    /** 联系人信息是否必填 */
    public static boolean LINKER_MUST_FLAG   = true;
    /** 银行卡是否必填 */
    public static boolean BANK_MUST_FLAG     = true;
    /** 芝麻信用是否必填 */
    public static boolean ZMXY_MUST_FLAG     = true;
    /** 公积金是否必填 */
    public static boolean FUND_MUST_FLAG     = false;
    /** 社保是否必填 */
    public static boolean SECURITY_MUST_FLAG = false;
    /** 手机运营商是否必填 */
    public static boolean PHONE_MUST_FLAG    = true;
    /** 支付宝是否必填 */
    public static boolean ZFB_MUST_FLAG      = false;
    /** 更多信息是否必填 */
    public static boolean MORE_MUST_FLAG     = false;
    /** iconFont */
    public static int[]   CREDIT_ICON_FONT   = new int[]{R.string.iconfont_person_msg, R.string.iconfont_linker,
            R.string.iconfont_recive_bank, R.string.iconfont_phone_state,
            R.string.iconfont_zhimaxinyong, R.string.iconfont_social_security,
            R.string.iconfont_accumulation_fund,R.string.iconfont_work_msg,
            R.string.iconfont_zhifubao, R.string.iconfont_more_msg};
}

