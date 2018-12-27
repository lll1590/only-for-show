package com.beiyongjin.byg.module.home.viewControl;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;
import com.beiyongjin.byg.R;

/**
 * Author: TaoHao
 * E-mail: taoh@erongdu.com
 * Date: 2016/10/20$ 13:43$
 * <p/>
 * Description: 轮播图加载器
 */
public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).error(R.drawable.banner_default).placeholder(R.drawable.banner_default).into(imageView);
    }
}
