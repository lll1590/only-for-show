package com.beiyongjin.byg.module.home.dataModel;

/**
 * Author: TaoHao
 * E-mail: taoh@erongdu.com
 * Date: 2016/10/27$ 14:48$
 * <p/>
 * Description: 首页产品列表数据接收类型
 */
public class ProjectItemRec {

    /** 项目总额 */
    private String account;
    /** 加息 */
    private String addApr;
    /** 利率 */
    private String apr;
    /** 是否可以债权转让 1 可以，0不可以 */
    private String     bondUseful;
    /** 最低起投金额 */
    private String lowestAccount;
    /** 最高可投 */
    private String mostAccount;
    /** 新手标标识： 1新手专享 0 普通 ，默认：0 */
    private String     novice;
    /** 项目id */
    private String     projectId;
    /** 项目名称 */
    private String     projectName;
    /** 是否可变现: 1 可变现 0 不可变现，默认 0 */
    private String     realizeUseful;
    /** 剩余可投金额 */
    private String     remainAccount;
    /** 开售时间，如果过了返回-1 */
    private String       saleTime;
    /** 定向销售方式(0 不定向 1 定向密码 2 定向等级 3 定向邮箱域名，默认0 */
    private String     specificSale;
    /** 借款期限 */
    private String    timeLimit;
    /** 借款期限类型： 0月标 1天标 */
    private String     timeType;

    public String getAccount() {
        return account;
    }

    public String getAddApr() {
        return addApr;
    }

    public String getApr() {
        return apr;
    }

    public String getBondUseful() {
        return bondUseful;
    }

    public String getLowestAccount() {
        return lowestAccount;
    }

    public String getMostAccount() {
        return mostAccount;
    }

    public String getNovice() {
        return novice;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getRealizeUseful() {
        return realizeUseful;
    }

    public String getRemainAccount() {
        return remainAccount;
    }

    public String getSaleTime() {
        return saleTime;
    }

    public String getSpecificSale() {
        return specificSale;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public String getTimeType() {
        return timeType;
    }
}
