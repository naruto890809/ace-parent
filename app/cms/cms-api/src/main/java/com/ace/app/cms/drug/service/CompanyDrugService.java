package com.ace.app.cms.drug.service;

import com.ace.app.cms.drug.domain.CompanyDrug;
import java.util.List;
import com.ace.framework.base.BaseCrudService;
import com.ace.app.cms.ExcelService;

/**
* 商业公司药品关联表
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
public interface CompanyDrugService extends BaseCrudService<CompanyDrug> , ExcelService<CompanyDrug> {

    CompanyDrug findByTime(CompanyDrug companyDrug);
    CompanyDrug findForOrder(CompanyDrug companyDrug);

}
