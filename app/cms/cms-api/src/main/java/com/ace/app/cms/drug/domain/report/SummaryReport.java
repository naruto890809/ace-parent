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
public class SummaryReport {

    @Excel(name ="医院所属市")
    private String hospitalCity;
    @Excel(name ="医院行政区域")
    private String hospitalArea;
    @Excel(name ="部门")
    private String departmentName;


    @Excel(name ="年")
    private Integer year;
    @Excel(name ="月")
    private Integer month;
    @Excel(name ="品名")
    private String drugName;
    @Excel(name ="类型")
    private String summaryType;
    @Excel(name ="数量|金额")
    private String amountOrMoney;
    @Excel(name ="同比")
    private String tb;
    @Excel(name ="环比")
    private String hb;


    public SummaryReport() {
    }

    public SummaryReport(String hospitalCity, String hospitalArea,
                         String departmentName,Integer year, Integer month, String drugName, String summaryType, String amountOrMoney, String tb, String hb) {
        this.hospitalCity = hospitalCity;
        this.hospitalArea = hospitalArea;
        this.departmentName = departmentName;
        this.year=year;
        this.month=month;
        this.drugName = drugName;
        this.summaryType = summaryType;
        this.amountOrMoney = amountOrMoney;
        this.tb = tb;
        this.hb = hb;
    }


    public String getHospitalCity() {
        return hospitalCity;
    }

    public void setHospitalCity(String hospitalCity) {
        this.hospitalCity = hospitalCity;
    }

    public String getHospitalArea() {
        return hospitalArea;
    }

    public void setHospitalArea(String hospitalArea) {
        this.hospitalArea = hospitalArea;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getSummaryType() {
        return summaryType;
    }

    public void setSummaryType(String summaryType) {
        this.summaryType = summaryType;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
}

