package com.beiyongjin.byg.common;

/**
 * Author: Hubert
 * E-mail: hbh@erongdu.com
 * Date: 2017/3/31 下午5:02
 * <p/>
 * Description:
 */
public class FeatureConfig {
    /** 手机运营商 */
    public static final int enablemobileServerFeature = 1;
    /** 芝麻信用 */
    public static final int enablesesameFeature  = 1;
    /** 个人信息 - 商汤扫描 */
    public static final int enablescanFeature = 1;
    /** 支付宝认证 */
    public static final int enablealipayIdentifyFeature     = 0;
    /** 网页版首页 */
    public static final int enablewebHomeFeature            = 0;
    /** 还款模块 */
    public static final int enablerepaymentFeature = 1;
    /** 续借模块 */
    public static final int enablereBorrowFeature           = 0;
    /** 主动还款 银行卡展现 */
    public static final int enableactiveRepaymentFeature = 1;
    /** 主动还款 银行卡还款--连连支付 */
    public static final int enablelianlianRepaymentFeature = 1;
    /** 自动扣款 */
    public static final int enableautomaticDeductionFeature = 1;
    /** 支付宝扣款 支付宝展现 */
    public static final int enablealipayDeductionFeature = 1;
    /** 支付宝主动还款 支付宝支付 */
    public static final int enablealipayRepaymentFeature    = 0;
    /** 微信主动还款 微信支付 */
    public static final int enablewechatRepaymentFeature    = 0;
    /** 同盾模块 */
    public static final int enabletongdunModuleFeature      = 1;
    /** 公積金 */
    public static final int enableaccumulationFundFeature   = 0;
    /** 社保 */
    public static final int enablesecurityFeature           = 0;
    /** 邀请好友 */
    public static final int enableinviteFeature = 1;
    /** 渠道引流 */
    public static final int enablechannelFeature            = 0;
    /** 今日限額 */
    public static final int enabledailyLimitFeature         = 0;
    /** 数据行为分析 */
    public static final int enablebehaviorGatherFeature     = 0;
    /** 提升额度 */
    public static final int enablelimitAmountTipFeature     = 0;
    /** 配置费用详情 */
    public static final int enablefeeDetailFeature = 1;
    /*客服*/
    public static final int enableServiceDetailFeature = 1;
    public static boolean enableFeature(int feature) {
        return feature == 1;
    }
}
