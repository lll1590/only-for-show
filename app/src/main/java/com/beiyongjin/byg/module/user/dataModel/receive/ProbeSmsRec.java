package com.beiyongjin.byg.module.user.dataModel.receive;

/**
 * Created by mingchen on 17/3/16.
 * 是否可获取短信验证码
 */
public class ProbeSmsRec {
    /** 可获取验证码的倒计时，单位秒 */
    private String countDown;
    /** 10可获取，20不可获取 */
    private String state;
    /** 提示信息 */
    private String message;

    public String getCountDown() {
        return countDown;
    }

    public String getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }
}
