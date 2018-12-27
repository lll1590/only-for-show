package com.beiyongjin.byg.network.api;

import com.erongdu.wireless.network.entity.HttpResult;
import com.erongdu.wireless.network.entity.ListData;
import com.erongdu.wireless.network.entity.PageMo;
import com.beiyongjin.byg.module.mine.dataModel.recive.BorrowRec;
import com.beiyongjin.byg.module.repay.dataModel.rec.ActiveRepaymentRec;
import com.beiyongjin.byg.module.repay.dataModel.rec.RepayAccountRec;
import com.beiyongjin.byg.module.repay.dataModel.rec.RepayDetailsRec;
import com.beiyongjin.byg.module.repay.dataModel.rec.RepayRecordItemRec;
import com.beiyongjin.byg.network.RequestParams;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Author: JinFu
 * E-mail: jf@erongdu.com
 * Date: 2017/2/14 17:45
 * <p>
 * Description:还款接口
 */
public interface RepayService {
    /**
     * 借款记录
     */
    @POST("act/mine/borrow/page.htm")
    Call<HttpResult<ListData<BorrowRec>>> getBorrow(@Body PageMo pageMo);

    /**
     * 还款记录
     */
    @POST("act/borrow/findAll.htm")
    Call<HttpResult<ListData<RepayRecordItemRec>>> getRecordList(@Body PageMo pageMo);

    /**
     * 还款详情
     */
    //    @POST("act/borrow/repayRegister/repayView.htm")
    @FormUrlEncoded
    @POST("act/mine/borrow/findProgress.htm")
    Call<HttpResult<RepayDetailsRec>> getRepayDetails(@Field("borrowId") String id);

    /**
     * 还款申请
     */
    @POST("act/borrow/repayRegister/repayRegister.htm")
    Call<HttpResult> getRepayApply();

    /**
     * 还款申请
     */
    @GET("act/borrow/repay/collectionInfo.htm")
    Call<HttpResult<RepayAccountRec>> getRepayType(@Query(RequestParams.TYPE) String type);

    /**
     *主动还款--连连
     */
    @FormUrlEncoded
    @POST("act/borrow/repay/repayment.htm")
    Call<HttpResult<ActiveRepaymentRec>> activeRepayment(@Field("borrowId") String borrowId,@Field("ip") String ip);

    /**
     *主动还款同步响应--连连
     */
    @FormUrlEncoded
    @POST("act/borrow/repay/repaymentReturn.htm")
    Call<HttpResult> activeRepaymentReturn(@Field("payOrderNo") String payOrderNo,@Field("payResult") String payResult);
}
