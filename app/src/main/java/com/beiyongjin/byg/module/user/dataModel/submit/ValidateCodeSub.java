package com.beiyongjin.byg.module.user.dataModel.submit;

/**
 * Author: Chenming
 * E-mail: cm1@erongdu.com
 * Date: 2017/2/21 下午7:43
 * <p/>
 * Description: 验证短信验证码提交累
 */
public class ValidateCodeSub {
    private String phone;
    /** 签名md5(appkey+phone＋type+vcode) */
    private String signMsg;
    private String vcode;
    private String type;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSignMsg() {
        return signMsg;
    }

    public void setSignMsg(String signMsg) {
        this.signMsg = signMsg;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
