package com.ace.app.cms.drug.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseEntity;
import com.ace.framework.base.PrimaryKey;
import java.math.BigDecimal;
import java.util.Date;

/**
* 任务管理
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-10-18 21:27:37
*/
public class DepartmentJob extends BaseEntity {
    private static final long serialVersionUID = -3549926478862818724L;

	

	@PrimaryKey
	private String id;//   主键
	private String departmentId;//   部门id
	private String drugId;//   药品id
	private Integer jobAmount;//   任务量
	private Integer year;//   年份


    ////////////////////////////////////////
    private String drugName;

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getId() {
	    return this.id;
	}
	public void setId(String id) {
	    this.id=id;
	}
	public String getDepartmentId() {
	    return this.departmentId;
	}
	public void setDepartmentId(String departmentId) {
	    this.departmentId=departmentId;
	}
	public String getDrugId() {
	    return this.drugId;
	}
	public void setDrugId(String drugId) {
	    this.drugId=drugId;
	}
	public Integer getJobAmount() {
	    return this.jobAmount;
	}
	public void setJobAmount(Integer jobAmount) {
	    this.jobAmount=jobAmount;
	}
	public Integer getYear() {
	    return this.year;
	}
	public void setYear(Integer year) {
	    this.year=year;
	}

}

