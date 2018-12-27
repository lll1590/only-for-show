package com.beiyongjin.byg.module.home.dataModel;

/**
 * Author: JinFu
 * E-mail: jf@erongdu.com
 * Date: 2017/2/24 14:40
 * <p>
 * Description:首页认证
 */
public class HomeAuthRec {
    /**
     * qualified : 1
     * result : 0
     * total : 5
     */
    private String qualified;
    private String result;
    private String total;

    public String getQualified() {
        return qualified;
    }

    public void setQualified(String qualified) {
        this.qualified = qualified;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
