package com.beiyongjin.byg.network.api;

import com.erongdu.wireless.network.entity.HttpResult;
import com.erongdu.wireless.network.entity.ListData;
import com.beiyongjin.byg.module.mine.dataModel.recive.CommonRec;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Author: JinFu
 * E-mail: jf@erongdu.com
 * Date: 2017/2/25 18:49
 * <p/>
 * Description:
 */
public interface CommonService {
    /** 协议清单 */
    @GET("protocol/list.htm")
    Call<HttpResult<ListData<CommonRec>>> protocolList();

    /** H5列表 */
    @GET("h5/list.htm")
    Call<HttpResult<ListData<CommonRec>>> h5List();

    /** 备注清单 */
    @GET("remark/list.htm")
    Call<HttpResult<ListData<CommonRec>>> remarkList();

}
