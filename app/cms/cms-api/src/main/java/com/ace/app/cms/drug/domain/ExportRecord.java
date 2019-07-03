package com.ace.app.cms.drug.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseEntity;
import com.ace.framework.base.PrimaryKey;
/**
* 导入记录
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
public class ExportRecord extends BaseEntity {
    private static final long serialVersionUID = -2852384543147182244L;

	

	@PrimaryKey
	private String id;//   主键
	private String fileName;//   文件名
	private Integer exportCnt;//   导入条数|操作人|操作时间
	public String getId() {
	    return this.id;
	}
	public void setId(String id) {
	    this.id=id;
	}
	public String getFileName() {
	    return this.fileName;
	}
	public void setFileName(String fileName) {
	    this.fileName=fileName;
	}
	public Integer getExportCnt() {
	    return this.exportCnt;
	}
	public void setExportCnt(Integer exportCnt) {
	    this.exportCnt=exportCnt;
	}

	public ExportRecord() {
	}

	public ExportRecord(String fileName, Integer exportCnt) {
		this.fileName = fileName;
		this.exportCnt = exportCnt;
	}
}

