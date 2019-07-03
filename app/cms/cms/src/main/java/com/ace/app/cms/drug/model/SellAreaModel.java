package com.ace.app.cms.drug.model;


import com.ace.app.cms.drug.domain.SellArea;
import com.ace.framework.base.BaseModel;
import com.ace.framework.util.ExecutionContext;

/**
* 销售区域
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-06 21:00:55
*/
public class SellAreaModel extends BaseModel {
    private SellArea sellArea;

    public SellArea getSellArea() {
        return sellArea;
    }

    public void setSellArea(SellArea sellArea) {
        sellArea.setParentCorpCode(ExecutionContext.getParentCorpCode());
        sellArea.setCorpCode(ExecutionContext.getCorpCode());
        this.sellArea = sellArea;
    }
}
