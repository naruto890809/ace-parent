package com.ace.app.cms.drug.domain.report;


import cn.afterturn.easypoi.excel.annotation.Excel;

/**
* 数据字典
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
public class DrugAreaReport {
    @Excel(name ="品名")
    private String drugName;

    @Excel(name ="区域")
    private String deptName;
    @Excel(name ="数量/金额")
    private String totalMoney;


    public DrugAreaReport(String drugName) {
        this.drugName = drugName;
    }

    public DrugAreaReport(String drugName, String deptName, String totalMoney) {
        this.drugName = drugName;
        this.deptName = deptName;
        this.totalMoney = totalMoney;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }
}

