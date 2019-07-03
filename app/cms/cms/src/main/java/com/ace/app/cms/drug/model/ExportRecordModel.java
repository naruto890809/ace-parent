package com.ace.app.cms.drug.model;


import com.ace.app.cms.drug.domain.ExportRecord;
import com.ace.framework.base.BaseModel;
import com.ace.framework.util.ExecutionContext;

/**
* 导入记录
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
public class ExportRecordModel extends BaseModel {
    private ExportRecord exportRecord;



    public ExportRecord getExportRecord() {

        return exportRecord;
    }

    public void setExportRecord(ExportRecord exportRecord) {
        exportRecord.setParentCorpCode(ExecutionContext.getParentCorpCode());
        exportRecord.setCorpCode(ExecutionContext.getCorpCode());

        this.exportRecord = exportRecord;
    }
}
