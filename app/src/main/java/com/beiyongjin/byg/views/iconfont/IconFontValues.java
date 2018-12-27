package com.beiyongjin.byg.views.iconfont;

import com.joanzapata.iconify.Icon;
/**
 * Created by chenming
 * Created Date 17/4/21 11:27
 * mail:cm1@erongdu.com
 * Describe:
 */
public enum IconFontValues implements Icon {
    home_icon('\ue682'),
    repay_icon('\ue683'),
    mine_icon('\ue684'),
    html_home_icon('\ue6ac');

    char character;

    IconFontValues(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
