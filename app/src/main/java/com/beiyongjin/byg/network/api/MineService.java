package com.beiyongjin.byg.network.api;

import com.erongdu.wireless.network.entity.HttpResult;
import com.erongdu.wireless.network.entity.ListData;
import com.erongdu.wireless.network.entity.PageMo;
import com.beiyongjin.byg.common.BundleKeys;
import com.beiyongjin.byg.module.mine.dataModel.recive.BankRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.CreditBankRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.CreditImgRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.CreditLinkerRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.CreditMoreRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.CreditPersonRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.CreditStatusRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.CreditUrlRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.CreditWorkInfoRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.CreditZmxyRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.DicRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.FaceOcrRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.IdCardTimeRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.InfoRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.InviteAwardItemRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.InviteBonusRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.InviteRecordItemRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.InviteWithdrawItemRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.MineInviteRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.OcrMsgRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.PassRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.RegardOcrMsgRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.ShareLinkRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.TradeStateRec;
import com.beiyongjin.byg.module.mine.dataModel.submit.AuthSignSub;
import com.beiyongjin.byg.module.mine.dataModel.submit.CreditBankSub;
import com.beiyongjin.byg.module.mine.dataModel.submit.CreditLinkerSub;
import com.beiyongjin.byg.module.mine.dataModel.submit.CreditMoreSub;
import com.beiyongjin.byg.module.mine.dataModel.submit.CreditWorkSub;
import com.beiyongjin.byg.module.mine.dataModel.submit.IdCardSyncSub;
import com.beiyongjin.byg.module.mine.dataModel.submit.IdeaSub;
import com.beiyongjin.byg.module.mine.dataModel.submit.PhoneInfoSub;
import com.beiyongjin.byg.module.mine.dataModel.submit.UpdatePwdSub;
import com.beiyongjin.byg.module.user.dataModel.submit.ForgotPaySub;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Author: JinFu
 * E-mail: jf@erongdu.com
 * Date: 2017/2/14 17:46
 * <p>
 * Description:我的接口
 */
public interface MineService {

//    https://web.beiyj.com//api/user/isPhoneExists.htm
    /** 获取认证信息 */
    @GET("act/mine/userAuth/getUserAuth.htm")
    Call<HttpResult<CreditStatusRec>> getUserAuth();

    /** 获取字典 */
    @GET("act/dict/list.htm")
    Call<HttpResult<DicRec>> getDicts(@Query(BundleKeys.TYPE) String type);

    /** 保存联系人信息 */
    @POST("act/mine/contact/saveOrUpdate.htm")
    Call<HttpResult> contactSaveOrUpdate(@Body CreditLinkerSub sub);

    /** 获取联系人信息 */
    @GET("act/mine/contact/getContactInfoList.htm")
    Call<HttpResult<ListData<CreditLinkerRec>>> getContactInfoList();

    /** 获取银行卡列表 */
    @GET("act/mine/bankCard/getBankCardList.htm")
    Call<HttpResult<CreditBankRec>> getBankCardList();

    /** 保存银行卡 */
    @POST("act/mine/bankCard/authSign.htm")
    Call<HttpResult<BankRec>> bankSaveOrUpdate(@Body CreditBankSub sub);

    /** 银行卡签约处理 */
    @POST("act/mine/bankCard/authSignReturn.htm")
    Call<HttpResult> authSignReturn(@Body AuthSignSub sub);

    @GET("act/mine/bankCard/getCaptcha.htm")
    Call<HttpResult> getCaptcha();

    /** 提交ocr文件流 */
    @Multipart
    @POST("act/mine/userInfo/apiLinkfaceIDOcrRequest.htm")
    Call<HttpResult<OcrMsgRec>> apiLinkfaceIDOcrRequest(@HeaderMap Map<String, String> head, @PartMap Map<String, RequestBody> params);

    /** 个人信息认证提交 */
    @Multipart
    @POST("act/mine/userInfo/apiLinkfaceliRequest.htm")
    Call<HttpResult> apiLinkfaceliRequest(@HeaderMap Map<String, String> head, @PartMap Map<String, RequestBody> params);

    /** 个人信息认证提交 */
    @Multipart
    @POST("act/mine/userInfo/apiLinkfaceliRequest.htm")
    Call<HttpResult> updateLinkfaceliRequest(@HeaderMap Map<String, String> head, @PartMap Map<String, RequestBody> params);


    /** 个人信息认证提交 */
    @Multipart
    @POST("act/mine/userInfo/idCardCredit.htm")
    Call<HttpResult> idCardCredit(@HeaderMap Map<String, String> head, @PartMap Map<String, RequestBody> params);

    /** 个人信息认证提交 */
    @Multipart
    @POST("act/mine/userInfo/idCardCredit.htm")
    Call<HttpResult> updateIdCardCredit(@HeaderMap Map<String, String> head, @PartMap Map<String, RequestBody> params);

    /** 个人信息认证提交 */
    @Multipart
    @POST("act/mine/userInfo/authentication.htm")
    Call<HttpResult> idCardCreditTwo(@HeaderMap Map<String, String> head, @PartMap Map<String, RequestBody> params);

    /** 个人信息认证提交 */
    @Multipart
    @POST("act/mine/userInfo/authentication.htm")
    Call<HttpResult> updateIdCardCreditTwo(@HeaderMap Map<String, String> head, @PartMap Map<String, RequestBody> params);

    /** 获取个人信息 */
    @GET("act/mine/userInfo/getUserInfo.htm")
    Call<HttpResult<CreditPersonRec>> getUserInfo();

//    /** 芝麻信息 */
//    @GET("act/mine/zhima/view.htm")
//    Call<HttpResult<CreditZmxyRec>> zmxyView();

    /** 芝麻信息 */
    @GET("act/mine/bqs_zhima/view.htm")
    Call<HttpResult<CreditZmxyRec>> zmxyView();

    /** 工作信息保存 */
    @POST("act/mine/workInfo/saveOrUpdate.htm")
    Call<HttpResult> workInfoSaveOrUpdate(@Body CreditWorkSub sub);

    /** OCR使用记录同步 */
    @POST("act/mine/sdk/synchron.htm")
    Call<HttpResult> ocrSynchron(@Body IdCardSyncSub sub);

    /** 身份证识别次数初始化 */
    @GET("act/mine/sdk/find.htm")
    Call<HttpResult<IdCardTimeRec>> idCardCreditTime();

    /** 身份证识别次数初始化 */
    @GET("act/mine/userInfo/ocrUrl.htm")
    Call<HttpResult<FaceOcrRec>> ocrUrl();

    /** 工作信息获取 */
    @GET("act/mine/userInfo/getWorkInfo.htm")
    Call<HttpResult<CreditWorkInfoRec>> getWorkInfo();

    /** 工作照保存 */
    @Multipart
    @POST("act/mine/workInfo/workImgSave.htm")
    Call<HttpResult> workImgSave(@HeaderMap Map<String, String> head, @PartMap Map<String, RequestBody> params);

    /** 上传身份证正面照 获取ocr信息 */
    @Multipart
    @POST("act/mine/faceID/getOCR.htm")
    Call<HttpResult<RegardOcrMsgRec>> subOcr(@HeaderMap Map<String, String> head, @PartMap Map<String, RequestBody> params);

    /** 上传身份证正面照 获取ocr信息 小视科技*/
    @Multipart
    @POST
    Call<HttpResult<RegardOcrMsgRec>> subRegardOcr(@Url String url, @PartMap Map<String, RequestBody> params);

    /** 工作照查询 */
    @GET("act/mine/userInfo/getWorkImg.htm")
    Call<HttpResult<ListData<String>>> getWorkImg();

    /** 查询更多 */
    @GET("act/mine/other/findDetail.htm")
    Call<HttpResult<CreditMoreRec>> otherFindDetail();

    /** 更多信息 */
    @POST("act/mine/other/saveOrUpdate.htm")
    Call<HttpResult> otherSaveOrUpdate(@Body CreditMoreSub sub);

//    /** 芝麻信息 */
//    @GET("act/mine/zhima/authorize.htm")
//    Call<HttpResult<CreditUrlRec>> authorize();
    /** 芝麻信息 */
    @GET("act/mine/bqs_zhima/authorize.htm")
    Call<HttpResult<CreditUrlRec>> authorize();

    /** 获取个人信息 */
    @POST("act/user/info.htm")
    Call<HttpResult<InfoRec>> getInfo();

    /** 交易密码状态 */
    @POST("act/user/getTradeState.htm")
    Call<HttpResult<TradeStateRec>> getTradeState();

    /** 退出登录 */
    @POST("user/logout.htm")
    Call<HttpResult> logout();

    /** 修改密码 */
    @POST("act/user/changeLoginPwd.htm")
    Call<HttpResult> updatePwd(@Body UpdatePwdSub sub);

    /** 找回交易密码身份验证 */
    @POST("act/user/validateUser.htm")
    Call<HttpResult<PassRec>> validateUser(@Body ForgotPaySub sub);

    /** 修改交易密码 */
    @POST("act/user/changeTradePwd.htm")
    Call<HttpResult> updatePayPwd(@Body UpdatePwdSub sub);

    /** 验证交易密码是否正确 */
    @POST("act/user/validateTradePwd.htm")
    Call<HttpResult<PassRec>> validateTradePwd(@Body UpdatePwdSub sub);

    /** 设置交易密码 */
    @POST("act/user/setTradePwd.htm")
    Call<HttpResult> setTradePwd(@Body UpdatePwdSub sub);

    /** 重置交易密码 */
    @POST("act/user/resetTradePwd.htm")
    Call<HttpResult> resetTradePwd(@Body UpdatePwdSub sub);

    //******************* 代理商及邀请  *********************//

    /** 获取运营商连接 */
    @GET("act/mine/operator/operatorCredit.htm")
    Call<HttpResult<CreditUrlRec>> operatorCredit();

    /** 获取公积金连接 */
    @GET("act/mine/userAuth/accFundRequest.htm")
    Call<HttpResult<CreditUrlRec>> accFundRequest();

    /** 获取手机号 */
    @POST("userInvite/findPhone.htm")
    Call<HttpResult<MineInviteRec>> findPhone();

    /** 我的奖金 */
    @POST("act/mine/profitAmount/find.htm")
    Call<HttpResult<InviteBonusRec>> findBonus();

    /** 邀请记录 */
    @FormUrlEncoded
    @POST("act/user/inviteList.htm")
    Call<HttpResult<ListData<InviteRecordItemRec>>> inviteList(@Field("invitId") String id, @Field("pageNo") int current, @Field("pageSize") int pageSize);

    /** 邀请地址 */
    @POST("userInvite/findInvite.htm")
    Call<HttpResult<ShareLinkRec>> findInvite();

    /** 獲取分享圖片 */
    @POST
    @Streaming
    Call<ResponseBody> readImg(@Url String url);

    /**
     * 奖励明细
     */
    @POST("act/mine/profitLog/page.htm")
    Call<HttpResult<ListData<InviteAwardItemRec>>> profitLog(@Body PageMo pageMo);

    /**
     * 提现记录
     */
    @POST("act/mine/profitCashLog/page.htm")
    Call<HttpResult<ListData<InviteWithdrawItemRec>>> profitCashLog(@Body PageMo pageMo);

    /** 意见反馈 */
    @POST("act/mine/opinion/submit.htm")
    Call<HttpResult> opinion(@Body IdeaSub sub);

    /** 通话记录 */
    @POST("act/mine/userInfo/records.htm")
    Call<HttpResult> records(@Body PhoneInfoSub sub);

    /** 短信 */
    @POST("act/mine/userInfo/messages.htm")
    Call<HttpResult> messages(@Body PhoneInfoSub sub);

    /** 通讯录 */
    @POST("act/mine/userInfo/contacts.htm")
    Call<HttpResult> contacts(@Body PhoneInfoSub sub);

    /** 获取认证中心图片 */
    @GET("act/mine/userAuth/getAuthImgLogo.htm")
    Call<HttpResult<CreditImgRec>> getAuthImgLogo();
}
