package com.ace.app.cms.common;


import com.ace.app.cms.drug.domain.Order;
import com.ace.framework.base.BaseModel;

/**
* 业务管理
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-06 20:56:29
*/
public class CommonModel extends BaseModel {
    private String fileName;
    private String fileUrl;
    private String exportClass;
    private ImportEntity importEntity;
    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ImportEntity getImportEntity() {
        return importEntity;
    }

    public void setImportEntity(ImportEntity importEntity) {
        this.importEntity = importEntity;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExportClass() {
        return exportClass;
    }

    public void setExportClass(String exportClass) {
        this.exportClass = exportClass;
    }
}
