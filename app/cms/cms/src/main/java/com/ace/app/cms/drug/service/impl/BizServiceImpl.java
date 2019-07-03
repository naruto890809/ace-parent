package com.ace.app.cms.drug.service.impl;

import com.ace.app.cms.drug.dao.BizDao;
import com.ace.app.cms.drug.domain.Biz;
import com.ace.app.cms.drug.service.BizService;
import com.ace.app.cms.CmsConstant;
import com.ace.app.cms.drug.service.DrugSpecService;
import com.ace.app.cms.drug.service.HospitalService;
import com.ace.app.cms.drug.service.SalesmanService;
import com.ace.framework.base.BaseCrudServiceImpl;
import com.ace.framework.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ace.framework.base.BaseDao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* 业务管理

* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:30
*/
@Service("bizService")
public class BizServiceImpl  extends BaseCrudServiceImpl<Biz> implements BizService {
    @Resource
    private BizDao bizDao;
    @Resource
    private HospitalService hospitalService;
    @Resource
    private DrugSpecService drugSpecService;
    @Resource
    private SalesmanService salesmanService;


    @Override
    public BaseDao<Biz> getEntityDao() {
        return bizDao;
    }

    @Override
    public List findListForExport(Biz condition) {
        List<Biz> list = this.findList(condition);
        if (CollectionUtils.isEmpty(list)){
            return list;
        }

        Map<String, String> hosIdNameMap = hospitalService.getIdNameMap();
        Map<String, String> idsDrugSpecNameStrMap = drugSpecService.idsDrugSpecNameStrMap();
        Map<String, String> salesmanIdNameMap = salesmanService.getSalesmanIdNameMap();
        for (Biz row : list) {
            row.setSalesmanName(salesmanIdNameMap.get(row.getSalesmanId()));
            row.setHospitalName(hosIdNameMap.get(row.getHospitalId()));
            row.setSpecDrugName(idsDrugSpecNameStrMap.get(row.getDrugId() +","+ row.getSpecDrugId()));
            row.setSaleDateText(DateUtil.convertTo_yyyy_MM_dd(row.getSaleDate()));
            row.setTraceStartDateText(DateUtil.convertTo_yyyy_MM_dd(row.getTraceStartDate()));
            row.setTraceEndDateText(DateUtil.convertTo_yyyy_MM_dd(row.getTraceEndDate()));
        }
        return list;
    }

    @Override
    public String importBatch(List<Biz> list,String extParam) {
        String result = "";
        Map<String, String> hospitalNameIdMap = hospitalService.getNameIdMap();
        Map<String, String> drugSpecNameIdsStrMap = drugSpecService.drugSpecNameIdsStrMap();
        Map<String, String> salesmanNameIdMap = salesmanService.getSalesmanNameIdMap();
        List<String> excelBiz = new ArrayList<>();
        for (Biz biz : list) {
            String indexResult = "";
            String salesmanName = biz.getSalesmanName();
            String salesmanId = salesmanNameIdMap.get(salesmanName);
            if (StringUtils.isBlank(salesmanName)){
                indexResult += "业务员为空、";
            }else {
                if ( StringUtils.isBlank(salesmanId)){
                    indexResult += "业务员不存在、";
                }else {
                    biz.setSalesmanId(salesmanId);
                }
                if (excelBiz.contains(biz.toString())) {
                    indexResult += "表格有重复数据重复、";
                }else {
                    excelBiz.add(biz.toString());
                }
            }

            String hospitalName = biz.getHospitalName();
            String hospitalId = hospitalNameIdMap.get(hospitalName);
            if (StringUtils.isBlank(hospitalName)) {
                indexResult += "医院名称为空、";
            } else {
                if (StringUtils.isBlank(hospitalId)) {
                    indexResult += "医院不存在、";
                }else {
                    biz.setHospitalId(hospitalId);
                    biz.setNote(hospitalName);
                }
            }

            String specDrugName = biz.getSpecDrugName();
            String drugId = "";
            String specDrugId="";
            if (StringUtils.isBlank(specDrugName)) {
                indexResult += "品规名称为空、";
            } else {
                String drugSpecIds = drugSpecNameIdsStrMap.get(specDrugName);
                if (StringUtils.isBlank(drugSpecIds)) {
                    indexResult += "品规名称不存在、";
                }else {
                    String[] split = drugSpecIds.split(",");
                    drugId = split[0];
                    specDrugId = split[1];
                    biz.setDrugId(drugId);
                    biz.setSpecDrugId(specDrugId);
                }
            }

            String saleDateText = biz.getSaleDateText();
            if (StringUtils.isBlank(saleDateText)){
                indexResult += "销售时间段为空、";
            }else {
                Date saleDate = DateUtil.convert(saleDateText);
                if (saleDate == null){
                    indexResult += "销售时间段格式错误（如：2018-10-10）、";
                }else {
                    biz.setSaleDate(saleDate);
                }
            }


            if ("申请".equals(biz.getType()) ){
                String traceStartDateText = biz.getTraceStartDateText();
                Date traceStartDate = null;
                if (StringUtils.isBlank(traceStartDateText)){
                    indexResult += "跟踪开始时间为空、";
                }else {
                    traceStartDate = DateUtil.convert(traceStartDateText);
                    if (traceStartDate == null){
                        indexResult += "跟踪开始时间格式错误（如：2018-10-10）、";
                    }else {
                        biz.setTraceStartDate(traceStartDate);
                    }
                }

                Date traceEndDate = null;
                String traceEndDateText = biz.getTraceEndDateText();
                if (StringUtils.isBlank(traceEndDateText)){
                    indexResult += "跟踪结束时间为空、";
                }else {
                    traceEndDate = DateUtil.convert(traceEndDateText);
                    if (traceEndDate == null){
                        indexResult += "跟踪结束时间格式错误（如：2018-10-10）、";
                    }else {
                        biz.setTraceEndDate(traceEndDate);
                    }
                }
            }

            java.util.Date saleDate = biz.getSaleDate();
            if(StringUtils.isNotBlank(hospitalId) && StringUtils.isNotBlank(drugId) && StringUtils.isNotBlank(specDrugId) &&
                    saleDate != null){
                List<Biz> bizList = this.findList(new Biz(saleDate,hospitalId, drugId, specDrugId));
                if (CollectionUtils.isNotEmpty(bizList)){
                    indexResult += "当前医院对应品规在此销售时间已分配业务员、";
                }
            }

            if (StringUtils.isNotBlank(indexResult)){
                int index = list.indexOf(biz )+2;
                indexResult = "第"+index+"行数据有误："+indexResult+"<br/>";
                result += indexResult;
                continue;
            }

            biz.setApprove(CmsConstant.APPROVING);
        }
        if (StringUtils.isBlank(result)){
            this.saveBatch(list);
        }

        return result;
    }


    @Override
    public Biz findForOrder(Biz biz) {
        return bizDao.findForOrder(biz);
    }
}
