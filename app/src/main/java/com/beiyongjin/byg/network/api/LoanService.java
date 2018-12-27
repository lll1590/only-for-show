package com.beiyongjin.byg.network.api;

import com.erongdu.wireless.network.entity.HttpResult;
import com.erongdu.wireless.network.entity.ListData;
import com.beiyongjin.byg.module.home.dataModel.HomeChoiceRec;
import com.beiyongjin.byg.module.home.dataModel.HomeRec;
import com.beiyongjin.byg.module.home.dataModel.LoanProgressRec;
import com.beiyongjin.byg.module.home.dataModel.LoanRec;
import com.beiyongjin.byg.module.home.dataModel.LoanSub;
import com.beiyongjin.byg.module.home.dataModel.NeedDivertRec;
import com.beiyongjin.byg.module.home.dataModel.NoticeRec;
import com.beiyongjin.byg.module.home.viewModel.HomeBannerRec;
import com.beiyongjin.byg.module.mine.dataModel.recive.BorrowRec;
import com.beiyongjin.byg.network.RequestParams;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Author: JinFu
 * E-mail: jf@erongdu.com
 * Date: 2017/2/14 17:45
 * <p/>
 * Description:借款接口
 */
public interface LoanService {
    /**
     * 获取首页详情
     */
    @POST("borrow/findIndex.htm")
    Call<HttpResult<HomeRec>> getHomeIndex();

    /**
     * 获取公告列表
     */
    @POST("borrow/listIndex.htm")
    Call<HttpResult<ListData<NoticeRec>>> getNoticeList();

    /**
     * 获取借款进度
     */
    @FormUrlEncoded
    @POST("act/mine/borrow/findProgress.htm")
    Call<HttpResult<ListData<LoanProgressRec>>> getProgressList(@Field(RequestParams.ORDER_NO) String orderNo);

    /**
     * 获取借款详情
     */
    @FormUrlEncoded
    @POST("act/borrow/findBorrow.htm")
    Call<HttpResult<LoanRec>> getLoanDetails(@Field(RequestParams.ORDER_NO) String orderNo);

    /**
     * 申请借款
     */
    @POST("act/borrow/save.htm")
    Call<HttpResult> getLoanApply(@Body LoanSub sub);

    /**
     * 选择借款金额和期限，获取实际到账和手续费信息
     */
    @POST("borrow/choices.htm")
    Call<HttpResult<ListData<HomeChoiceRec>>> getHomeChoice();

    /**
     * 选择借款金额和期限，获取实际到账和手续费信息(升级版查询所有借款费用信息)
     */
    @POST("borrow/choicesList.htm")
    Call<HttpResult<ListData<HomeChoiceRec>>> getHomeChoicesList();

    @POST("act/borrow/findAll.htm")
    Call<HttpResult<ListData<BorrowRec>>> findAll();
    /**
     * 是否引流
     */
    @GET("act/needDivert.htm")
    Call<HttpResult<NeedDivertRec>> needDivert();

    /**
     * 获取首页banner
     */
    @GET("adver/banner.htm")
    Call<HttpResult<ListData<HomeBannerRec>>> getBanner();
}
