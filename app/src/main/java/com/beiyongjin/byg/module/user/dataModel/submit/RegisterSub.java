package com.beiyongjin.byg.module.user.dataModel.submit;

import com.erongdu.wireless.network.annotation.SerializedEncryption;
import com.google.gson.annotations.SerializedName;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/17 16:18
 * <p/>
 * Description: 注册需要提交的数据
 */
public class RegisterSub {
    /** 手机号 */
    @SerializedName("loginName")
    private String phone;
    /** 密码 */
    @SerializedEncryption(type = "MD5")
    @SerializedName("loginPwd")
    private String pwd;
    @SerializedName("vcode")
    private String code;
    /** 邀请人手机号 */
    @SerializedName("invitationCode")
    private String inviter;

    /** 设备指纹 */
    @SerializedName("blackBox")
    private String box;
    /** 设备类型 */
    private String client;

    /** 是否同意协议 */
    private int agree = 1;
    public RegisterSub() {
    }
    /** 注册地址 */
    private String registerAddr;
    /** 注册经纬度 */
    private String registerCoordinate;

    /*添加白骑士token*/

    private String extToken;

    public RegisterSub(String phone, String pwd, String inviter,String extToken) {
        this.phone = phone;
        this.pwd = pwd;
        this.inviter = inviter;
        this.extToken=extToken;
    }

    public String getRegisterAddr() {
        return registerAddr;
    }

    public void setRegisterAddr(String registerAddr) {
        this.registerAddr = registerAddr;
    }

    public String getRegisterCoordinate() {
        return registerCoordinate;
    }

    public void setRegisterCoordinate(String registerCoordinate) {
        this.registerCoordinate = registerCoordinate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public int getAgree() {
        return agree;
    }

    public void setAgree(int agree) {
        this.agree = agree;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getExtToken() {
        return extToken;
    }

    public void setExtToken(String extToken) {
        this.extToken = extToken;
    }

    @Override
    public String toString() {
        return "RegisterSub{" +
                "phone='" + phone + '\'' +
                ", pwd='" + pwd + '\'' +
                ", code='" + code + '\'' +
                ", inviter='" + inviter + '\'' +
                ", box='" + box + '\'' +
                ", client='" + client + '\'' +
                ", agree=" + agree +
                ", registerAddr='" + registerAddr + '\'' +
                ", registerCoordinate='" + registerCoordinate + '\'' +
                ", extToken='" + extToken + '\'' +
                '}';
    }
}
