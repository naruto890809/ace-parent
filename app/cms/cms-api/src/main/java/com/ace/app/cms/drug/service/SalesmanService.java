package com.ace.app.cms.drug.service;

import com.ace.app.cms.drug.domain.Salesman;
import java.util.List;
import java.util.Map;

import com.ace.framework.base.BaseCrudService;
import com.ace.app.cms.ExcelService;

/**
* 业务员
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
public interface SalesmanService extends BaseCrudService<Salesman> , ExcelService<Salesman> {


    Map<String,String> getSalesmanIdNameMap();
    Map<String,Salesman> getIdSalesmanMap();
    Map<String,String> getSalesmanNameIdMap();
}
