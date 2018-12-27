package com.beiyongjin.byg.common;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/12/6 18:06
 * <p>
 * Description: 路由表
 */
@SuppressWarnings("unused")
public class RouterUrl {
    private static final String URI_SCHEME = "wolverine:/";

    /**
     * 获得请求URI
     */
    public static String getRouterUrl(String host) {
        return URI_SCHEME + host;
    }

    /****************************************************************************************************************/
    /******************************************* appCommon router url ***********************************************/
    /****************************************************************************************************************/

    // 主界面 - 声明
    public static final String AppCommon_IMain          = "appCommon/main";
    // 主界面 - 调用(参数：type：0 - 首页，1 - 理财，2 - 资产，3 - 我)
    public static final String AppCommon_Main           = AppCommon_IMain + "?type=%1$s";
    // 启动页
    public static final String AppCommon_Splash         = "appCommon/splash";
    // 引导页
    public static final String AppCommon_Guide          = "appCommon/guide";
    // WebView 页面 - 声明
    public static final String AppCommon_IWebView       = "appCommon/webView";
    // WebView 页面 - 调用(参数：title - 标题，url - 请求地址，)
    public static final String AppCommon_WebView        = AppCommon_IWebView + "?title=%1$s&url=%2$s&postData=%3$s";
    // 设置手势密码
    public static final String AppCommon_GestureLock    = "appCommon/gestureLock";
    // 解锁手势密码 - 声明
    public static final String AppCommon_IGestureUnlock = "appCommon/gestureUnlock";
    // 解锁手势密码 - 调用(参数：type：true - 修改手势密码，false - 解锁手势密码)
    public static final String AppCommon_GestureUnlock  = AppCommon_IGestureUnlock + "?type=%1$s";
    public static String Mine_RealnameSucceed;

    /****************************************************************************************************************/
    /******************************************* userInfoManage router url ******************************************/
    /****************************************************************************************************************/
    // 登录
    public static final String UserInfoManage_ILogin          = "userInfoManage/login";
    // 登录
    public static final String UserInfoManage_Login           = UserInfoManage_ILogin + "?type=%1$s";
    // 注册
    public static final String UserInfoManage_IRegister       = "userInfoManage/register";
    // 注册
    public static final String UserInfoManage_Register        = UserInfoManage_IRegister + "?id=%1$s";
    // 注册成功
    public static final String UserInfoManage_RegisterSucceed = "userInfoManage/registerSucceed";
    // 忘记密码 - 声明
    public static final String UserInfoManage_IForgotPwd      = "userInfoManage/forgotPwd";
    // 忘记密码- 调用(参数：id - 手机号)
    public static final String UserInfoManage_ForgotPwd       = UserInfoManage_IForgotPwd + "?id=%1$s&type=%2$s";
    // 忘记交易密码
    public static final String UserInfoManage_IForgotPayPwd   = "userInfoManage/forgotPayPwd";
    // 忘记交易密码
    public static final String UserInfoManage_ForgotPayPwd    = UserInfoManage_IForgotPayPwd + "?type=%1$s";
    // 重置密码页面 - 声明
    public static final String UserInfoManage_IResetPwd       = "userInfoManage/resetPwd";
    // 重置密码页面 - 调用(参数：id - 手机号)
    public static final String UserInfoManage_ResetPwd        = UserInfoManage_IResetPwd + "?id=%1$s&sid=%2$s";
    // 主界面 “我”
    public static final String UserInfoManage_UserHomePage    = "userInfoManage/userHomePage";

    /****************************************************************************************************************/
    /******************************************* Loan router url ******************************************/
    /****************************************************************************************************************/
    //借款详情 - 声明
    public static final String Loan_IDetails                  = "loan/details";
    //借款详情 - 调用
    public static final String Loan_Details                   = Loan_IDetails +
            "?loanMoney=%1$s&loanLimit=%2$s&realMoney=%3$s&fee=%4$s&cardName=%5$s&cardNo=%6$s&cardId=%7$s";
    /****************************************************************************************************************/
    /******************************************* Repay router url ******************************************/
    /****************************************************************************************************************/

    // 还款详情--声明
    public static final String Repay_IDetails                 = "repay/details";
    // 还款详情--调用
    public static final String Repay_Details                  = Repay_IDetails + "?id=%1$s&type=%2$s";
    // 还款方式
    public static final String Repay_Type                     = "repay/type?id=%1$s";
    // 还款方式 - 自动还款
    public static final String Repay_Auto                     = "repay/auto";
    // 还款方式 - 声明
    public static final String Repay_IAccount                 = "repay/account";
    // 还款方式 - 调用
    public static final String Repay_Account                  = Repay_IAccount + "?type=%1$s";
    /****************************************************************************************************************/
    /************************************************ Renew router url ***********************************************/
    /****************************************************************************************************************/
    //续借
    public static final String Renew                          ="renew?id=%1$s&type=%2$s";
    //续借列表
    public static final String RenewRecord                          ="renew/record?id=%1$s";
    /****************************************************************************************************************/
    /************************************************ Mine router url ***********************************************/
    /****************************************************************************************************************/
    // 账户与安全
    public static final String Mine_AccountSecurity           = "mine/accountSecurity";
    // 设置
    public static final String Mine_Settings                  = "mine/settings";
    // 意见反馈
    public static final String Mine_Settings_Idea             = "mine/settings/idea";
    // 修改密码
    public static final String Mine_Settings_Update           = "mine/settings/update";
    // 设置/修改支付密码
    public static final String Mine_Settings_IPay_Pwd         = "mine/settings/payPwd";
    // 设置/修改支付密码
    public static final String Mine_Settings_Pay_Pwd          = Mine_Settings_IPay_Pwd + "?type=%1$s";
    // 认证中心
    public static final String Mine_CreditCenter              = "mine/creditCenter";
    // 认证中心
    public static final String Mine_CreditTwoCenter           = "mine/creditTwoCenter";
    // 认证中心
    public static final String Mine_ICreditThreeCenter        = "mine/creditThreeCenter";
    // 认证中心
    public static final String Mine_CreditThreeCenter         = Mine_ICreditThreeCenter + "?type=%1$s";
    //我的邀请码
    public static final String Mine_IInvite                   = "mine/Invite";
    //我的邀请码
    public static final String Mine_Invite                    = Mine_IInvite + "?rate=%1$s";
    //邀请好友
    public static final String Mine_Invite_Friend             = "mine/InviteFriend";
    //邀请记录
    public static final String Mine_Invite_Record             = "mine/InviteRecord";
    //二级邀请记录 - 声明
    public static final String Mine_Invite_Second_IRecord     = "mine/InviteSecondRecord";
    //二级邀请记录 - 声明
    public static final String Mine_Invite_Second_Record      = Mine_Invite_Second_IRecord + "?id=%1$s";
    //我的奖金 - 声明
    public static final String Mine_Invite_IBonus             = "mine/InviteBonus";
    //我的奖金 - 调用
    public static final String Mine_Invite_Bonus              = Mine_Invite_IBonus + "?phone=%1$s";
    //提现记录
    public static final String Mine_Invite_Withdraw           = "mine/withdraw";
    //奖励明细
    public static final String Mine_Invite_Award              = "mine/InviteAward";
    // 借款记录
    public static final String Mine_LendRecord                = "mine/lendRecord";
    // 个人信息(1) - 申明
    public static final String Mine_ICreditPerson             = "mine/creditPerson";
    // 个人信息(1) - 调用
    public static final String Mine_CreditPerson              = Mine_ICreditPerson + "?state=%1$s";
    // 个人信息(2) - 申明
    public static final String Mine_ICreditPersonTwo          = "mine/creditPersonTwo";
    // 个人信息(2) - 调用
    public static final String Mine_CreditPersonTwo           = Mine_ICreditPersonTwo + "?state=%1$s";
    // 个人信息(3) - 申明
    public static final String Mine_ICreditPersonThree        = "mine/creditPersonThree";
    // 个人信息(3) - 调用
    public static final String Mine_CreditPersonThree         = Mine_ICreditPersonThree + "?state=%1$s";
    // 工作信息 - 申明
    public static final String Mine_ICreditWork               = "mine/creditWork";
    // 工作信息 - 调用
    public static final String Mine_CreditWork                = Mine_ICreditWork + "?state=%1$s";
    // 工作照片
    public static final String Mine_CreditWorkPhoto           = "mine/creditWorkPhoto";
    // 银行卡信息 - 申明
    public static final String Mine_ICreditBank               = "mine/creditBank";
    // 银行卡信息 - 调用
    public static final String Mine_CreditBank                = Mine_ICreditBank + "?type=%1$s";
    // 已绑定银行卡信息
    public static final String Mine_CreditBindBank            = "mine/creditBindBank";
    // 联系人信息 - 申明
    public static final String Mine_ICreditLinker             = "mine/creditLinker";
    // 联系人信息 - 调用
    public static final String Mine_CreditLinker              = Mine_ICreditLinker + "?state=%1$s";
    // 芝麻信用信息 - 申明
    public static final String Mine_ICreditZmxy               = "mine/creditZmxy";
    // 芝麻信用信息 - 调用
    public static final String Mine_CreditZmxy                = Mine_ICreditZmxy + "?state=%1$s";
    // 运营商信息 - 申明
    public static final String Mine_ICreditPhone              = "mine/creditPhone";
    // 运营商信息 - 调用
    public static final String Mine_CreditPhone               = Mine_ICreditPhone + "?state=%1$s";
    // 公积金信息 - 申明
    public static final String Mine_ICreditAccumulation       = "mine/creditAccumulation";
    // 公积金信息 - 调用
    public static final String Mine_CreditAccumulation        = Mine_ICreditAccumulation + "?state=%1$s";
    // 运营商信息 - 申明
    public static final String Mine_ICreditMore               = "mine/creditMore";
    // 运营商信息 - 调用
    public static final String Mine_CreditMore                = Mine_ICreditMore + "?state=%1$s";
    // 高德地图
    public static final String Mine_GdMap                     = "mine/GdMap";
    // 消息中心
    public static final String Mine_MsgCenter                 = "mine/msgCenter";
    //客服的微信中心
    public static final String Mine_ServiceWeChat=              "mine/servicewechat";
}
