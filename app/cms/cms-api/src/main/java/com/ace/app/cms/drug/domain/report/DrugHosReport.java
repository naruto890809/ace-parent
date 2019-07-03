package com.ace.app.cms.drug.domain.report;


import cn.afterturn.easypoi.excel.annotation.Excel;

/**
* 数据字典
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
public class DrugHosReport {
    @Excel(name ="品名")
    private String drugName;
    @Excel(name ="医院")
    private String hospitalName;
    @Excel(name ="部门")
    private String departmentName;
    @Excel(name ="员工")
    private String salesmanName;
    @Excel(name ="数量|金额")
    private String amountOrMoney;
    @Excel(name ="同比")
    private String tb;
    @Excel(name ="环比")
    private String hb;

    public DrugHosReport() {
    }

    public DrugHosReport(String drugName, String hospitalName, String departmentName,
                         String salesmanName, String amountOrMoney, String tb, String hb) {
        this.drugName = drugName;
        this.hospitalName = hospitalName;
        this.departmentName = departmentName;
        this.salesmanName = salesmanName;
        this.amountOrMoney = amountOrMoney;
        this.tb = tb;
        this.hb = hb;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
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
}

