package com.ace.app.cms.drug.model;


import com.ace.app.cms.drug.domain.DrugSpec;
import com.ace.framework.base.BaseModel;
import com.ace.framework.util.ExecutionContext;

/**
* 药品规格
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
public class DrugSpecModel extends BaseModel {
    private DrugSpec drugSpec;



    public DrugSpec getDrugSpec() {

        return drugSpec;
    }

    public void setDrugSpec(DrugSpec drugSpec) {
        drugSpec.setParentCorpCode(ExecutionContext.getParentCorpCode());
        drugSpec.setCorpCode(ExecutionContext.getCorpCode());

        this.drugSpec = drugSpec;
    }
}
