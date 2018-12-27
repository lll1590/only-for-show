package com.beiyongjin.byg.module.user.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.erongdu.wireless.tools.utils.StringFormat;
import com.erongdu.wireless.tools.utils.TextUtil;
import com.beiyongjin.byg.BR;
import com.beiyongjin.byg.module.user.viewControl.LoginCtrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/16 16:17
 * <p/>
 * Description: 登录页面模型({@link LoginCtrl})
 */
public class LoginVM extends BaseObservable {
    /** 手机号 */
    private String  phone;
    private String  phoneHide;
    /** 密码 */
    private String  pwd;
    /** 按钮是否可用 */
    private boolean enable;
    /** 按钮是否可用 */
    private boolean stepEnable;
    /** 是否为下一步 */
    private boolean step = true;

    public LoginVM() {
        /*phone = "13758213355";
        pwd = "123456";*/
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        checkPhoneInput();
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getPhoneHide() {
        return StringFormat.phoneHideFormat(phone);
    }

    @Bindable
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
        checkPwdInput();
        notifyPropertyChanged(BR.pwd);
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
    public boolean isStepEnable() {
        return stepEnable;
    }

    public void setStepEnable(boolean stepEnable) {
        this.stepEnable = stepEnable;
        notifyPropertyChanged(BR.stepEnable);
    }

    @Bindable
    public boolean isStep() {
        return step;
    }

    public void setStep(boolean step) {
        this.step = step;
        notifyPropertyChanged(BR.step);
        notifyPropertyChanged(BR.phoneHide);
    }

    private void checkPhoneInput() {
        if (TextUtil.isEmpty(phone) || phone.length() != 11) {
            setStepEnable(false);
        } else {
            setStepEnable(true);
        }
    }

    private void checkPwdInput() {
        if (TextUtil.isEmpty(pwd) || pwd.length() < 6) {
            setEnable(false);
        } else {
            setEnable(true);
        }
    }
}
