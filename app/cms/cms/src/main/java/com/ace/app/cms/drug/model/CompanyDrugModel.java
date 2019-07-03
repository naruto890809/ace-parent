package com.ace.app.cms.drug.model;


import com.ace.app.cms.drug.domain.Company;
import com.ace.app.cms.drug.domain.CompanyDrug;
import com.ace.framework.base.BaseModel;
import com.ace.framework.util.ExecutionContext;

import java.util.List;
import java.util.Map;

/**
* 商业公司药品关联表
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
public class CompanyDrugModel extends BaseModel {
    private CompanyDrug companyDrug;
    private Map<String,String> companyIdNameMap;
    private Map<String,String> drugSpecIdsNamesMap;


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

    public CompanyDrug getCompanyDrug() {

        return companyDrug;
    }

    public void setCompanyDrug(CompanyDrug companyDrug) {
        companyDrug.setParentCorpCode(ExecutionContext.getParentCorpCode());
        companyDrug.setCorpCode(ExecutionContext.getCorpCode());

        this.companyDrug = companyDrug;
    }
}
