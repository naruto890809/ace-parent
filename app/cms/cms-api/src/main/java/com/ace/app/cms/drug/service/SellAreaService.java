package com.ace.app.cms.drug.service;

import com.ace.app.cms.ExcelService;
import com.ace.app.cms.drug.domain.SellArea;
import com.ace.framework.base.BaseCrudService;

import java.util.List;
import java.util.Map;

/**
* 销售区域
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-06 21:00:55
*/
public interface SellAreaService extends BaseCrudService<SellArea> , ExcelService<SellArea> {


    List<SellArea> findPassed();
    Map<String,String> sellAreaNameIdMap();
    Map<String,String> sellAreaIdNameMap();
}
