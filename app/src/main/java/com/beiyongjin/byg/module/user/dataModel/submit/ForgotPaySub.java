package com.beiyongjin.byg.module.user.dataModel.submit;

/**
 * Author: Hubert
 * E-mail: hbh@erongdu.com
 * Date: 2017/2/27 下午6:02
 * <p/>
 * Description:
 */
public class ForgotPaySub {
    //身份证
    private String idNo;
    //姓名
    private String realName;
    //短信验证码
    private String vcode;

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }
}
