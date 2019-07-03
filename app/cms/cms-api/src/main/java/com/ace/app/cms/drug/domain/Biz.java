package com.ace.app.cms.drug.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseEntity;
import com.ace.framework.base.PrimaryKey;

import java.util.Date;
import java.util.List;

/**
* 业务管理
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:30
*/
public class Biz extends BaseEntity {
    private static final long serialVersionUID = -1492214139867256941L;


    ////////////////////////////////
    @Excel(name ="业务员姓名")
    private String salesmanName;//   业务员姓名
    @Excel(name ="医院名称")
    private String hospitalName;//   医院名称
    @Excel(name ="品规名称")
    private String specDrugName;//   药品规格名称
    @Excel(name ="类型（申请、交接）")
    private String type;//   审核状态 审核中、通过（PASSED）
    @Excel(name ="考核量")
    private Integer amount;//   审核状态 审核中、通过（PASSED）

    @Excel(name ="销售时间段")
    private String saleDateText;//   销售时间段
    @Excel(name ="跟踪开始时间")
    private String traceStartDateText;//   跟踪开始时间
    @Excel(name ="跟踪结束时间")
    private String traceEndDateText;//   跟踪结束时间（默认开始加六个月）
    ////////////////////////////////

	@PrimaryKey
	private String id;//   主键
	private String salesmanId;//   业务员id
	private String hospitalId;//   医院id
	private String drugId;//   药品id
	private String specDrugId;//   药品规格id

    private Date saleDate;//   销售时间段
    private Date traceStartDate;//   跟踪开始时间
    private Date traceEndDate;//   跟踪结束时间（默认开始加六个月）
    //@Excel(name ="备注")
    private String note;//   备注
	private String approve;//   审核状态 审核中、通过（PASSED）

	///////////////////////////////////////////

	private String approveText;//   审核状态 审核中（APPROVING）、通过（PASSED） 驳回 REFUSED
    private Date conditionStartDate;
    private Date conditionEndDate;
    private Date orderDate;

    private String departmentId;
    private List<String> salesmanIds;

    private String editCheck;//   审核状态 审核中（APPROVING）、通过（PASSED） 驳回 REFUSED

    public String getEditCheck() {
        return editCheck;
    }

    public void setEditCheck(String editCheck) {
        this.editCheck = editCheck;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<String> getSalesmanIds() {
        return salesmanIds;
    }

    public void setSalesmanIds(List<String> salesmanIds) {
        this.salesmanIds = salesmanIds;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Biz() {
    }

    public Biz(String hospitalId, String drugId, String specDrugId, Date conditionStartDate, Date conditionEndDate) {
        this.hospitalId = hospitalId;
        this.drugId = drugId;
        this.specDrugId = specDrugId;
        this.conditionStartDate = conditionStartDate;
        this.conditionEndDate = conditionEndDate;
    }


    public Biz(Date saleDate,String hospitalId, String drugId, String specDrugId) {
        this.hospitalId = hospitalId;
        this.drugId = drugId;
        this.specDrugId = specDrugId;
        this.saleDate = saleDate;
    }

    public Biz(String hospitalId, String drugId, String specDrugId, Date orderDate) {
        this.hospitalId = hospitalId;
        this.drugId = drugId;
        this.specDrugId = specDrugId;
        this.orderDate = orderDate;
    }

    public Date getConditionStartDate() {
        return conditionStartDate;
    }

    public void setConditionStartDate(Date conditionStartDate) {
        this.conditionStartDate = conditionStartDate;
    }

    public Date getConditionEndDate() {
        return conditionEndDate;
    }

    public void setConditionEndDate(Date conditionEndDate) {
        this.conditionEndDate = conditionEndDate;
    }

    public String getSaleDateText() {
        return saleDateText;
    }

    public void setSaleDateText(String saleDateText) {
        this.saleDateText = saleDateText;
    }

    public String getTraceStartDateText() {
        return traceStartDateText;
    }

    public void setTraceStartDateText(String traceStartDateText) {
        this.traceStartDateText = traceStartDateText;
    }

    public String getTraceEndDateText() {
        return traceEndDateText;
    }

    public void setTraceEndDateText(String traceEndDateText) {
        this.traceEndDateText = traceEndDateText;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getSpecDrugName() {
        return specDrugName;
    }

    public void setSpecDrugName(String specDrugName) {
        this.specDrugName = specDrugName;
    }

    public String getId() {
	    return this.id;
	}
	public void setId(String id) {
	    this.id=id;
	}
	public String getSalesmanId() {
	    return this.salesmanId;
	}
	public void setSalesmanId(String salesmanId) {
	    this.salesmanId=salesmanId;
	}
	public String getHospitalId() {
	    return this.hospitalId;
	}
	public void setHospitalId(String hospitalId) {
	    this.hospitalId=hospitalId;
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
	public Date getSaleDate() {
	    return this.saleDate;
	}
	public void setSaleDate(Date saleDate) {
	    this.saleDate=saleDate;
	}
	public Date getTraceStartDate() {
	    return this.traceStartDate;
	}
	public void setTraceStartDate(Date traceStartDate) {
	    this.traceStartDate=traceStartDate;
	}
	public Date getTraceEndDate() {
	    return this.traceEndDate;
	}
	public void setTraceEndDate(Date traceEndDate) {
	    this.traceEndDate=traceEndDate;
	}
	public String getNote() {
	    return this.note;
	}
	public void setNote(String note) {
	    this.note=note;
	}
	public String getApprove() {
	    return this.approve;
	}
	public void setApprove(String approve) {
	    this.approve=approve;
	}

	public String getApproveText() {
		if (CmsConstant.APPROVING.equals(approve)){
		    approveText = CmsConstant.APPROVING_TEXT;
		}else if (CmsConstant.PASSED.equals(approve)){
		    approveText = CmsConstant.PASSED_TEXT;
		}else if (CmsConstant.REFUSED.equals(approve)){
		    approveText = CmsConstant.REFUSED_TEXT;
		}else {
		    approveText = "未定义";
		}
		return approveText;
	}
		
	public void setApproveText(String approveText) {
		this.approveText = approveText;
	}

    @Override
    public String toString() {
        return "Biz{" +
                "salesmanName='" + salesmanName + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", specDrugName='" + specDrugName + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", saleDateText='" + saleDateText + '\'' +
                ", traceStartDateText='" + traceStartDateText + '\'' +
                ", traceEndDateText='" + traceEndDateText + '\'' +
                ", id='" + id + '\'' +
                ", salesmanId='" + salesmanId + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                ", drugId='" + drugId + '\'' +
                ", specDrugId='" + specDrugId + '\'' +
                ", saleDate=" + saleDate +
                ", traceStartDate=" + traceStartDate +
                ", traceEndDate=" + traceEndDate +
                ", note='" + note + '\'' +
                ", approve='" + approve + '\'' +
                ", approveText='" + approveText + '\'' +
                ", conditionStartDate=" + conditionStartDate +
                ", conditionEndDate=" + conditionEndDate +
                ", orderDate=" + orderDate +
                ", departmentId='" + departmentId + '\'' +
                ", salesmanIds=" + salesmanIds +
                ", editCheck='" + editCheck + '\'' +
                '}';
    }
}

