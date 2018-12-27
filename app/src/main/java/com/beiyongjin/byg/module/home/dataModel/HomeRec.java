package com.beiyongjin.byg.module.home.dataModel;

import java.util.List;

/**
 * Author: JinFu
 * E-mail: jf@erongdu.com
 * Date: 2017/2/18 15:23
 * <p/>
 * Description:
 */
public class HomeRec {
    private String                borrowId;
    private String                cardName;
    private String                cardNo;
    private String                fee;
    private boolean               isBorrow;
    private String                maxCredit;
    private String                maxDays;
    private String                minCredit;
    private String                minDays;
    private String                title;
    /** 借款次数 */
    private String                count;
    /** 信用额度 */
    private String                total;
    /** 费率列表 */
    private List<String>          interests;
    /** 资金列表 */
    private List<String>          creditList;
    /** 日期列表 */
    private List<String>          dayList;
    /** 银行卡ID */
    private String                cardId;
    /** 认证状态 */
    private HomeAuthRec           auth;
    /** 借款进度 */
    private List<LoanProgressRec> list;
    /** 是否可还款 */
    private boolean               isRepay;
    /** 是否设置交易密码 */
    private boolean               isPwd;
    /** 最新一笔借款信息 */
    private LoanRec               borrow;
    /** 今日限额 */
    private String                loanCeiling;
    /** 是否被拒 true被拒 */
    private boolean               isRefuse;

    public boolean isPwd() {
        return isPwd;
    }

    public String getCount() {
        return count;
    }

    public List<String> getInterests() {
        return interests;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public String getFee() {
        return fee;
    }

    public boolean isBorrow() {
        return isBorrow;
    }

    public String getMaxCredit() {
        return maxCredit;
    }

    public String getMaxDays() {
        return maxDays;
    }

    public String getMinCredit() {
        return minCredit;
    }

    public String getMinDays() {
        return minDays;
    }

    public String getTitle() {
        return title;
    }

    public String getTotal() {
        return total;
    }

    public List<String> getCreditList() {
        return creditList;
    }

    public List<String> getDayList() {
        return dayList;
    }

    public String getCardId() {
        return cardId;
    }

    public HomeAuthRec getAuth() {
        return auth;
    }

    public List<LoanProgressRec> getList() {
        return list;
    }

    public LoanRec getBorrow() {
        return borrow;
    }

    public boolean isRepay() {
        return isRepay;
    }

    public String getLoanCeiling() {
        return loanCeiling;
    }

    public boolean isRefuse() {
        return isRefuse;
    }
}
