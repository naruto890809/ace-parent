package com.ace.app.cms.drug.dao;


import com.ace.app.cms.drug.domain.CompanyDrug;
import com.ace.framework.base.BaseDao;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
* 商业公司药品关联表
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
@Repository
public class CompanyDrugDao extends BaseDao<CompanyDrug> {

    private static final String STATEMENT = "com.ace.app.cms.drug.domain.CompanyDrug.";

    public CompanyDrug findByTime(CompanyDrug companyDrug){
        List<CompanyDrug> list = findList(STATEMENT + "findByTime", companyDrug);
        if (CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }

        return null;
    }

    public CompanyDrug findForOrder(CompanyDrug companyDrug){
        List<CompanyDrug> list = findList(STATEMENT + "findForOrder", companyDrug);
        if (CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }

        return null;
    }
}
