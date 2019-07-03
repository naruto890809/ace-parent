package com.ace.app.cms.drug.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseEntity;
import com.ace.framework.base.PrimaryKey;
import com.ace.framework.util.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
* 流向清单
*
* WuZhiWei v1.00
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
public class Order extends BaseEntity {
    private static final long serialVersionUID = -4252420885929541544L;

	

	@PrimaryKey
	private String id;//   主键
    private String drugName;//   药品
    private String specName;//   规格
	@Excel(name ="品规名称")
	private String drugSpecName;//   品规
    @Excel(name ="批号")
    private String drugNum;//   批号
    @Excel(name ="数量")//Easypoi导出类型 1 是文本 2 是图片,3 是函数,10 是数字 默认是文本
    private String drugCntText;//   数量
    private BigDecimal drugCnt;//   数量
    private BigDecimal coefficient;//   换算之后的数量
    private String hospitalName;//   医院名称
	@Excel(name ="医院原始名称")
    private String hospitalPreName;//   医院名称
	@Excel(name ="医院原始地址")
    private String hospitalPreAddre;//   医院名称

    private Date orderDate;//   日期
	@Excel(name ="日期")
	private String orderDateText;//   日期

    private BigDecimal price;//   单价（终端开票价）
	@Excel(name ="单价")
	private String priceText;//   单价（终端开票价）

	@Excel(name ="商业公司")
    private String companyName;//   商业公司
	@Excel(name ="分公司")
	private String branchCompany;//   分公司
	private String branchCompanyName;//   分公司  现在作为导入记录主键
	private Integer year;//   年
	private Integer month;//   月
	private Integer day;//   日


    //商业公司
    private String companyId;//   商业公司ID
    private String companySellAreaId;//   商业公司所属销售区域市
    private String companyArea;//   商业公司所属销售区域 省
    private String companyDrugId;//   商业公司药品关联表id（根据商业公司、药品、规格id及有效时间段从业务表查出）
    private BigDecimal deductionRate;//   商业公司药品关联表扣率
    private BigDecimal biddingPrice;//   商业公司药品关联表中标价（医保支付价）
    private BigDecimal billingPrice;//   商业公司药品关联表开票价（开票价）



    //医院
    private String hospitalId;//   医院ID
    private String deptmentId;//   医院所属推广部id
    private String departmentName;//   医院所属推广部名称
    private String hospitalArea;//   医院所在行政区域（省市区|县）
    private String hospitalCity;//   医院所在行政区域（省市区|县）
    private String hospitalProvince;//   医院所在行政区域（省市区|县）
    private String hospitalLavel;//   医院等级
    private String hospitalType;//   医院性质


    //返利
    private BigDecimal rebateRate;//   返利信息表-返利点数
    private String rebateId;//   返利信息表id（根据商业公司、药品、规格、医院id从返利表查出）
    private BigDecimal brightPrice;//   返利信息表-明返单价
    private BigDecimal darkPrice;//   返利信息表-暗返单价
    private BigDecimal rebatePrice;//   返利信息表-返利单价（明+暗）
    private BigDecimal executePrice;//   返利信息表-执行价
	private String priceTopicName;//统计维度

    //0.正常
    //1.不在返利信息表内的医院，性质为医院的，终端开票价不等于医保支付价
    //2.(不是医院的，医院性质作为筛选条件,品规一致)终端开票价格小于开票价为异常
    //3.(医院名称在返利信息表内,品规一致)终端开票价格不等于返利信息表中的执行价则为异常
    private String status;//   状态，正常0、医保支付价异常、终端开票价异常

    //业务员
    private String salesmanName;//   业务员姓名
    private String salesmanId;//   业务员id（根据医院、药品、规格id及跟踪时间段从业务表查出）


    private BigDecimal actualPrice;//   实际结算价【(中标价格-返利单价)*(1-商业扣率)】
	private BigDecimal totalMoney;//   销售金额【实际结算价格*数量】


	private String drugId;//   药品Id
	private String specId;//   规格Id




    private String drugCode;//   药品代码


	//////////////////////

	private String statusText;//   状态，正常0、医保支付价异常、终端开票价异常
	private String statusText1;//   状态，正常0、医保支付价异常、终端开票价异常
	private Date startTime;
	private Date endTime;
	private String amountOrMoney;
	private String tb;
	private String hb;
	private String reportType;//报表类型  数量 coefficient  金额 total_money

	private BigDecimal taskCnt;//任务量
	private String monthTb;//当月同比
	private BigDecimal yearCnt;//当年销量
	private String yearTb;//当年同比
	private BigDecimal completeRatio;//完成率
	private BigDecimal dif;//差量
	private String endTimeText;
	private String startTimeText;
	private String cxAddCnt;//纯销环比增长量
	private String ratio;//百分比
	private String cx;//是否统计纯销   YES 统计
	private List<String> hospIds;

	private List<String> drugIds;
	private List<String> specIds;
	private List<String> salesmanIds;
	private String orderBy;//排序类型
	private String summaryType;//类型  整体  纯销  零售

	private String reportDif;//统计维度
	private String exportType;//统计维度
	private String reportDifName;//统计维度
	private String totalMoneyText;//统计维度
	private String bigArea;//统计维度
	private String traceRange;//统计维度
	private Date createStart;//统计维度
	private Date createEnd;//统计维度
	private List<String> deptIds;
	private Integer limitCnt;
	private String hospitalNameSplit;
	private String creater;
	private List<String> hospitalNameList;


    private String departmentNameSplit;
    private List<String> departmentNameList;

    public List<String> getDepartmentNameList() {
        if (StringUtils.isNotBlank(departmentNameSplit)){
            departmentNameList = Arrays.asList(departmentNameSplit.split(",|，|, "));
        }
        return departmentNameList;
    }

    public void setDepartmentNameList(List<String> departmentNameList) {
        this.departmentNameList = departmentNameList;
    }

    public String getDepartmentNameSplit() {
        return departmentNameSplit;
    }

    public void setDepartmentNameSplit(String departmentNameSplit) {
        this.departmentNameSplit = departmentNameSplit;
    }



	public String getBranchCompany() {
		return branchCompany;
	}

	public void setBranchCompany(String branchCompany) {
		this.branchCompany = branchCompany;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public List<String> getHospitalNameList() {
		if (StringUtils.isNotBlank(hospitalNameSplit)){
			hospitalNameList = Arrays.asList(hospitalNameSplit.split("\\s+"));
		}
		return hospitalNameList;
	}

	public void setHospitalNameList(List<String> hospitalNameList) {
		this.hospitalNameList = hospitalNameList;
	}

	public String getHospitalNameSplit() {
		return hospitalNameSplit;
	}

	public void setHospitalNameSplit(String hospitalNameSplit) {
		this.hospitalNameSplit = hospitalNameSplit;
	}

	public String getPriceTopicName() {
		return priceTopicName;
	}

	public void setPriceTopicName(String priceTopicName) {
		this.priceTopicName = priceTopicName;
	}

	public String getHospitalPreName() {
		return hospitalPreName;
	}

	public void setHospitalPreName(String hospitalPreName) {
		this.hospitalPreName = hospitalPreName;
	}

	public String getHospitalPreAddre() {
		return hospitalPreAddre;
	}

	public void setHospitalPreAddre(String hospitalPreAddre) {
		this.hospitalPreAddre = hospitalPreAddre;
	}

	public Integer getLimitCnt() {
		return limitCnt;
	}

	public void setLimitCnt(Integer limitCnt) {
		this.limitCnt = limitCnt;
	}

	public String getTraceRange() {
		return traceRange;
	}

	public void setTraceRange(String traceRange) {
		this.traceRange = traceRange;
	}

	public List<String> getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(List<String> deptIds) {
		this.deptIds = deptIds;
	}

	public String getBigArea() {
		return bigArea;
	}

	public void setBigArea(String bigArea) {
		this.bigArea = bigArea;
	}

	public Date getCreateStart() {
        return createStart;
    }

    public void setCreateStart(Date createStart) {
        this.createStart = createStart;
    }

    public Date getCreateEnd() {
        return createEnd;
    }

    public void setCreateEnd(Date createEnd) {
        this.createEnd = createEnd;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public String getTotalMoneyText() {
        return totalMoneyText;
    }

    public void setTotalMoneyText(String totalMoneyText) {
        this.totalMoneyText = totalMoneyText;
    }

    public String getReportDifName() {
        return reportDifName;
    }

    public void setReportDifName(String reportDifName) {
        this.reportDifName = reportDifName;
    }

    public String getHospitalProvince() {
        return hospitalProvince;
    }

    public void setHospitalProvince(String hospitalProvince) {
        this.hospitalProvince = hospitalProvince;
    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public String getReportDif() {
        return reportDif;
    }

    public void setReportDif(String reportDif) {
        this.reportDif = reportDif;
    }

    public String getHospitalCity() {
		return hospitalCity;
	}

	public void setHospitalCity(String hospitalCity) {
		this.hospitalCity = hospitalCity;
	}

	public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSummaryType() {
        return summaryType;
    }

    public void setSummaryType(String summaryType) {
        this.summaryType = summaryType;
    }

    public List<String> getDrugIds() {
		return drugIds;
	}

	public void setDrugIds(List<String> drugIds) {
		this.drugIds = drugIds;
	}

	public List<String> getSpecIds() {
		return specIds;
	}

	public void setSpecIds(List<String> specIds) {
		this.specIds = specIds;
	}

	public List<String> getSalesmanIds() {
		return salesmanIds;
	}

	public void setSalesmanIds(List<String> salesmanIds) {
		this.salesmanIds = salesmanIds;
	}

	public String getStartTimeText() {
		return startTimeText;
	}

	public void setStartTimeText(String startTimeText) {
		this.startTimeText = startTimeText;
	}

	public String getCxAddCnt() {
		return cxAddCnt;
	}

	public void setCxAddCnt(String cxAddCnt) {
		this.cxAddCnt = cxAddCnt;
	}

	public String getCx() {
		return cx;
	}

	public void setCx(String cx) {
		this.cx = cx;
	}

	public List<String> getHospIds() {
		return hospIds;
	}

	public void setHospIds(List<String> hospIds) {
		this.hospIds = hospIds;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}


	public String getEndTimeText() {
		return endTimeText;
	}

	public void setEndTimeText(String endTimeText) {
		this.endTimeText = endTimeText;
	}

	public BigDecimal getTaskCnt() {
		return taskCnt;
	}

	public void setTaskCnt(BigDecimal taskCnt) {
		this.taskCnt = taskCnt;
	}

	public String getMonthTb() {
		return monthTb;
	}

	public void setMonthTb(String monthTb) {
		this.monthTb = monthTb;
	}

	public BigDecimal getYearCnt() {
		return yearCnt;
	}

	public void setYearCnt(BigDecimal yearCnt) {
		this.yearCnt = yearCnt;
	}

	public String getYearTb() {
		return yearTb;
	}

	public void setYearTb(String yearTb) {
		this.yearTb = yearTb;
	}

	public BigDecimal getCompleteRatio() {
		return completeRatio;
	}

	public void setCompleteRatio(BigDecimal completeRatio) {
		this.completeRatio = completeRatio;
	}

	public BigDecimal getDif() {
		return dif;
	}

	public void setDif(BigDecimal dif) {
		this.dif = dif;
	}

	public BigDecimal getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(BigDecimal coefficient) {
		this.coefficient = coefficient;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getAmountOrMoney() {
		return amountOrMoney;
	}

	public void setAmountOrMoney(String amountOrMoney) {
		this.amountOrMoney = amountOrMoney;
	}

	public String getTb() {
		return tb;
	}

	public void setTb(String tb) {
		this.tb = tb;
	}

	public String getHb() {
		return hb;
	}

	public void setHb(String hb) {
		this.hb = hb;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

    public String getStatusText1() {
        if ("0".equals(status)){
            return "正常";
        }

        return "<span style='color:red'>异常</span>";
    }

    public void setStatusText1(String statusText1) {
        this.statusText1 = statusText1;
    }

    public String getStatusText() {
		if ("0".equals(status)){
			return "正常";
		}

		return "异常";
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public BigDecimal getExecutePrice() {
        return executePrice;
    }

    public void setExecutePrice(BigDecimal executePrice) {
        this.executePrice = executePrice;
    }

    public Date getOrderDate() {
		if (StringUtils.isNotBlank(orderDateText)){
			orderDate = DateUtil.convert(orderDateText);
		}
		return this.orderDate;
	}

	public BigDecimal getPrice() {
		if (StringUtils.isNotBlank(priceText)){
			try {
				return new BigDecimal(priceText);
			} catch (Exception e) {
				return price;
			}
		}


		return this.price;
	}




	public String getOrderDateText() {
		return orderDateText;
	}

	public void setOrderDateText(String orderDateText) {
		this.orderDateText = orderDateText;
	}

	public String getPriceText() {
		return priceText;
	}

	public void setPriceText(String priceText) {
		this.priceText = priceText;
	}

	public String getId() {
	    return this.id;
	}
	public void setId(String id) {
	    this.id=id;
	}
	public String getDrugName() {
	    return this.drugName;
	}
	public void setDrugName(String drugName) {
	    this.drugName=drugName;
	}
	public String getSpecName() {
	    return this.specName;
	}
	public void setSpecName(String specName) {
	    this.specName=specName;
	}
	public String getDrugSpecName() {
	    return this.drugSpecName;
	}
	public void setDrugSpecName(String drugSpecName) {
	    this.drugSpecName=drugSpecName;
	}
	public String getDrugNum() {
	    return this.drugNum;
	}
	public void setDrugNum(String drugNum) {
	    this.drugNum=drugNum;
	}
	public BigDecimal getDrugCnt() {
        if (StringUtils.isNotBlank(drugCntText)){
            try {
                return new BigDecimal(drugCntText);
            } catch (Exception e) {
                return drugCnt;
            }
        }

        return drugCnt;
	}
	public void setDrugCnt(BigDecimal drugCnt) {
	    this.drugCnt=drugCnt;
	}
	public String getHospitalName() {
	    return this.hospitalName;
	}
	public void setHospitalName(String hospitalName) {
	    this.hospitalName=hospitalName;
	}
	public void setOrderDate(Date orderDate) {
	    this.orderDate=orderDate;
	}

	public void setPrice(BigDecimal price) {
	    this.price=price;
	}
	public String getCompanyName() {
	    return this.companyName;
	}
	public void setCompanyName(String companyName) {
	    this.companyName=companyName;
	}
	public String getBranchCompanyName() {
	    return this.branchCompanyName;
	}
	public void setBranchCompanyName(String branchCompanyName) {
	    this.branchCompanyName=branchCompanyName;
	}
	public Integer getYear() {
	    return this.year;
	}
	public void setYear(Integer year) {
	    this.year=year;
	}
	public Integer getMonth() {
	    return this.month;
	}
	public void setMonth(Integer month) {
	    this.month=month;
	}
	public Integer getDay() {
	    return this.day;
	}
	public void setDay(Integer day) {
	    this.day=day;
	}
	public BigDecimal getActualPrice() {
	    return this.actualPrice;
	}
	public void setActualPrice(BigDecimal actualPrice) {
	    this.actualPrice=actualPrice;
	}
	public BigDecimal getTotalMoney() {
	    return this.totalMoney;
	}
	public void setTotalMoney(BigDecimal totalMoney) {
	    this.totalMoney=totalMoney;
	}
	public String getCompanyArea() {
	    return this.companyArea;
	}
	public void setCompanyArea(String companyArea) {
	    this.companyArea=companyArea;
	}
	public String getDepartmentName() {
	    return this.departmentName;
	}
	public void setDepartmentName(String departmentName) {
	    this.departmentName=departmentName;
	}
	public String getHospitalArea() {
	    return this.hospitalArea;
	}
	public void setHospitalArea(String hospitalArea) {
	    this.hospitalArea=hospitalArea;
	}
	public String getHospitalLavel() {
	    return this.hospitalLavel;
	}
	public void setHospitalLavel(String hospitalLavel) {
	    this.hospitalLavel=hospitalLavel;
	}
	public String getHospitalType() {
	    return this.hospitalType;
	}
	public void setHospitalType(String hospitalType) {
	    this.hospitalType=hospitalType;
	}
	public String getSalesmanName() {
	    return this.salesmanName;
	}
	public void setSalesmanName(String salesmanName) {
	    this.salesmanName=salesmanName;
	}
	public BigDecimal getDeductionRate() {
	    return this.deductionRate;
	}
	public void setDeductionRate(BigDecimal deductionRate) {
	    this.deductionRate=deductionRate;
	}
	public BigDecimal getBiddingPrice() {
	    return this.biddingPrice;
	}
	public void setBiddingPrice(BigDecimal biddingPrice) {
	    this.biddingPrice=biddingPrice;
	}
	public BigDecimal getBillingPrice() {
	    return this.billingPrice;
	}
	public void setBillingPrice(BigDecimal billingPrice) {
	    this.billingPrice=billingPrice;
	}
	public BigDecimal getRebateRate() {
	    return this.rebateRate;
	}
	public void setRebateRate(BigDecimal rebateRate) {
	    this.rebateRate=rebateRate;
	}
	public BigDecimal getBrightPrice() {
	    return this.brightPrice;
	}
	public void setBrightPrice(BigDecimal brightPrice) {
	    this.brightPrice=brightPrice;
	}
	public BigDecimal getDarkPrice() {
	    return this.darkPrice;
	}
	public void setDarkPrice(BigDecimal darkPrice) {
	    this.darkPrice=darkPrice;
	}
	public BigDecimal getRebatePrice() {
	    return this.rebatePrice;
	}
	public void setRebatePrice(BigDecimal rebatePrice) {
	    this.rebatePrice=rebatePrice;
	}
	public String getDrugId() {
	    return this.drugId;
	}
	public void setDrugId(String drugId) {
	    this.drugId=drugId;
	}
	public String getSpecId() {
	    return this.specId;
	}
	public void setSpecId(String specId) {
	    this.specId=specId;
	}
	public String getHospitalId() {
	    return this.hospitalId;
	}
	public void setHospitalId(String hospitalId) {
	    this.hospitalId=hospitalId;
	}
	public String getDeptmentId() {
	    return this.deptmentId;
	}
	public void setDeptmentId(String deptmentId) {
	    this.deptmentId=deptmentId;
	}
	public String getCompanyId() {
	    return this.companyId;
	}
	public void setCompanyId(String companyId) {
	    this.companyId=companyId;
	}
	public String getCompanySellAreaId() {
	    return this.companySellAreaId;
	}
	public void setCompanySellAreaId(String companySellAreaId) {
	    this.companySellAreaId=companySellAreaId;
	}
	public String getSalesmanId() {
	    return this.salesmanId;
	}
	public void setSalesmanId(String salesmanId) {
	    this.salesmanId=salesmanId;
	}
	public String getCompanyDrugId() {
	    return this.companyDrugId;
	}
	public void setCompanyDrugId(String companyDrugId) {
	    this.companyDrugId=companyDrugId;
	}
	public String getRebateId() {
	    return this.rebateId;
	}
	public void setRebateId(String rebateId) {
	    this.rebateId=rebateId;
	}
	public String getStatus() {
	    return this.status;
	}
	public void setStatus(String status) {
	    this.status=status;
	}

    public String getDrugCntText() {
        return drugCntText;
    }

    public void setDrugCntText(String drugCntText) {
        this.drugCntText = drugCntText;
    }
}

