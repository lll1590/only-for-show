package com.beiyongjin.byg.module.home.dataModel;

import com.erongdu.wireless.network.annotation.SerializedEncryption;

/**
 * Author: JinFu
 * E-mail: jf@erongdu.com
 * Date: 2017/2/20 14:01
 * <p>
 * Description:申请借款信息
 */
public class LoanSub {
    private String amount;//借款金额	string		是
    private String cardId;//	银行卡关联id	string		是
    private String fee;//	综合费用	string		是
    private String realAmount;//实际到账金额	string		是
    private String timeLimit;//借款期限	string		是
    @SerializedEncryption(type = "MD5")
    private String tradePwd;//交易密码	string		是	MD5加密
    private String client = "10";
    private String address;
    private String coordinate;
    private String ip;
    private String extToken;

    public LoanSub(String amount, String cardId, String fee, String realAmount, String timeLimit, String tradePwd, String address, String coordinate, String ip,String extToken) {
        this.amount = amount;
        this.cardId = cardId;
        this.fee = fee;
        this.realAmount = realAmount;
        this.timeLimit = timeLimit;
        this.tradePwd = tradePwd;
        this.address = address;
        this.coordinate = coordinate;
        this.ip = ip;
        this.extToken=extToken;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
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

    public String getTradePwd() {
        return tradePwd;
    }

    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getExtToken() {
        return extToken;
    }

    public void setExtToken(String extToken) {
        this.extToken = extToken;
    }

    @Override
    public String toString() {
        return "LoanSub{" +
                "amount='" + amount + '\'' +
                ", cardId='" + cardId + '\'' +
                ", fee='" + fee + '\'' +
                ", realAmount='" + realAmount + '\'' +
                ", timeLimit='" + timeLimit + '\'' +
                ", tradePwd='" + tradePwd + '\'' +
                ", client='" + client + '\'' +
                ", address='" + address + '\'' +
                ", coordinate='" + coordinate + '\'' +
                ", ip='" + ip + '\'' +
                ", extToken='" + extToken + '\'' +
                '}';
    }
}
