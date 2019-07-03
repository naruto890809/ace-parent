package com.ace.app.cms.drug.service;

import com.ace.app.cms.drug.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ace.framework.base.BaseCrudService;
import com.ace.app.cms.ExcelService;
import com.ace.framework.base.Page;

/**
* 流向清单
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
public interface OrderService extends BaseCrudService<Order> , ExcelService<Order> {


    String checkOrderInfo(Order order, Map<String, String> drugSpecNameIdsStrMap, Map<String, BigDecimal> drugSpecNameCoefficientStrMap ,
                          Map<String, Drug> drugIdNameMap, Map<String, String> specIdNameMap,
                          Map<String, Hospital> nameHosMap, Map<String, Company> comNameIdMap,
                          Map<String, String> sellAreaIdNameMap, Map<String, String> deptIdNameMap,
                          Map<String, Salesman> salesmanIdNameMap, Map<String, String> dicIdNameMap);

    void packageInfoList(List<Order> order);

    Page<Order> drugHosPage(Order order,Page<Order> page);

    Page<Order> summaryPage(Order order,Page<Order> page);

    Map<String,BigDecimal> summaryMap(Order order);

    //计算同比 环比
    Map<String,BigDecimal> drugHosBMap(Order order);

    Page<Order> cSalePage(Order order,Page<Order> page);

    //计算同比 环比
    Map<String,BigDecimal> cSaleBMap(Order order);

    Page<Order> newHosPage(Order order,Page<Order> page);

    //计算同比 环比
    Map<String,BigDecimal> newHosBMap(Order order);

    Page<Order> hosCntPage(Order order,Page<Order> page);

    List<Order> departmentReport(Order order);
    List<Order> departmentDrugReport(Order order);
    List<Order> drugDepartmentReport(Order order);
    List<Order> drugAreaReport(Order order);
    List<Order> hospitalDrugReport(Order order);
    List<Order> drugHospitalReport(Order order);
    List findListForReport(Order order);

}
