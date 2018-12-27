package com.beiyongjin.byg.module.home.dataModel;

import java.util.List;

/**
 * Created by yoseflin on 5/8/17.
 */
public class HomeChoiceRec {
    private String                     amount;  // 借款金额
    private String                     fee;     // 综合服务费率
    private HomeFeeDetailRec           feeDetail; // 服务费明细
    private List<HomeFeeDetailItemRec> feeDetailList; // 服务费明细
    private String                     realAmount;  // 实际到账金额
    private String                     timeLimit;   // 借款期限

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public HomeFeeDetailRec getFeeDetail() {
        return feeDetail;
    }

    public void setFeeDetail(HomeFeeDetailRec feeDetail) {
        this.feeDetail = feeDetail;
    }

    public String getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(String realAmount) {
        this.realAmount = realAmount;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public List<HomeFeeDetailItemRec> getFeeDetailList() {
        return feeDetailList;
    }
}
