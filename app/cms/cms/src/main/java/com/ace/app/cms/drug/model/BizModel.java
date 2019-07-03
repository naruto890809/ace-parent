package com.ace.app.cms.drug.model;


import com.ace.app.cms.drug.domain.Biz;
import com.ace.app.cms.drug.domain.Department;
import com.ace.app.cms.drug.domain.Hospital;
import com.ace.app.cms.drug.domain.Salesman;
import com.ace.framework.base.BaseModel;
import com.ace.framework.util.ExecutionContext;

import java.util.List;
import java.util.Map;

/**
* 业务管理
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:30
*/
public class BizModel extends BaseModel {
    private Biz biz;

//    private Map<String,String> hospitalIdNameMap;
    private Map<String,String> salesmanIdNameMap;
    private Map<String,String> drugSpecIdsNamesMap;
    private List<Department> departments;
    private List<Hospital> hospitals;

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public void setHospitals(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

//    public Map<String, String> getHospitalIdNameMap() {
//        return hospitalIdNameMap;
//    }

//    public void setHospitalIdNameMap(Map<String, String> hospitalIdNameMap) {
//        this.hospitalIdNameMap = hospitalIdNameMap;
//    }

    public Map<String, String> getSalesmanIdNameMap() {
        return salesmanIdNameMap;
    }

    public void setSalesmanIdNameMap(Map<String, String> salesmanIdNameMap) {
        this.salesmanIdNameMap = salesmanIdNameMap;
    }

    public Map<String, String> getDrugSpecIdsNamesMap() {
        return drugSpecIdsNamesMap;
    }

    public void setDrugSpecIdsNamesMap(Map<String, String> drugSpecIdsNamesMap) {
        this.drugSpecIdsNamesMap = drugSpecIdsNamesMap;
    }

    public Biz getBiz() {

        return biz;
    }

    public void setBiz(Biz biz) {
        biz.setParentCorpCode(ExecutionContext.getParentCorpCode());
        biz.setCorpCode(ExecutionContext.getCorpCode());

        this.biz = biz;
    }
}
