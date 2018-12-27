package com.beiyongjin.byg.router.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.google.gson.Gson;
import com.beiyongjin.byg.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/6/6 9:52
 * <p/>
 * Description: 传递自定义对象
 */
@Route(path = RouterUrl.SERVICE_JSON)
public class JsonServiceImpl implements SerializationService {
    @Override
    public void init(Context context) {

    }

    @Override
    public <T> T json2Object(String text, Class<T> clazz) {
        return new Gson().fromJson(text, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        return new Gson().toJson(instance);
    }
}
