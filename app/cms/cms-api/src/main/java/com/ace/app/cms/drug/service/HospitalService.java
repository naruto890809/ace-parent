package com.ace.app.cms.drug.service;

import com.ace.app.cms.drug.domain.Hospital;
import java.util.List;
import java.util.Map;

import com.ace.framework.base.BaseCrudService;
import com.ace.app.cms.ExcelService;

/**
* 医院
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
public interface HospitalService extends BaseCrudService<Hospital> , ExcelService<Hospital> {

    Map<String,String> getIdNameMap();
    Map<String,String> getNameIdMap();
    Map<String,Hospital> getIdHosMap();
    Map<String,Hospital> getNameHosMap();

    Map<String,Hospital> getAliasNameHosMap();

    Map<String,String> getHosIdCityMap();
}
