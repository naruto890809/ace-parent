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
public class DepartmentDrugReport {

    @Excel(name ="维度")
    private String reportDifName;
    @Excel(name ="品名")
    private String drugName;
    @Excel(name ="销售额|销售量")
    private String totalMoney;


    public DepartmentDrugReport() {
    }

    public DepartmentDrugReport(String reportDifName, String drugName, String totalMoney) {
        this.reportDifName = reportDifName;
        this.drugName = drugName;
        this.totalMoney = totalMoney;
    }

    public String getReportDifName() {
        return reportDifName;
    }

    public void setReportDifName(String reportDifName) {
        this.reportDifName = reportDifName;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }
}

