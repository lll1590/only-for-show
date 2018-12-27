package com.beiyongjin.byg.module.user.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.erongdu.wireless.tools.utils.ContextHolder;
import com.erongdu.wireless.tools.utils.RegularUtil;
import com.erongdu.wireless.tools.utils.TextUtil;
import com.beiyongjin.byg.BR;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.module.user.viewControl.ForgotCtrl;
import com.beiyongjin.byg.utils.InputCheck;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/17 17:25
 * <p/>
 * Description: 忘记密码页面模型({@link ForgotCtrl})
 */
public class ForgotVM extends BaseObservable {
    /* 忘记修改面第一步 */
    /** 手机号 */
    private String  phone;
    /** 验证码 */
    private String  code;
    /** 获取验证码按钮是否可用 */
    private boolean codeEnable;
    /** 下一步按钮是否可用 */
    private boolean enable;
    /* 忘记修改面第二步 */
    /** 修改按钮是否可用 */
    private boolean updateEnable;
    /** 新密码 */
    private String  pwd;
    /** 确认新密码 */
    private String  confirmPwd;
    /** 是否可见第一步 */
    private int    isOne = View.VISIBLE;
    /** 是否可见第二步 */
    private int    isTwo = View.GONE;
    private String title = ContextHolder.getContext().getResources().getString(R.string.forgot_pwd_title_step_1);   //忘记密码标题

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        checkInput();
        codeEnableCheck();
        notifyPropertyChanged(BR.phone);
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
    public int getIsOne() {
        return isOne;
    }

    public void setIsOne(int isOne) {
        this.isOne = isOne;
        notifyPropertyChanged(BR.isOne);
    }

    @Bindable
    public int getIsTwo() {
        return isTwo;
    }

    public void setIsTwo(int isTwo) {
        this.isTwo = isTwo;
        notifyPropertyChanged(BR.isTwo);
    }

    /* 忘记修改密码第二步 */
    @Bindable
    public boolean isUpdateEnable() {
        return updateEnable;
    }

    public void setUpdateEnable(boolean updateEnable) {
        this.updateEnable = updateEnable;
        notifyPropertyChanged(BR.updateEnable);
    }

    @Bindable
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
        notifyPropertyChanged(BR.pwd);
        checkInputUpdate();
    }

    @Bindable
    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
        checkInputUpdate();
    }

    /**
     * TimeButton是否可用
     */
    private void codeEnableCheck() {
//        if (RegularUtil.isPhone(phone)) {
//            setCodeEnable(true);
//        } else {
//            setCodeEnable(false);
//        }
        if (RegularUtil.isPhoneLength(phone)) {
            setCodeEnable(true);
        } else {
            setCodeEnable(false);
        }
    }

    /**
     * 输入校验
     */
    private void checkInput() {
        if (!TextUtil.isEmpty(phone) && InputCheck.checkCode(code)) {
            setEnable(true);
        } else {
            setEnable(false);
        }
    }

    /**
     * 修改密码输入校验
     */
    private void checkInputUpdate() {
        if (TextUtil.isEmpty(pwd) || TextUtil.isEmpty(confirmPwd) || pwd.length() < 6 || confirmPwd.length() < 6 || confirmPwd.length() > 16 || pwd.length()
                > 16) {
            setUpdateEnable(false);
        } else {
            setUpdateEnable(true);
        }
    }
}
