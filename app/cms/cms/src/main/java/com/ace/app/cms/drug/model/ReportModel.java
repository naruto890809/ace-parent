package com.ace.app.cms.drug.model;


import com.ace.app.cms.account.Account;
import com.ace.app.cms.drug.domain.*;
import com.ace.framework.base.BaseModel;
import com.ace.framework.util.DateUtil;

import java.util.Date;
import java.util.List;

/**
* 业务管理
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:30
*/
public class ReportModel extends BaseModel {

    private Order order;
    private List<Drug> drugs;
    private List<DrugSpec> drugSpecs;
    private List<Hospital> hospitals;
    private List<Department> departments;
    private String startDateText;
    private String endDateText;
    private List<Salesman> salesmen;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    private Account account;


    public List<Salesman> getSalesmen() {
        return salesmen;
    }

    public void setSalesmen(List<Salesman> salesmen) {
        this.salesmen = salesmen;
    }

    public List<DrugSpec> getDrugSpecs() {
        return drugSpecs;
    }

    public void setDrugSpecs(List<DrugSpec> drugSpecs) {
        this.drugSpecs = drugSpecs;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public String getStartDateText() {
        return startDateText;
    }

    public void setStartDateText(String startDateText) {
        this.startDateText = startDateText;
    }

    public String getEndDateText() {
        return endDateText;
    }

    public void setEndDateText(String endDateText) {
        this.endDateText = endDateText;
    }

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public void setHospitals(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
