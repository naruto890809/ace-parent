package com.ace.app.cms.drug.model;


import com.ace.app.cms.drug.domain.Company;
import com.ace.app.cms.drug.domain.Dic;
import com.ace.app.cms.drug.domain.SellArea;
import com.ace.framework.base.BaseModel;
import com.ace.framework.util.ExecutionContext;

import java.util.List;

/**
* 商业公司
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
public class CompanyModel extends BaseModel {
    private Company company;
    private List<Dic> channels;
    private List<SellArea> sellAreas;

    public List<Dic> getChannels() {
        return channels;
    }

    public void setChannels(List<Dic> channels) {
        this.channels = channels;
    }

    public List<SellArea> getSellAreas() {
        return sellAreas;
    }

    public void setSellAreas(List<SellArea> sellAreas) {
        this.sellAreas = sellAreas;
    }

    public Company getCompany() {

        return company;
    }

    public void setCompany(Company company) {
        company.setParentCorpCode(ExecutionContext.getParentCorpCode());
        company.setCorpCode(ExecutionContext.getCorpCode());

        this.company = company;
    }
}
