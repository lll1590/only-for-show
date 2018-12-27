package com.beiyongjin.byg.common.binding;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.beiyongjin.byg.R;

import java.io.File;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/8/17 11:53
 * <p/>
 * Description: 自定义转换
 */
@SuppressWarnings("unused")
public class BindingConversions {
    /**
     * int 型color 转  ColorDrawable
     */
    @BindingConversion
    public static ColorDrawable convertColorToDrawable(int color) {
        return new ColorDrawable(color);
    }

    /**
     * File 型 转 String 路径
     */
    @BindingConversion
    public static String fileToPath(File file) {
        return null == file ? "" : file.getPath();
    }

    /**
     * 设置Textview drawable ColorFilter
     *
     * @param imageView
     * @param color
     */
    @BindingAdapter(value = {"drawable", "colorFilter"}, requireAll = true)
    public static void drawableImageView(ImageView imageView, Drawable drawable, int color) {
        if (0 == color) {
            color = ContextCompat.getColor(imageView.getContext(), R.color.app_color_principal);
        }
        if (null != drawable) {
            drawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        }

        imageView.setImageDrawable(drawable);
    }

    /**
     * 设置Textview drawable ColorFilter
     *
     * @param tv
     * @param drawableLeft
     * @param drawableTop
     * @param drawableRight
     * @param drawableBottom
     * @param color
     */
    @BindingAdapter(value = {"drawableLeft", "drawableTop", "drawableRight", "drawableBottom", "colorFilter"}, requireAll = false)
    public static void drawableText(TextView tv, Drawable drawableLeft, Drawable drawableTop, Drawable drawableRight, Drawable drawableBottom, int color) {
        if (0 == color) {
            color = ContextCompat.getColor(tv.getContext(), R.color.app_color_principal);
        }
        if (null != drawableLeft) {
            drawableLeft.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        }
        if (null != drawableTop) {
            drawableTop.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        }
        if (null != drawableBottom) {
            drawableRight.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        }
        if (null != drawableBottom) {
            drawableRight.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        }
        tv.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
    }
}
