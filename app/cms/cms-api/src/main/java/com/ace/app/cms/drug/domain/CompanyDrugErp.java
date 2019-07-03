package com.ace.app.cms.drug.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseEntity;
import com.ace.framework.base.PrimaryKey;
import java.math.BigDecimal;
import java.util.Date;

/**
* 商业公司药品Erp
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-29 18:41:55
*/
public class CompanyDrugErp extends BaseEntity {
    private static final long serialVersionUID = -7949787820014749238L;

	

	@PrimaryKey
	private String id;//   主键
	private String companyId;//   商业公司id
	private String drugId;//   药品id
	private String specDrugId;//   药品规格id
	//private Integer inCount;//
	//private Integer outCount;//

    private BigDecimal inCount;//
    private BigDecimal outCount;//
	public String getId() {
	    return this.id;
	}
	public void setId(String id) {
	    this.id=id;
	}
	public String getCompanyId() {
	    return this.companyId;
	}
	public void setCompanyId(String companyId) {
	    this.companyId=companyId;
	}
	public String getDrugId() {
	    return this.drugId;
	}
	public void setDrugId(String drugId) {
	    this.drugId=drugId;
	}
	public String getSpecDrugId() {
	    return this.specDrugId;
	}
	public void setSpecDrugId(String specDrugId) {
	    this.specDrugId=specDrugId;
	}
	//public Integer getInCount() {
	//    return this.inCount;
	//}
	//public void setInCount(Integer inCount) {
	 //   this.inCount=inCount;
	//}

    public BigDecimal getInCountD() {
        return inCount;
    }

    public BigDecimal getOutCountD() {
        return outCount;
    }

    public void setOutCountD(BigDecimal outCountD) {
        this.outCount = outCountD;
    }

    public void setInCountD(BigDecimal inCountD) {
        this.inCount = inCountD;
    }

   // public Integer getOutCount() {
	 //   return this.outCount;
	//}
	//public void setOutCount(Integer outCount) {
	//    this.outCount=outCount;
	//}


	public CompanyDrugErp() {
	}

	public CompanyDrugErp(String companyId, String drugId, String specDrugId) {
		this.companyId = companyId;
		this.drugId = drugId;
		this.specDrugId = specDrugId;
	}
}

