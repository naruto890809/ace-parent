package com.ace.app.cms.drug.service;

import com.ace.app.cms.drug.domain.CompanyDrugErp;

import java.math.BigDecimal;
import java.util.List;
import com.ace.framework.base.BaseCrudService;
import com.ace.app.cms.ExcelService;

/**
* 商业公司药品Erp
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-29 18:41:55
*/
public interface CompanyDrugErpService extends BaseCrudService<CompanyDrugErp> , ExcelService<CompanyDrugErp> {

    void addCnt(String companyId, String drugId, String specDrugId, BigDecimal inCnt, BigDecimal outCnt);

    void subCnt(String companyId,String drugId,String specDrugId,BigDecimal inCnt,BigDecimal outCnt);

}
