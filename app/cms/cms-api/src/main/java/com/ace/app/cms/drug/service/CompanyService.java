package com.ace.app.cms.drug.service;

import com.ace.app.cms.drug.domain.Company;
import java.util.List;
import java.util.Map;

import com.ace.framework.base.BaseCrudService;
import com.ace.app.cms.ExcelService;

/**
* 商业公司
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
public interface CompanyService extends BaseCrudService<Company> , ExcelService<Company> {

    Map<String,String> getComIdNameMap();
    Map<String,String> getComNameIdMap();
    Map<String,Company> getComNameCompanyMap();
}
