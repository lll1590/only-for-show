package com.beiyongjin.byg.router.service;

import android.content.Context;
import android.net.Uri;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.PathReplaceService;
import com.beiyongjin.byg.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/4/8 17:30
 * <p/>
 * Description: 重写跳转URL
 */
@Route(path = RouterUrl.SERVICE_PATH_REPLACE)
public class PathReplaceServiceImpl implements PathReplaceService {
    @Override
    public String forString(String path) {
        // 按照一定的规则处理之后返回处理后的结果
        return path;
    }

    @Override
    public Uri forUri(Uri uri) {
        // 按照一定的规则处理之后返回处理后的结果
        return uri;
    }

    @Override
    public void init(Context context) {

    }
}
