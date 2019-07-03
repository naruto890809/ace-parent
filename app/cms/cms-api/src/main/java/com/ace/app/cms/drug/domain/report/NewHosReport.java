package com.ace.app.cms.drug.domain.report;


import cn.afterturn.easypoi.excel.annotation.Excel;

import java.math.BigDecimal;

/**
* 数据字典
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
public class NewHosReport {
    @Excel(name ="部门")
    private String departmentName;
    @Excel(name ="品规")
    private String drugSpecName;
    @Excel(name ="新申请医院数量|金额")
    private String amountOrMoney;
    @Excel(name ="纯销环比增长量")
    private String cxAddCnt;
    @Excel(name ="百分比")
    private String ratio;

    public NewHosReport() {
    }

    public NewHosReport(String departmentName, String drugSpecName, String amountOrMoney, String cxAddCnt, String ratio) {
        this.departmentName = departmentName;
        this.drugSpecName = drugSpecName;
        this.amountOrMoney = amountOrMoney;
        this.cxAddCnt = cxAddCnt;
        this.ratio = ratio;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDrugSpecName() {
        return drugSpecName;
    }

    public void setDrugSpecName(String drugSpecName) {
        this.drugSpecName = drugSpecName;
    }

    public String getAmountOrMoney() {
        return amountOrMoney;
    }

    public void setAmountOrMoney(String amountOrMoney) {
        this.amountOrMoney = amountOrMoney;
    }

    public String getCxAddCnt() {
        return cxAddCnt;
    }

    public void setCxAddCnt(String cxAddCnt) {
        this.cxAddCnt = cxAddCnt;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }
}

