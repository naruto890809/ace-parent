package com.ace.app.cms.drug.model;


import com.ace.app.cms.drug.domain.Dic;
import com.ace.app.cms.drug.domain.Hospital;
import com.ace.app.cms.drug.domain.Rebate;
import com.ace.framework.base.BaseModel;
import com.ace.framework.util.ExecutionContext;

import java.util.List;
import java.util.Map;

/**
* 返利设置
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
public class RebateModel extends BaseModel {
    private Rebate rebate;
    private Map<String,String> companyIdNameMap;
    private Map<String,String> drugSpecIdsNamesMap;
    private Map<String,String> hospitalIdNameMap;
    private List<Dic> priceTopics;
    private List<Dic> rebateStyles;
    private List<Hospital> hospitals;

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public void setHospitals(List<Hospital> hospitals) {
        this.hospitals = hospitals;
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

    public List<Dic> getPriceTopics() {
        return priceTopics;
    }

    public void setPriceTopics(List<Dic> priceTopics) {
        this.priceTopics = priceTopics;
    }

    public List<Dic> getRebateStyles() {
        return rebateStyles;
    }

    public void setRebateStyles(List<Dic> rebateStyles) {
        this.rebateStyles = rebateStyles;
    }

    public Rebate getRebate() {

        return rebate;
    }

    public void setRebate(Rebate rebate) {
        rebate.setParentCorpCode(ExecutionContext.getParentCorpCode());
        rebate.setCorpCode(ExecutionContext.getCorpCode());

        this.rebate = rebate;
    }
}
