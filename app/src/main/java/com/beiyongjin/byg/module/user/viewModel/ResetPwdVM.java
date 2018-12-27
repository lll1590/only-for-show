package com.beiyongjin.byg.module.user.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.beiyongjin.byg.BR;
import com.beiyongjin.byg.module.user.viewControl.ResetPwdCtrl;
import com.beiyongjin.byg.utils.InputCheck;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/17 18:50
 * <p/>
 * Description: 重置密码页面模型({@link ResetPwdCtrl})
 */
public class ResetPwdVM extends BaseObservable {
    /** 旧密码 */
    private String  pwdOld;
    /** 新密码 */
    private String  pwdNew;
    /** 确认密码 */
    private String  pwdConfirm;
    /** 按钮是否可用 */
    private boolean enable;

    @Bindable
    public String getPwdOld() {
        return pwdOld;
    }

    public void setPwdOld(String pwdOld) {
        this.pwdOld = pwdOld;
        checkInput();
        notifyPropertyChanged(BR.pwdOld);
    }

    @Bindable
    public String getPwdNew() {
        return pwdNew;
    }

    public void setPwdNew(String pwdNew) {
        this.pwdNew = pwdNew;
        checkInput();
        notifyPropertyChanged(BR.pwdNew);
    }

    @Bindable
    public String getPwdConfirm() {
        return pwdConfirm;
    }

    public void setPwdConfirm(String pwdConfirm) {
        this.pwdConfirm = pwdConfirm;
        checkInput();
        notifyPropertyChanged(BR.pwdConfirm);
    }

    @Bindable
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        notifyPropertyChanged(BR.enable);
    }

    private void checkInput() {
        if (InputCheck.checkPwd(pwdOld) && InputCheck.checkPwd(pwdNew) && pwdNew.equals(pwdConfirm)) {
            setEnable(true);
        } else {
            setEnable(false);
        }
    }
}
