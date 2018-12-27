package com.beiyongjin.byg.module.user.dataModel.submit;

/**
 * Author: JinFu
 * E-mail: jf@erongdu.com
 * Date: 2017/1/12 17:43
 * <p/>
 * Description: 修改密码需要提交的数据
 */
public class ResetPwdSub {
    /** 用户ID */
    private String accountId;
    /** 新密码 */
    private String newPwd;
    /** 确认密码 */
    private String verifyPwd;
    /** 老密码 */
    private String oldPwd;
    /** 用户token */
    private String token;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getVerifyPwd() {
        return verifyPwd;
    }

    public void setVerifyPwd(String verifyPwd) {
        this.verifyPwd = verifyPwd;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
