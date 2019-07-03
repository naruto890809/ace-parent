package com.ace.app.cms.drug.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ace.framework.base.BaseEntity;
import com.ace.framework.base.PrimaryKey;
import com.ace.framework.util.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
* 流向清单
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
public class OrderSummary extends BaseEntity {
    private static final long serialVersionUID = -4252420885929541544L;

    @Excel(name ="商业公司地区")
    private String companySellAreaId;//   商业公司所属销售区域Id
    @Excel(name ="年")
    private Integer year;//   年
    @Excel(name ="月")
    private Integer month;//   月
    @Excel(name ="日期")
    private String orderDateText;//   日期
    @Excel(name ="推广部名称")
    private String departmentName;//   医院所属推广部名称
    @Excel(name ="行政区域")
    private String hospitalArea;//   医院所在行政区域（省市区|县）
    @Excel(name ="业务员")
    private String salesmanName;//   业务员姓名
	@Excel(name ="议价主体")
	private String priceTopicName;//   医院名称
    @Excel(name ="医院名称")
    private String hospitalName;//   医院名称
	@Excel(name ="医院原始名称")
    private String hospitalPreName;//   医院名称
	@Excel(name ="医院原始地址")
	private String hospitalPreAddre;//   医院名称

	@Excel(name ="医院等级")
    private String hospitalLavel;//   医院等级
    @Excel(name ="医院性质")
    private String hospitalType;//   医院性质
    @Excel(name ="品名")
    private String drugName;//   药品
    @Excel(name ="品名规格")
    private String specName;//   规格
    @Excel(name ="数量",type=10)
    private BigDecimal drugCnt;//   数量
    @Excel(name ="医保支付价")
    private BigDecimal biddingPrice;//   商业公司药品关联表中标价（医保支付价）
    @Excel(name ="终端开票价")
    private BigDecimal price;//   单价（）
    @Excel(name ="开票价")
    private BigDecimal billingPrice;//   商业公司药品关联表开票价（开票价）
    @Excel(name ="返利点数")
    private BigDecimal rebateRate;//   返利信息表-返利点数
    private String rebateId;//   返利信息表id（根据商业公司、药品、规格、医院id从返利表查出）
    @Excel(name ="明返单价")
    private BigDecimal brightPrice;//   返利信息表-明返单价
    @Excel(name ="暗返单价")
    private BigDecimal darkPrice;//   返利信息表-暗返单价
    @Excel(name ="返利单价")
    private BigDecimal rebatePrice;//   返利信息表-返利单价（明+暗）
    @Excel(name ="实际结算价")
    private BigDecimal actualPrice;//   实际结算价【(中标价格-返利单价)*(1-商业扣率)】
    @Excel(name ="销售金额")
    private BigDecimal totalMoney;//   销售金额【实际结算价格*数量】
    @Excel(name ="商业公司")
    private String companyName;//   商业公司
    @Excel(name ="商品代码")
    private String drugCode;//   状态，正常0、医保支付价异常、终端开票价异常
    @Excel(name ="分公司")
    private String companyBranch;//   状态，正常0、医保支付价异常、终端开票价异常
    @Excel(name ="批号")
    private String drugNum;//   批号
    @Excel(name ="状态")
    private String statusText;//   状态，正常0、医保支付价异常、终端开票价异常



    private BigDecimal executePrice;//   返利信息表-执行价
	@PrimaryKey
	private String id;//   主键
	private String drugSpecName;//   品规



	private Date orderDate;//   日期



	private String priceText;//   单价（终端开票价）

	private String branchCompanyName;//   分公司  现在作为导入记录主键

	private Integer day;//   日


	//商业公司
	private String companyId;//   商业公司ID

	private String companyArea;//   商业公司所属销售区域



	private String salesmanId;//   业务员id（根据医院、药品、规格id及跟踪时间段从业务表查出）

	private String companyDrugId;//   商业公司药品关联表id（根据商业公司、药品、规格id及有效时间段从业务表查出）
	private BigDecimal deductionRate;//   商业公司药品关联表扣率




	//医院
	private String hospitalId;//   医院ID
	private String deptmentId;//   医院所属推广部id
	private String bigArea;//统计维度






	//0.正常
	//1.不在返利信息表内的医院，性质为医院的，终端开票价不等于医保支付价
	//2.(不是医院的，医院性质作为筛选条件,品规一致)终端开票价格小于开票价为异常
	//3.(医院名称在返利信息表内,品规一致)终端开票价格不等于返利信息表中的执行价则为异常
	private String status;//   状态，正常0、医保支付价异常、终端开票价异常




	private String drugId;//   药品Id
	private String specId;//   规格Id



	private Date startTime;
	private Date endTime;
	private String hospitalNameSplit;


	//////////////////////
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

	public String getHospitalNameSplit() {
		return hospitalNameSplit;
	}

	public void setHospitalNameSplit(String hospitalNameSplit) {
		this.hospitalNameSplit = hospitalNameSplit;
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

	public String getBigArea() {
		return bigArea;
	}

	public void setBigArea(String bigArea) {
		this.bigArea = bigArea;
	}

	public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public String getCompanyBranch() {
        return companyBranch;
    }

    public void setCompanyBranch(String companyBranch) {
        this.companyBranch = companyBranch;
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
	    return this.drugCnt;
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

	public OrderSummary() {
	}

	public OrderSummary(String id, String drugName, String specName, String drugSpecName,
                        String drugNum, BigDecimal drugCnt, String hospitalName, Date orderDate,
                        String orderDateText, BigDecimal price, String priceText,
                        String companyName, String branchCompanyName, Integer year,
                        Integer month, Integer day, String companyId,
                        String companySellAreaId, String companyArea,
                        String companyDrugId, BigDecimal deductionRate,
                        BigDecimal biddingPrice, BigDecimal billingPrice,
                        String hospitalId, String deptmentId, String departmentName,
                        String hospitalArea, String hospitalLavel, String hospitalType,
                        BigDecimal rebateRate, String rebateId, BigDecimal brightPrice,
                        BigDecimal darkPrice, BigDecimal rebatePrice, BigDecimal executePrice,
                        String status, String salesmanName, String salesmanId, BigDecimal actualPrice,
                        BigDecimal totalMoney, String drugId, String specId
            , String statusText, String drugCode, String companyBranch, String priceTopicName, String hospitalPreName, String hospitalPreAddre) {
		this.hospitalPreAddre = hospitalPreAddre;
		this.hospitalPreName = hospitalPreName;
		this.priceTopicName = priceTopicName;
		this.id = id;
		this.drugName = drugName;
		this.specName = specName;
		this.drugSpecName = drugSpecName;
		this.drugNum = drugNum;
		this.drugCnt = drugCnt;
		this.hospitalName = hospitalName;
		this.orderDate = orderDate;
		this.orderDateText = orderDateText;
		this.price = price;
		this.priceText = priceText;
		this.companyName = companyName;
		this.branchCompanyName = branchCompanyName;
		this.year = year;
		this.month = month;
		this.day = day;
		this.companyId = companyId;
		this.companySellAreaId = companySellAreaId;
		this.companyArea = companyArea;
		this.companyDrugId = companyDrugId;
		this.deductionRate = deductionRate;
		this.biddingPrice = biddingPrice;
		this.billingPrice = billingPrice;
		this.hospitalId = hospitalId;
		this.deptmentId = deptmentId;
		this.departmentName = departmentName;
		this.hospitalArea = hospitalArea;
		this.hospitalLavel = hospitalLavel;
		this.hospitalType = hospitalType;
		this.rebateRate = rebateRate;
		this.rebateId = rebateId;
		this.brightPrice = brightPrice;
		this.darkPrice = darkPrice;
		this.rebatePrice = rebatePrice;
		this.executePrice = executePrice;
		this.status = status;
		this.salesmanName = salesmanName;
		this.salesmanId = salesmanId;
		this.actualPrice = actualPrice;
		this.totalMoney = totalMoney;
		this.drugId = drugId;
		this.specId = specId;
		this.statusText = statusText;
		this.drugCode = drugCode;
		this.companyBranch = companyBranch;
	}
}

