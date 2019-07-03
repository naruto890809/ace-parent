package com.ace.app.cms.common;

import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 导入时，选择字段对应表头时用到
 */

public class ImportEntity {
    private String url;
    private String id;
    private String headName;//表头名称
    private String fieldName;//属性名称
    private String field;//属性
    private String demo;//示例数据
    private Integer defined;//是否为自定义
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private List<ImportEntity> child;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDemo() {
        return demo;
    }

    public void setDemo(String demo) {
        this.demo = demo;
    }

    public Integer getDefined() {
        return defined;
    }

    public void setDefined(Integer defined) {
        this.defined = defined;
    }

    public List<ImportEntity> getChild() {
        return child;
    }

    public void setChild(List<ImportEntity> child) {
        this.child = child;
    }

    public ImportEntity() {
    }

    public ImportEntity(String headName, String demo) {
        this.headName = headName;
        this.demo = demo;
    }

    public ImportEntity(String fieldName, String field,Integer defined) {
        this.fieldName = fieldName;
        this.field = field;
        this.defined = defined;
    }


}
