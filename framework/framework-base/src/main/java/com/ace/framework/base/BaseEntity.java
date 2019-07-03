package com.ace.framework.base;


import com.ace.framework.util.DateUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author WuZhiWei
 * @since 2015-11-03 17:24:13
 */
public abstract class BaseEntity implements Serializable{

    /*
    * 公司编码
    */
    protected String corpCode;
    protected String parentCorpCode;
    /*
     * 创建人
     */
    private String createBy;
    /*
     * 创建时间
     */
    private Date createTime;
    /*
     * 最后修改时间
    */
    private Date lastModifyTime;
    /*
     * 最后修改人
     */
    private String lastModifyBy;
    /*
     * 扩展字段
     */
    private Map<String, Object> extMap;
    private String createByName;
    private String createTimeText;
    private List<String> ids;
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public void setCreateTimeText(String createTimeText) {
        this.createTimeText = createTimeText;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    /**
     * String类型的创建时间
     */
    public String getCreateTimeText() {
        return createTime != null ? new SimpleDateFormat(DateUtil.DATE_PATTERN_yyyy_MM_dd_HH_MM)
                .format(createTime) : "";
    }

    /**
     * String类型的最后修改时间
     */
    public String getLastModifyTimeText() {
        return lastModifyTime != null ? new SimpleDateFormat(DateUtil.DATE_PATTERN_yyyy_MM_dd_HH_MM)
                .format(lastModifyTime) : "";
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getLastModifyBy() {
        return lastModifyBy;
    }

    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }

    public Map<String, Object> getExtMap() {
        return extMap;
    }

    public void setExtMap(Map<String, Object> extMap) {
        this.extMap = extMap;
    }

    public String getParentCorpCode() {
        return parentCorpCode;
    }

    public void setParentCorpCode(String parentCorpCode) {
        this.parentCorpCode = parentCorpCode;
    }
}