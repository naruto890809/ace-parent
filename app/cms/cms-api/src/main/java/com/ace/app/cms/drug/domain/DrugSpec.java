package com.ace.app.cms.drug.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseEntity;
import com.ace.framework.base.PrimaryKey;

import java.math.BigDecimal;

/**
* 药品规格
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
public class DrugSpec extends BaseEntity {
    private static final long serialVersionUID = 6979695110676162184L;

	

	@PrimaryKey
	private String id;//   主键
	private String specName;//   规格名称
	private String drugId;//   所属产品
	private String code;//   产品代码
	private BigDecimal coefficient;//   系数

    ///////////////////

    private String drugName;
    private String drugAliasName;

	public String getDrugAliasName() {
		return drugAliasName;
	}

	public void setDrugAliasName(String drugAliasName) {
		this.drugAliasName = drugAliasName;
	}

	public BigDecimal getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(BigDecimal coefficient) {
		this.coefficient = coefficient;
	}

	public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public DrugSpec() {
    }

    public DrugSpec(String specName, String drugId) {
        this.specName = specName;
        this.drugId = drugId;
    }

	public DrugSpec(String specName, String drugId, BigDecimal coefficient) {
		this.specName = specName;
		this.drugId = drugId;
		this.coefficient = coefficient;
	}

	public String getId() {
	    return this.id;
	}
	public void setId(String id) {
	    this.id=id;
	}
	public String getSpecName() {
	    return this.specName;
	}
	public void setSpecName(String specName) {
	    this.specName=specName;
	}
	public String getDrugId() {
	    return this.drugId;
	}
	public void setDrugId(String drugId) {
	    this.drugId=drugId;
	}
	public String getCode() {
	    return this.code;
	}
	public void setCode(String code) {
	    this.code=code;
	}

}

