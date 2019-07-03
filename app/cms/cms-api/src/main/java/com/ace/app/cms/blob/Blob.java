package com.ace.app.cms.blob;

import com.ace.framework.base.BaseEntity;
import com.ace.framework.base.PrimaryKey;

import java.util.List;

/**
 * 大字段
 * @author WuZhiWei
 * @since 2015-12-15 14:23:34
 */
public class Blob extends BaseEntity{

    private static final long serialVersionUID = 4984984459358007970L;
    @PrimaryKey
    private String blobId;
    private String resourceId;
    private String columnName;
    private String tableName;
    private String txtValue;

    ////////////////////////以下为非持久化字段///////////////////////////////////
    private List<String> columnNames;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public String getBlobId() {
        return blobId;
    }

    public void setBlobId(String blobId) {
        this.blobId = blobId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTxtValue() {
        return txtValue;
    }

    public void setTxtValue(String txtValue) {
        this.txtValue = txtValue;
    }
}
