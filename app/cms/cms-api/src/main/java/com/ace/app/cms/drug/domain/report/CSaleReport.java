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
public class CSaleReport {
    @Excel(name ="部门")
    private String departmentName;
    @Excel(name ="产品")
    private String drugName;
    @Excel(name ="年度任务")
    private BigDecimal taskCnt;
    @Excel(name ="当月销量")
    private BigDecimal drugCnt;
    @Excel(name ="当月同比")
    private String monthTb;
    @Excel(name ="当年累积销量")
    private BigDecimal yearCnt;
    @Excel(name ="当年累积同比")
    private String yearTb;
    @Excel(name ="完成率")
    private BigDecimal completeRatio;
    @Excel(name ="差量")
    private BigDecimal dif;

    public CSaleReport() {
    }

    public CSaleReport(String departmentName, String drugName, BigDecimal taskCnt,
                       BigDecimal drugCnt, String monthTb, BigDecimal yearCnt, String yearTb, BigDecimal completeRatio, BigDecimal dif) {
        this.departmentName = departmentName;
        this.drugName = drugName;
        this.taskCnt = taskCnt;
        this.drugCnt = drugCnt;
        this.monthTb = monthTb;
        this.yearCnt = yearCnt;
        this.yearTb = yearTb;
        this.completeRatio = completeRatio;
        this.dif = dif;
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

    public BigDecimal getTaskCnt() {
        return taskCnt;
    }

    public void setTaskCnt(BigDecimal taskCnt) {
        this.taskCnt = taskCnt;
    }

    public BigDecimal getDrugCnt() {
        return drugCnt;
    }

    public void setDrugCnt(BigDecimal drugCnt) {
        this.drugCnt = drugCnt;
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
}

