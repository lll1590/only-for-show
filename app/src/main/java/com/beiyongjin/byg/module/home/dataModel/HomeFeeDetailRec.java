package com.beiyongjin.byg.module.home.dataModel;

/**
 * Created by yoseflin on 5/8/17.
 */

public class HomeFeeDetailRec {

    private String infoAuthFee;     // 信息认证费

    private String interest;        // 利息

    private String serviceFee;      // 居间服务费

    public String getInfoAuthFee() {
        return infoAuthFee;
    }

    public void setInfoAuthFee(String infoAuthFee) {
        this.infoAuthFee = infoAuthFee;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }
}
