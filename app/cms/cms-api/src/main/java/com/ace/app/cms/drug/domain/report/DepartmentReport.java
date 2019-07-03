package com.ace.app.cms.drug.domain.report;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ace.app.cms.drug.domain.Order;

import java.math.BigDecimal;

/**
* 数据字典
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
public class DepartmentReport{

    @Excel(name ="维度")
    private String departmentName;
    @Excel(name ="销售额")
    private BigDecimal totalMoney;
    @Excel(name ="比重")
    private String ratio;

    public DepartmentReport() {
    }

    public DepartmentReport(String departmentName, BigDecimal totalMoney, String ratio) {
        this.departmentName = departmentName;
        this.totalMoney = totalMoney;
        this.ratio = ratio;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }
}

