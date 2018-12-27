package com.beiyongjin.byg.module.user.dataModel.receive;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/12/9 14:12
 * <p/>
 * Description:
 */
public class SessionRec {
    /** 用户标识 */
    private String __sid;
    /** 修改邮箱标识 */
    private String modifyEmailSign;
    /** 绑定邮箱标识 */
    private String emailBindToken;
    /** 修改手机标识 */
    private String modifyPhoneSign;
    /** 绑定手机标识 */
    private String mobileBindToken;

    public String get__sid() {
        return __sid;
    }

    public void set__sid(String __sid) {
        this.__sid = __sid;
    }

    public String getModifyEmailSign() {
        return modifyEmailSign;
    }

    public void setModifyEmailSign(String modifyEmailSign) {
        this.modifyEmailSign = modifyEmailSign;
    }

    public String getEmailBindToken() {
        return emailBindToken;
    }

    public void setEmailBindToken(String emailBindToken) {
        this.emailBindToken = emailBindToken;
    }

    public String getModifyPhoneSign() {
        return modifyPhoneSign;
    }

    public void setModifyPhoneSign(String modifyPhoneSign) {
        this.modifyPhoneSign = modifyPhoneSign;
    }

    public String getMobileBindToken() {
        return mobileBindToken;
    }

    public void setMobileBindToken(String mobileBindToken) {
        this.mobileBindToken = mobileBindToken;
    }
}
