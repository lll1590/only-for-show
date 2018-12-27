package com.beiyongjin.byg.module.user.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import com.erongdu.wireless.tools.utils.ContextHolder;
import com.beiyongjin.byg.BR;
import com.beiyongjin.byg.R;

/**
 * Author: Hubert
 * E-mail: hbh@erongdu.com
 * Date: 2017/2/25 下午7:49
 * <p/>
 * Description:
 */
public class ForgotPayVM extends BaseObservable {
    private String title = ContextHolder.getContext().getString(R.string.settings_forgot_pay_title);
    private String phone;
    private String name;
    private String no;
    private String code;
    private boolean codeEnable = false;
    private boolean enable     = false;

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        codeEnableCheck();
        notifyPropertyChanged(BR.phone);

    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        checkInput();
    }

    @Bindable
    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
        checkInput();
        notifyPropertyChanged(BR.no);
    }

    @Bindable
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
        checkInput();
        notifyPropertyChanged(BR.code);
    }

    @Bindable
    public boolean isCodeEnable() {
        return codeEnable;
    }

    public void setCodeEnable(boolean codeEnable) {
        this.codeEnable = codeEnable;
        notifyPropertyChanged(BR.codeEnable);
    }

    @Bindable
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        notifyPropertyChanged(BR.enable);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    /**
     * TimeButton是否可用
     */
    private void codeEnableCheck() {
        if (TextUtils.isEmpty(phone)) {
            setCodeEnable(false);
        } else {
            setCodeEnable(true);
        }
    }

    /**
     * 输入校验
     */
    private void checkInput() {
        if (TextUtils.isEmpty(no) || no.length() < 15 || TextUtils.isEmpty(name) || name.length() < 2 || TextUtils.isEmpty(code)) {
            setEnable(false);
        } else {
            setEnable(true);
        }
    }
}
