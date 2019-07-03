package com.ace.app.cms.drug.service;

import com.ace.app.cms.drug.domain.DrugSpec;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ace.framework.base.BaseCrudService;
import com.ace.app.cms.ExcelService;

/**
* 药品规格
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
public interface DrugSpecService extends BaseCrudService<DrugSpec> , ExcelService<DrugSpec> {

    Map<String, String> getDrugIdSpecNameMap();

    Map<String, String> getDrugIdSpecCoefficientMap();


    //品规名 对应品、规格id，英文逗号分隔
    Map<String,String> drugSpecNameIdsStrMap();

    Map<String,String> drugSpecAliaNameIdsStrMap();

    Map<String,BigDecimal> drugSpecNameCoefficientStrMap();

    Map<String,String> idsDrugSpecNameStrMap();

    List<DrugSpec> drugSpecIdNameList();

    Map<String,String> getIdSpecNameMap();

}
