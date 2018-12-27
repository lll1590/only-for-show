package com.beiyongjin.byg.common;

/**
 * Author: Chenming
 * E-mail: cm1@erongdu.com
 * Date: 2016/11/22 下午2:56
 * <p/>
 * Description:
 */
public class KeyValueBean {
    /** 唯一标示 */
    private long   id;
    /** 父级标示 */
    private long   parentId;
    /** 值 */
    private String value;

    public KeyValueBean(long id , long parentId, String value){
        this.id = id;
        this.parentId = parentId;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPickerViewText() {
        if (value.length() > 4) {
            return value.substring(0, 4);
        }
        return value;
    }
}
