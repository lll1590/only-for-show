package com.beiyongjin.byg.module.user.dataModel.receive;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/18 下午3:15
 * <p/>
 * Description: 登录信息
 */
public class OauthTokenMo {
    /** 刷新token值 */
    private String refreshToken;
    /** 用户名 */
    private String username;
    /** token */
    private String token;
    /** 用户ID */
    private String userId;
    /** 头像地址 */
    private String avatarPhoto;
    /** 隐藏用户名 */
    private String hideUserName;

    public String getAvatarPhoto() {
        return avatarPhoto;
    }

    public String getHideUserName() {
        return hideUserName;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
