package com.ace.app.cms.drug.model;


import com.ace.app.cms.drug.domain.Drug;
import com.ace.app.cms.drug.domain.DrugSpec;
import com.ace.framework.base.BaseModel;
import com.ace.framework.util.ExecutionContext;

import java.util.List;

/**
* 药品
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
public class DrugModel extends BaseModel {
    private Drug drug;
    private List<DrugSpec> drugSpecs;


    public List<DrugSpec> getDrugSpecs() {
        return drugSpecs;
    }

    public void setDrugSpecs(List<DrugSpec> drugSpecs) {
        this.drugSpecs = drugSpecs;
    }

    public Drug getDrug() {

        return drug;
    }

    public void setDrug(Drug drug) {
        drug.setParentCorpCode(ExecutionContext.getParentCorpCode());
        drug.setCorpCode(ExecutionContext.getCorpCode());

        this.drug = drug;
    }
}
