package com.ace.app.cms.drug.model;


import com.ace.app.cms.account.Account;
import com.ace.app.cms.drug.domain.*;
import com.ace.framework.base.BaseModel;
import com.ace.framework.util.ExecutionContext;

import java.util.List;
import java.util.Map;

/**
* 流向清单
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
public class OrderModel extends BaseModel {
    private Order order;
    private OrderSummary orderSummary;
    private Map<String,String> companyIdNameMap;
    private Map<String,String> drugSpecIdsNamesMap;
    private Map<String,String> hospitalIdNameMap;
    private List<Salesman> salesmans;
    private List<Account> accounts;
    private List<Department> departments;
    private List<String> bigAreas;
    private List<Hospital> hospitals;
    private List<Dic> priceTopics;
    private List<Dic> levels;
    private List<Dic> types;

    public List<Dic> getLevels() {
        return levels;
    }

    public void setLevels(List<Dic> levels) {
        this.levels = levels;
    }

    public List<Dic> getTypes() {
        return types;
    }

    public void setTypes(List<Dic> types) {
        this.types = types;
    }

    public List<Dic> getPriceTopics() {
        return priceTopics;
    }

    public void setPriceTopics(List<Dic> priceTopics) {
        this.priceTopics = priceTopics;
    }

    public OrderSummary getOrderSummary() {
        return orderSummary;
    }

    public void setOrderSummary(OrderSummary orderSummary) {
        this.orderSummary = orderSummary;
    }

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public void setHospitals(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }

    public List<String> getBigAreas() {
        return bigAreas;
    }

    public void setBigAreas(List<String> bigAreas) {
        this.bigAreas = bigAreas;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Salesman> getSalesmans() {
        return salesmans;
    }

    public void setSalesmans(List<Salesman> salesmans) {
        this.salesmans = salesmans;
    }

    public Map<String, String> getCompanyIdNameMap() {
        return companyIdNameMap;
    }

    public void setCompanyIdNameMap(Map<String, String> companyIdNameMap) {
        this.companyIdNameMap = companyIdNameMap;
    }

    public Map<String, String> getDrugSpecIdsNamesMap() {
        return drugSpecIdsNamesMap;
    }

    public void setDrugSpecIdsNamesMap(Map<String, String> drugSpecIdsNamesMap) {
        this.drugSpecIdsNamesMap = drugSpecIdsNamesMap;
    }

    public Map<String, String> getHospitalIdNameMap() {
        return hospitalIdNameMap;
    }

    public void setHospitalIdNameMap(Map<String, String> hospitalIdNameMap) {
        this.hospitalIdNameMap = hospitalIdNameMap;
    }

    public Order getOrder() {

        return order;
    }

    public void setOrder(Order order) {
        order.setParentCorpCode(ExecutionContext.getParentCorpCode());
        order.setCorpCode(ExecutionContext.getCorpCode());

        this.order = order;
    }
}
