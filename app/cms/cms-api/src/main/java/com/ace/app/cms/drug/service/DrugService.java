package com.ace.app.cms.drug.service;

import com.ace.app.cms.drug.domain.Drug;
import java.util.List;
import java.util.Map;

import com.ace.framework.base.BaseCrudService;
import com.ace.app.cms.ExcelService;

/**
* 药品
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
public interface DrugService extends BaseCrudService<Drug> , ExcelService<Drug> {

    Map<String,String> getDrugIdNameMap();
    Map<String,Drug> getDrugIdMap();
    Map<String,String> getDrugNameIdMap();

}
