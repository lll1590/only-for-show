package com.beiyongjin.byg.views.iconfont;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

/**
 * Created by chenming
 * Created Date 17/4/21 11:25
 * mail:cm1@erongdu.com
 * Describe:
 */
public class IconFont implements IconFontDescriptor {

    @Override
    public String ttfFileName() {
        return "iconfont.ttf";
    }

    @Override
    public Icon[] characters() {
        return IconFontValues.values();
    }

}
