package com.beiyongjin.byg.network;

import com.erongdu.wireless.network.interceptor.BasicParamsInterceptor;
import com.erongdu.wireless.network.interceptor.IBasicDynamic;
import com.erongdu.wireless.tools.utils.ContextHolder;
import com.beiyongjin.byg.common.BaseParams;
import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.utils.statistics.DeviceInfoUtils;

import java.util.Map;
import java.util.TreeMap;

import okhttp3.Interceptor;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/5 17:59
 * <p/>
 * Description: 拦截器 - 用于添加签名参数
 */
class BasicParamsInject {
    private BasicParamsInterceptor interceptor;

    BasicParamsInject() {
        // 设置静态参数
        interceptor = new BasicParamsInterceptor.Builder()
                //.addBodyParam(Constant.APP_KEY, BaseParams.APP_KEY)
                .addBodyParam(Constant.MOBILE_TYPE, BaseParams.MOBILE_TYPE)
                .addBodyParam(Constant.VERSION_NUMBER, DeviceInfoUtils.getVersionName(ContextHolder.getContext()))
                .build();
        // 设置动态参数
        interceptor.setIBasicDynamic(new IBasicDynamic() {
            @Override
            public String signParams(String postBodyString) {
                //post提交动态添加参数
                return UrlUtils.getInstance().dynamicParams(postBodyString);
            }

            @Override
            public Map signParams(Map map) {
                //get提交动态添加参数
                TreeMap temp = new TreeMap(map);
                return UrlUtils.getInstance().dynamicParams(temp);
            }

            @Override
            public Map signHeadParams(Map headMap) {
                return UrlUtils.getInstance().signParams(headMap);
            }
        });
    }

    Interceptor getInterceptor() {
        return interceptor;
    }
}
