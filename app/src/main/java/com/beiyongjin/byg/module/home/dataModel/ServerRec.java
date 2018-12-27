package com.beiyongjin.byg.module.home.dataModel;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/12/12 15:14
 * <p/>
 * Description:
 */
public class ServerRec {
    /** 静态资源服务器地址 */
    private String imageServer;
    /** 移动端服务器地址 */
    private String mobileServer;

    public String getImageServer() {
        return imageServer;
    }

    public void setImageServer(String imageServer) {
        this.imageServer = imageServer;
    }

    public String getMobileServer() {
        return mobileServer;
    }

    public void setMobileServer(String mobileServer) {
        this.mobileServer = mobileServer;
    }
}
