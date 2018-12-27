package com.beiyongjin.byg.router;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/6/1 18:00
 * <p/>
 * Description:
 */
public class RouterUrl {
    private static final String SCHEME                  = "/wolverine/";
    /** 自定义全局降级策略 */
    public static final  String SERVICE_DEGRADE         = SCHEME + "service/degrade";
    /** 重写跳转URL */
    public static final  String SERVICE_PATH_REPLACE    = SCHEME + "service/pathReplace";
    /** 传递自定义对象 */
    public static final  String SERVICE_JSON            = SCHEME + "service/json";
    /** 主页 */
    ///////////////////////////////////////////////////////////////////////////
    // COMMON
    ///////////////////////////////////////////////////////////////////////////
    /****************************************************************************************************************/
    /******************************************* appCommon router url ***********************************************/
    /****************************************************************************************************************/

    // 主界面 - 声明(参数：type：0 - 首页，1 - 理财，2 - 资产，3 - 我)
    public static final  String AppCommon_Main          = SCHEME + "main";
    // 启动页
    public static final  String AppCommon_Splash        = SCHEME + "appCommon/splash";
    // 引导页
    public static final  String AppCommon_Guide         = SCHEME + "appCommon/guide";
    // WebView 页面 - 声明
    public static final  String AppCommon_WebView       = SCHEME + "appCommon/webView";
    // 设置手势密码
    public static final  String AppCommon_GestureLock   = SCHEME + "appCommon/gestureLock";
    // 解锁手势密码 - 声明
    public static final  String AppCommon_GestureUnlock = SCHEME + "appCommon/gestureUnlock";
    public static String Mine_RealnameSucceed;

    /****************************************************************************************************************/
    /******************************************* userInfoManage router url ******************************************/
    /****************************************************************************************************************/
    // 登录
    public static final String UserInfoManage_Login           = SCHEME + "userInfoManage/login";
    // 注册
    public static final String UserInfoManage_Register        = SCHEME + "userInfoManage/register";
    // 注册成功
    public static final String UserInfoManage_RegisterSucceed = SCHEME + "userInfoManage/registerSucceed";
    // 忘记密码 - 声明
    public static final String UserInfoManage_ForgotPwd       = SCHEME + "userInfoManage/forgotPwd";
    // 忘记交易密码
    public static final String UserInfoManage_ForgotPayPwd    = SCHEME + "userInfoManage/forgotPayPwd";
    // 重置密码页面 - 声明
    public static final String UserInfoManage_ResetPwd        = SCHEME + "userInfoManage/resetPwd";

    /****************************************************************************************************************/
    /******************************************* Loan router url ******************************************/
    /****************************************************************************************************************/
    //还款详情 - 声明
    public static final String Loan_Details                   = SCHEME + "loan/details";
    /****************************************************************************************************************/
    /******************************************* Repay router url ******************************************/
    /****************************************************************************************************************/

    // 还款详情--声明
    public static final String Repay_Details                  = SCHEME + "repay/details";
    // 还款方式
    public static final String Repay_Type                     = SCHEME + "repay/type";
    // 还款方式 - 自动还款
    public static final String Repay_Auto                     = SCHEME + "repay/auto";
    // 还款方式 - 声明
    public static final String Repay_Account                  = SCHEME + "repay/account";

    /****************************************************************************************************************/
    /************************************************ Mine router url ***********************************************/
    /****************************************************************************************************************/
    // 消息中心 - 调用
    public static final String Mine_MsgCenter                 = SCHEME + "mine/msgCenter";
    // 设置
    public static final String Mine_Settings                  = SCHEME + "mine/settings";
    // 意见反馈
    public static final String Mine_Settings_Idea             = SCHEME + "mine/settings/idea";
    // 修改密码
    public static final String Mine_Settings_Update           = SCHEME + "mine/settings/update";
    // 设置/修改支付密码
    public static final String Mine_Settings_Pay_Pwd          = SCHEME + "mine/settings/payPwd";
    // 认证中心
    public static final String Mine_CreditCenter              = SCHEME + "mine/creditCenter";
    // 认证中心
    public static final String Mine_CreditTwoCenter           = SCHEME + "mine/creditTwoCenter";
    // 认证中心
    public static final String Mine_CreditThreeCenter         = SCHEME + "mine/creditThreeCenter";
    //我的邀请码
    public static final String Mine_Invite                    = SCHEME + "mine/Invite";
    //邀请好友
    public static final String Mine_Invite_Friend             = SCHEME + "mine/InviteFriend";
    //邀请记录
    public static final String Mine_Invite_Record             = SCHEME + "mine/InviteRecord";
    //二级邀请记录 - 声明
    public static final String Mine_Invite_Second_Record      = SCHEME + "mine/InviteSecondRecord";
    //我的奖金 - 声明
    public static final String Mine_Invite_Bonus              = SCHEME + "mine/InviteBonus";
    //提现记录
    public static final String Mine_Invite_Withdraw           = SCHEME + "mine/withdraw";
    //奖励明细
    public static final String Mine_Invite_Award              = SCHEME + "mine/InviteAward";
    // 借款记录
    public static final String Mine_LendRecord                = SCHEME + "mine/lendRecord";
    // 个人信息(1) - 申明
    public static final String Mine_CreditPerson              = SCHEME + "mine/creditPerson";
    // 个人信息(2) - 申明
    public static final String Mine_CreditPersonTwo           = SCHEME + "mine/creditPersonTwo";
    // 个人信息(3) - 申明
    public static final String Mine_CreditPersonThree         = SCHEME + "mine/creditPersonThree";
    // 工作信息 - 申明
    public static final String Mine_CreditWork                = SCHEME + "mine/creditWork";
    // 工作照片
    public static final String Mine_CreditWorkPhoto           = SCHEME + "mine/creditWorkPhoto";
    // 银行卡信息 - 申明
    public static final String Mine_CreditBank                = SCHEME + "mine/creditBank";
    // 已绑定银行卡信息
    public static final String Mine_CreditBindBank            = SCHEME + "mine/creditBindBank";
    // 联系人信息 - 申明
    public static final String Mine_CreditLinker              = SCHEME + "mine/creditLinker";
    // 芝麻信用信息 - 申明
    public static final String Mine_CreditZmxy                = SCHEME + "mine/creditZmxy";
    // 运营商信息 - 申明
    public static final String Mine_CreditPhone               = SCHEME + "mine/creditPhone";
    // 公积金信息 - 申明
    public static final String Mine_CreditAccumulation        = SCHEME + "mine/creditAccumulation";
    // 运营商信息 - 申明
    public static final String Mine_CreditMore                = SCHEME + "mine/creditMore";
    // 高德地图
    public static final String Mine_GdMap                     = SCHEME + "mine/GdMap";
}
