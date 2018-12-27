package com.beiyongjin.byg.module.user.dataModel.submit;

import com.erongdu.wireless.network.annotation.SerializedEncryption;
import com.erongdu.wireless.network.annotation.SerializedIgnore;
import com.google.gson.annotations.SerializedName;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/17 18:43
 * <p/>
 * Description: 忘记密码  重置密码需要提交的数据
 */
public class ForgotSub {
    /** 手机号 */
    @SerializedName("phone")
    private String mobile;
    /** 密码 */
    @SerializedEncryption(type = "MD5")
    @SerializedName("newPwd")
    private String password;
    /** 确认密码 */
    @SerializedIgnore
    private String confirmPassword;
    /** 验证码 */
    @SerializedName("vcode")
    private String verifyCode;
    /** 签名信息 appKey+newPwd+phone+vcode */
    private String signMsg;

    public ForgotSub(String mobile, String password, String confirmPassword, String verifyCode) {
        this.mobile = mobile;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.verifyCode = verifyCode;
    }

    public ForgotSub() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getSignMsg() {
        return signMsg;
    }

    public void setSignMsg(String signMsg) {
        this.signMsg = signMsg;
    }
}
