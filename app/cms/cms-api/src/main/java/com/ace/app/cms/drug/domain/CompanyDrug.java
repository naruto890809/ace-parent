package com.ace.app.cms.drug.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseEntity;
import com.ace.framework.base.PrimaryKey;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
* 商业公司药品关联表
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
public class CompanyDrug extends BaseEntity {
    private static final long serialVersionUID = -8926364488718735126L;

	

	@PrimaryKey
	private String id;//   主键
    @Excel(name ="商业公司")
    private String companyName;//
	private String companyId;//   商业公司id
    @Excel(name ="品规名称")
    private String specDrugName;//   药品规格名称
	private String drugId;//   药品id
	private String specDrugId;//   药品规格id
    @Excel(name ="开始时间")
	private String startTimeText;//   开始时间
	private Date startTime;//   开始时间
    @Excel(name ="结束时间")
    private String endTimeText;//
	private Date endTime;//   结束时间
    @Excel(name ="中标价")
    private String biddingPriceText;//   中标价
    private BigDecimal biddingPrice;//   中标价
    @Excel(name ="扣率(%)")
    private String deductionRateText;//   扣率(1-100)
    private BigDecimal deductionRate;//   扣率(1-100)
    private String billingPriceText;//   开票价
    @Excel(name ="开票价，导入可不填写")
    private BigDecimal billingPrice;//   开票价

    private String approve;//   审核状态 审核中、通过（PASSED）

    ///////////////////////////////////////////

    private String approveText;//   审核状态 审核中（APPROVING）、通过（PASSED） 驳回 REFUSED

    private Date brightExecuteDate;//   明返执行日期
    private Date darkExecuteDate;//   暗返执行日期
    private Date startTimeCondition;//   开始时间
    private Date endTimeCondition;//   开始时间

    private Date orderDate;//   订单日期

    public Date getStartTimeCondition() {
        return startTimeCondition;
    }

    public void setStartTimeCondition(Date startTimeCondition) {
        this.startTimeCondition = startTimeCondition;
    }

    public Date getEndTimeCondition() {
        return endTimeCondition;
    }

    public void setEndTimeCondition(Date endTimeCondition) {
        this.endTimeCondition = endTimeCondition;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public CompanyDrug() {
    }

    public CompanyDrug(String companyId, String drugId, String specDrugId, Date brightExecuteDate, Date darkExecuteDate) {
        this.companyId = companyId;
        this.drugId = drugId;
        this.specDrugId = specDrugId;
        this.brightExecuteDate = brightExecuteDate;
        this.darkExecuteDate = darkExecuteDate;
    }

    public CompanyDrug(Date startTimeCondition, Date endTimeCondition,String companyId, String drugId, String specDrugId) {
        this.companyId = companyId;
        this.drugId = drugId;
        this.specDrugId = specDrugId;
        this.startTimeCondition = startTimeCondition;
        this.endTimeCondition = endTimeCondition;
    }

    public Date getBrightExecuteDate() {
        return brightExecuteDate;
    }

    public void setBrightExecuteDate(Date brightExecuteDate) {
        this.brightExecuteDate = brightExecuteDate;
    }

    public Date getDarkExecuteDate() {
        return darkExecuteDate;
    }

    public void setDarkExecuteDate(Date darkExecuteDate) {
        this.darkExecuteDate = darkExecuteDate;
    }

    public BigDecimal getBiddingPrice() {
        if (StringUtils.isNotBlank(biddingPriceText)){
            try {
                return new BigDecimal(biddingPriceText);
            } catch (Exception e) {
                return biddingPrice;
            }
        }

        return biddingPrice;
    }

    public BigDecimal getDeductionRate() {
        if (StringUtils.isNotBlank(deductionRateText)){
            try {
                return new BigDecimal(deductionRateText);
            } catch (Exception e) {
                return deductionRate;
            }
        }

        return deductionRate;
    }

    public BigDecimal getBillingPrice() {
        return billingPrice;
    }

    public String getBiddingPriceText() {
        return biddingPriceText;
    }

    public void setBiddingPriceText(String biddingPriceText) {
        this.biddingPriceText = biddingPriceText;
    }

    public String getDeductionRateText() {
        return deductionRateText;
    }

    public void setDeductionRateText(String deductionRateText) {
        this.deductionRateText = deductionRateText;
    }

    public String getBillingPriceText() {
        return billingPriceText;
    }

    public void setBillingPriceText(String billingPriceText) {
        this.billingPriceText = billingPriceText;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSpecDrugName() {
        return specDrugName;
    }

    public void setSpecDrugName(String specDrugName) {
        this.specDrugName = specDrugName;
    }

    public String getStartTimeText() {
        return startTimeText;
    }

    public void setStartTimeText(String startTimeText) {
        this.startTimeText = startTimeText;
    }

    public String getEndTimeText() {
        return endTimeText;
    }

    public void setEndTimeText(String endTimeText) {
        this.endTimeText = endTimeText;
    }

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }

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
	public Date getStartTime() {
	    return this.startTime;
	}
	public void setStartTime(Date startTime) {
	    this.startTime=startTime;
	}
	public Date getEndTime() {
	    return this.endTime;
	}
	public void setEndTime(Date endTime) {
	    this.endTime=endTime;
	}
	public void setBiddingPrice(BigDecimal biddingPrice) {
	    this.biddingPrice=biddingPrice;
	}
	public void setDeductionRate(BigDecimal deductionRate) {
	    this.deductionRate=deductionRate;
	}
	public void setBillingPrice(BigDecimal billingPrice) {
	    this.billingPrice=billingPrice;
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
}

