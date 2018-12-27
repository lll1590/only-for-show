package com.beiyongjin.byg.common.binding;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;

import com.erongdu.wireless.views.SwitchButton;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/30 10:53
 * <p/>
 * Description:
 */
@BindingMethods({
        @BindingMethod(type = SwitchButton.class, attribute = "isOpened", method = "setOpened"),
})
@InverseBindingMethods({
        @InverseBindingMethod(type = SwitchButton.class, attribute = "isOpened", method = "isOpened"),
})
public class SwitchButtonBindingAdapter {
    @BindingAdapter("isOpened")
    public static void setOpened(SwitchButton view, boolean isOpened) {
        if (view.isOpened() != isOpened) {
            view.setOpened(isOpened);
        }
    }

    @BindingAdapter("isOpenedAttrChanged")
    public static void setOpenedChangedListener(SwitchButton view, final InverseBindingListener attrChange) {
        if (attrChange == null) {
            view.setOnClickListener(null);
        } else {
            view.setOnStateChangedListener(new SwitchButton.OnStateChangedListener() {
                @Override
                public void toggleToOn(SwitchButton view) {
                    view.setOpened(true);
                    attrChange.onChange();
                }

                @Override
                public void toggleToOff(SwitchButton view) {
                    view.setOpened(false);
                    attrChange.onChange();
                }
            });
        }
    }
}
