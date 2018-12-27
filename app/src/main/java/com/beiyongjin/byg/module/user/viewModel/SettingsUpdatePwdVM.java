package com.beiyongjin.byg.module.user.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.erongdu.wireless.tools.utils.TextUtil;
import com.beiyongjin.byg.BR;

/**
 * Author: JinFu
 * E-mail: jf@erongdu.com
 * Date: 2017/2/22 9:27
 * <p/>
 * Description:
 */
public class SettingsUpdatePwdVM extends BaseObservable {
    String phone;
    String pwd;
    String newPwd;
    String confirmPwd;
    boolean enable = false;

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
        notifyPropertyChanged(BR.pwd);
        checkInput();
    }

    @Bindable
    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
        notifyPropertyChanged(BR.newPwd);
        checkInput();
    }

    @Bindable
    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
        notifyPropertyChanged(BR.confirmPwd);
        checkInput();
    }

    public void checkInput() {
        setEnable(!(TextUtil.isEmpty(pwd) || TextUtil.isEmpty(newPwd) || TextUtil.isEmpty(confirmPwd) || pwd.length() < 6 || newPwd.length() < 6 ||
                confirmPwd.length() < 6));
    }

    @Bindable
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        notifyPropertyChanged(BR.enable);
    }
}
