package com.beiyongjin.byg.module.user.dataModel.receive;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/12/9 09:54
 * <p/>
 * Description:
 */
public class ForgotRec {
    /** 手机号 */
    private String pathWay;
    /** 用户标识 */
    private String __sid;

    public String getPathWay() {
        return pathWay;
    }

    public void setPathWay(String pathWay) {
        this.pathWay = pathWay;
    }

    public String get__sid() {
        return __sid;
    }

    public void set__sid(String __sid) {
        this.__sid = __sid;
    }
}
