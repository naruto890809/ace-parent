package com.ace.app.cms.drug.domain.report;


import cn.afterturn.easypoi.excel.annotation.Excel;

import java.math.BigDecimal;

/**
 * 数据字典
 * <p/>
 * WuZhiWei v1.0
 *
 * @author 代码生成器 v1.0
 * @since 2018-09-12 07:52:32
 */
public class HosCntReport {
    @Excel(name = "业务员所在部门")
    private String departmentName;
    @Excel(name = "业务员")
    private String salesmanName;
    @Excel(name = "医院")
    private String hospitalName;
    @Excel(name = "品规")
    private String drugSpecName;
    @Excel(name = "月均实际销量")
    private BigDecimal drugCnt;
    @Excel(name = "月均考核量")
    private BigDecimal taskCnt;
    @Excel(name = "差值")
    private BigDecimal dif;

    public HosCntReport() {
    }

    public HosCntReport(String departmentName, String salesmanName,
                        String hospitalName, String drugSpecName, BigDecimal drugCnt, BigDecimal taskCnt, BigDecimal dif) {
        this.departmentName = departmentName;
        this.salesmanName = salesmanName;
        this.hospitalName = hospitalName;
        this.drugSpecName = drugSpecName;
        this.drugCnt = drugCnt;
        this.taskCnt = taskCnt;
        this.dif = dif;
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

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDrugSpecName() {
        return drugSpecName;
    }

    public void setDrugSpecName(String drugSpecName) {
        this.drugSpecName = drugSpecName;
    }

    public BigDecimal getDrugCnt() {
        return drugCnt;
    }

    public void setDrugCnt(BigDecimal drugCnt) {
        this.drugCnt = drugCnt;
    }

    public BigDecimal getTaskCnt() {
        return taskCnt;
    }

    public void setTaskCnt(BigDecimal taskCnt) {
        this.taskCnt = taskCnt;
    }

    public BigDecimal getDif() {
        return dif;
    }

    public void setDif(BigDecimal dif) {
        this.dif = dif;
    }
}

