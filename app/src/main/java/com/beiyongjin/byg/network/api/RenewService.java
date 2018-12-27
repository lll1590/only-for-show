package com.beiyongjin.byg.network.api;

import com.erongdu.wireless.network.entity.HttpResult;
import com.erongdu.wireless.network.entity.ListData;
import com.beiyongjin.byg.module.repay.dataModel.rec.RenewApplyRec;
import com.beiyongjin.byg.module.repay.dataModel.rec.RenewDetailRec;
import com.beiyongjin.byg.module.repay.dataModel.rec.RenewListDataRec;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Author: 沈志华
 * E-mail: shenzh@erongdu.com
 * Date: 2017/8/2$ 11:16$
 * <p/>
 */
public interface RenewService {
    /**
     * 续借详情
     */
    @FormUrlEncoded
    @POST("/api/act/borrow/renewDetail.htm")
    Call<HttpResult<RenewDetailRec>> getRenewDetail(@Field("borrowId") String borrowId);

    /**
     * 续借列表
     */
    @FormUrlEncoded
    @POST("/api/act/borrow/renewList.htm")
    Call<HttpResult<ListData<RenewListDataRec>>> getRenewList(@Field("borrowId") String borrowId, @Field("current") int current, @Field("pageSize") int pageSize);

    /**
     * 续借申请提交
     */
    @FormUrlEncoded
    @POST("/api/act/borrow/renewApply.htm")
    Call<HttpResult<RenewApplyRec>> renewApply(@Field("borrowId") String borrowId);
    /**
     * 续借返回结果  同步
     */
    @FormUrlEncoded
    @POST("/api/act/borrow/renewReturn.htm")
    Call<HttpResult> renewReturn(@Field("payOrderNo") String payOrderNo, @Field("payResult") String payResult);



}
