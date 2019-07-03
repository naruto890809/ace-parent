package com.ace.app.cms.drug.dao;


import com.ace.app.cms.drug.domain.Order;
import com.ace.framework.base.BaseDao;
import com.ace.framework.base.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 流向清单
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
@Repository
public class OrderDao extends BaseDao<Order> {

    private static final String STATEMENT = "com.ace.app.cms.drug.domain.Order.";

    public Page<Order> drugHosPage(Order order, Page<Order> page){
        return search(STATEMENT + "drugHosPage",order,page);
    }

    public Map<String,BigDecimal> drugHosBMap(Order order){
        List<Order> list = findList(STATEMENT + "drugHosPage", order);
        if (CollectionUtils.isEmpty(list)){
            return MapUtils.EMPTY_MAP;
        }

        String reportType = order.getReportType();
        Map<String,BigDecimal> drugHosBMap = new HashMap<>(list.size());
        for (Order tmp : list) {
            BigDecimal amount = null;
            if ("coefficient".equals(reportType)){
                amount = tmp.getDrugCnt();
            }else {
                amount = tmp.getTotalMoney();
            }

            drugHosBMap.put(tmp.getDrugId()+tmp.getHospitalId(),amount);
        }

        return drugHosBMap;
    }

    public Page<Order> summaryPage(Order order, Page<Order> page){
        return search(STATEMENT + "summaryPage",order,page);
    }

    public Map<String,BigDecimal> summaryMap(Order order){
        List<Order> list = findList(STATEMENT + "summaryPage", order);
        if (CollectionUtils.isEmpty(list)){
            return MapUtils.EMPTY_MAP;
        }

        String reportType = order.getReportType();
        Map<String,BigDecimal> drugHosBMap = new HashMap<>(list.size());
        for (Order tmp : list) {
            BigDecimal amount = null;
            if ("coefficient".equals(reportType)){
                amount =  tmp.getDrugCnt();
            }else {
                amount = tmp.getTotalMoney();
            }

            drugHosBMap.put(tmp.getDrugId()+tmp.getHospitalId(),amount);
        }

        return drugHosBMap;
    }


    public Page<Order> cSalePage(Order order, Page<Order> page){
        return search(STATEMENT + "cSalePage",order,page);
    }

    public Map<String,BigDecimal> cSaleBMap(Order order){
        List<Order> list = findList(STATEMENT + "cSalePage", order);
        if (CollectionUtils.isEmpty(list)){
            return MapUtils.EMPTY_MAP;
        }

        Map<String,BigDecimal> drugHosBMap = new HashMap<>(list.size());
        for (Order tmp : list) {
            //drugHosBMap.put(tmp.getDrugId()+tmp.getDeptmentId(),new BigDecimal( tmp.getDrugCnt()));
            drugHosBMap.put(tmp.getDrugId()+tmp.getDeptmentId(),tmp.getCoefficient());
        }

        return drugHosBMap;
    }



    public Page<Order> newHosPage(Order order, Page<Order> page){
        return search(STATEMENT + "newHosPage",order,page);
    }


    public Page<Order> hosCntPage(Order order, Page<Order> page){
        return search(STATEMENT + "hosCntPage",order,page);
    }

    public Map<String,BigDecimal> newHosBMap(Order order){
        List<Order> list = findList(STATEMENT + "newHosPage", order);
        if (CollectionUtils.isEmpty(list)){
            return MapUtils.EMPTY_MAP;
        }

        String reportType = order.getReportType();
        Map<String,BigDecimal> drugHosBMap = new HashMap<>(list.size());
        for (Order tmp : list) {
            BigDecimal amount = null;
            if ("coefficient".equals(reportType)){
                amount =  tmp.getDrugCnt();
            }else {
                amount = tmp.getTotalMoney();
            }

            drugHosBMap.put(tmp.getDrugId()+tmp.getSpecId()+tmp.getDeptmentId(),amount);
        }
        return drugHosBMap;
    }


    public List<Order> departmentReport(Order order){
        return findList(STATEMENT + "departmentReport",order);
    }

    public List<Order> departmentDrugReport(Order order){
        return findList(STATEMENT + "departmentDrugReport",order);
    }

    public List<Order> drugDepartmentReport(Order order){
        return findList(STATEMENT + "drugDepartmentReport",order);
    }

    public List<Order> drugAreaReport(Order order){
        return findList(STATEMENT + "drugAreaReport",order);
    }

    public List<Order> hospitalDrugReport(Order order){
        return findList(STATEMENT + "hospitalDrugReport",order);
    }

    public List<Order> drugHospitalReport(Order order){
        return findList(STATEMENT + "drugHospitalReport",order);
    }

}
