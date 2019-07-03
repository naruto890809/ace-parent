package com.ace.app.cms.drug.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseEntity;
import com.ace.framework.base.PrimaryKey;
import com.ace.framework.util.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
* 返利设置
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
public class Rebate extends BaseEntity {
    private static final long serialVersionUID = 105575618121247391L;

	

	@PrimaryKey
	private String id;//   主键
    @Excel(name ="商业公司")
    private String companyName;//
    @Excel(name ="末级商业公司")
    private String companyLast;//
	private String companyId;//   商业公司id
    @Excel(name ="医院")
    private String hospitalName;//   医院id
	private String hospitalId;//   医院id
    @Excel(name ="品规名称")
    private String specDrugName;//   药品规格名称

    @Excel(name ="议价主体")
    private String priceTopicName;//   议价主体  来自字典表，编号是：REBATE_PRICE_TOPIC
	private String priceTopic;//   议价主体  来自字典表，编号是：REBATE_PRICE_TOPIC 单体医院、医联体、地区、商业公司
    @Excel(name ="执行价")
    private String executePriceText;//   执行价（手动填写）
    private BigDecimal executePrice;//   执行价（手动填写）

    @Excel(name ="明返执行日期")
    private String brightExecuteDateText;
    private Date brightExecuteDate;//   明返执行日期
    @Excel(name ="暗返单价")
    private String darkPriceText;//   暗返单价
    private BigDecimal darkPrice;//   暗返单价
    @Excel(name ="暗返执行日期")
    private String darkExecuteDateText;//   暗返执行日期
	private Date darkExecuteDate;//   暗返执行日期
    @Excel(name ="返利形式")
    private String rebateStyleName;//   返利形式  来自字典表，编号是：REBATE_STYLE
	private String rebateStyle;//   返利形式  来自字典表，编号是：REBATE_STYLE  现金、折让


    @Excel(name ="明返单价（导入时可不填）")
    private String brightPriceText;//   明返单价
    private BigDecimal brightPrice;//   明返单价
    @Excel(name ="中标价（导入时可不填）" ,isImportField = "false")
    private BigDecimal biddingPrice;//   中标价（根据品规从t_cms_company_drug表中查出）
    @Excel(name ="返利单价（导入时可不填）" ,isImportField = "false")
    private BigDecimal rebatePrice;//   返利单价 明+暗 自动计算
    private String drugId;//   药品id
    private String specDrugId;//   药品规格id

    @Excel(name ="补差性质（导入时可不填）" ,isImportField = "false")
    private String difType;//   补差性质自动判断  明 暗 明+暗
    @Excel(name ="返利点数（导入时可不填）" ,isImportField = "false")
    private BigDecimal rebateRate;//   返利点数 返利单价/中标价  每次动态查了计算



    private String approve;//   审核状态 审核中、通过（PASSED）

	///////////////////////////////////////////

	private String approveText;//   审核状态 审核中（APPROVING）、通过（PASSED） 驳回 REFUSED
    private Date orderDate;
    @Excel(name ="医院区域（导入时可不填）" ,isImportField = "false")
    private String hospitalArea;
    @Excel(name ="医院所在部门（导入时可不填）" ,isImportField = "false")
    private String hospitalDeptName;
    private String approveCheck;//   药品规格id

    public String getApproveCheck() {
        return approveCheck;
    }

    public void setApproveCheck(String approveCheck) {
        this.approveCheck = approveCheck;
    }

    public String getCompanyLast() {
        return companyLast;
    }

    public void setCompanyLast(String companyLast) {
        this.companyLast = companyLast;
    }

    public String getHospitalArea() {
        return hospitalArea;
    }

    public void setHospitalArea(String hospitalArea) {
        this.hospitalArea = hospitalArea;
    }

    public String getHospitalDeptName() {
        return hospitalDeptName;
    }

    public void setHospitalDeptName(String hospitalDeptName) {
        this.hospitalDeptName = hospitalDeptName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDarkExecuteDate() {
        if (StringUtils.isNotBlank(darkExecuteDateText)){
            darkExecuteDate = DateUtil.convert(darkExecuteDateText);
        }
        return this.darkExecuteDate;
    }
    public Date getBrightExecuteDate() {
        if (StringUtils.isNotBlank(brightExecuteDateText)){
            brightExecuteDate = DateUtil.convert(brightExecuteDateText);
        }

        return this.brightExecuteDate;
    }

    public BigDecimal getExecutePrice() {
        if (StringUtils.isNotBlank(executePriceText)){
            try {
                return new BigDecimal(executePriceText);
            } catch (Exception e) {
                return executePrice;
            }
        }

        return executePrice;
    }


    public BigDecimal getBrightPrice() {
        if (StringUtils.isNotBlank(brightPriceText)){
            try {
                return new BigDecimal(brightPriceText);
            } catch (Exception e) {
                return brightPrice;
            }
        }

        return brightPrice;
    }

    public BigDecimal getDarkPrice() {
        if (StringUtils.isNotBlank(darkPriceText)){
            try {
                return new BigDecimal(darkPriceText);
            } catch (Exception e) {
                return darkPrice;
            }
        }

        return darkPrice;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getPriceTopicName() {
        return priceTopicName;
    }

    public void setPriceTopicName(String priceTopicName) {
        this.priceTopicName = priceTopicName;
    }

    public String getExecutePriceText() {
        return executePriceText;
    }

    public void setExecutePriceText(String executePriceText) {
        this.executePriceText = executePriceText;
    }

    public String getBrightPriceText() {
        return brightPriceText;
    }

    public void setBrightPriceText(String brightPriceText) {
        this.brightPriceText = brightPriceText;
    }

    public String getBrightExecuteDateText() {
        return brightExecuteDateText;
    }

    public void setBrightExecuteDateText(String brightExecuteDateText) {
        this.brightExecuteDateText = brightExecuteDateText;
    }

    public String getDarkPriceText() {
        return darkPriceText;
    }

    public void setDarkPriceText(String darkPriceText) {
        this.darkPriceText = darkPriceText;
    }

    public String getDarkExecuteDateText() {
        return darkExecuteDateText;
    }

    public void setDarkExecuteDateText(String darkExecuteDateText) {
        this.darkExecuteDateText = darkExecuteDateText;
    }

    public String getRebateStyleName() {
        return rebateStyleName;
    }

    public void setRebateStyleName(String rebateStyleName) {
        this.rebateStyleName = rebateStyleName;
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
	public String getDifType() {
	    return this.difType;
	}
	public void setDifType(String difType) {
	    this.difType=difType;
	}
	public String getPriceTopic() {
	    return this.priceTopic;
	}
	public void setPriceTopic(String priceTopic) {
	    this.priceTopic=priceTopic;
	}
	public BigDecimal getBiddingPrice() {
	    return this.biddingPrice;
	}
	public void setBiddingPrice(BigDecimal biddingPrice) {
	    this.biddingPrice=biddingPrice;
	}
	public void setExecutePrice(BigDecimal executePrice) {
	    this.executePrice=executePrice;
	}
	public void setBrightPrice(BigDecimal brightPrice) {
	    this.brightPrice=brightPrice;
	}

	public void setBrightExecuteDate(Date brightExecuteDate) {
	    this.brightExecuteDate=brightExecuteDate;
	}
	public void setDarkPrice(BigDecimal darkPrice) {
	    this.darkPrice=darkPrice;
	}

	public void setDarkExecuteDate(Date darkExecuteDate) {
	    this.darkExecuteDate=darkExecuteDate;
	}
	public BigDecimal getRebatePrice() {
	    return this.rebatePrice;
	}
	public void setRebatePrice(BigDecimal rebatePrice) {
	    this.rebatePrice=rebatePrice;
	}
	public BigDecimal getRebateRate() {
	    return this.rebateRate;
	}
	public void setRebateRate(BigDecimal rebateRate) {
	    this.rebateRate=rebateRate;
	}
	public String getRebateStyle() {
	    return this.rebateStyle;
	}
	public void setRebateStyle(String rebateStyle) {
	    this.rebateStyle=rebateStyle;
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
		}else if (CmsConstant.FREEZED.equals(approve)){
		    approveText = CmsConstant.FREEZED_TEXT;
		}else {
		    approveText = "未定义";
		}
		return approveText;
	}
		
	public void setApproveText(String approveText) {
		this.approveText = approveText;
	}

}

