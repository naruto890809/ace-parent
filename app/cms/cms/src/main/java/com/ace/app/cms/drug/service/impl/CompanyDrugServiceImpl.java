package com.ace.app.cms.drug.service.impl;

import com.ace.app.cms.drug.dao.CompanyDrugDao;
import com.ace.app.cms.drug.domain.CompanyDrug;
import com.ace.app.cms.drug.service.CompanyDrugService;
import com.ace.app.cms.CmsConstant;
import com.ace.app.cms.drug.service.CompanyService;
import com.ace.app.cms.drug.service.DrugSpecService;
import com.ace.framework.base.BaseCrudServiceImpl;
import com.ace.framework.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ace.framework.base.BaseDao;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
* 商业公司药品关联表

* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
@Service("companyDrugService")
public class CompanyDrugServiceImpl  extends BaseCrudServiceImpl<CompanyDrug> implements CompanyDrugService {
    @Resource
    private CompanyDrugDao companyDrugDao;
    @Resource
    private DrugSpecService drugSpecService;
    @Resource
    private CompanyService companyService;

    @Override
    public BaseDao<CompanyDrug> getEntityDao() {
        return companyDrugDao;
    }

    @Override
    public List findListForExport(CompanyDrug condition) {
        List<CompanyDrug> list = this.findList(condition);
        if (CollectionUtils.isEmpty(list)){
            return list;
        }
        Map<String, String> idsDrugSpecNameStrMap = drugSpecService.idsDrugSpecNameStrMap();
        Map<String, String> comIdNameMap = companyService.getComIdNameMap();
        for (CompanyDrug row : list) {
            row.setSpecDrugName(idsDrugSpecNameStrMap.get(row.getDrugId() + "," + row.getSpecDrugId()));
            row.setCompanyName(comIdNameMap.get(row.getCompanyId()));
            row.setStartTimeText(DateUtil.convertTo_yyyy_MM_dd(row.getStartTime()));
            row.setEndTimeText(DateUtil.convertTo_yyyy_MM_dd(row.getEndTime()));
            row.setBiddingPriceText(row.getBiddingPrice().toString());
            row.setBillingPriceText(row.getBillingPrice().toString());
            row.setDeductionRateText(row.getDeductionRate().toString());
        }
        return list;
    }

    @Override
    public String importBatch(List<CompanyDrug> list,String extParam) {
        String result = "";
        Map<String, String> drugSpecNameIdsStrMap = drugSpecService.drugSpecNameIdsStrMap();
        Map<String, String> comNameIdMap = companyService.getComNameIdMap();
        for (CompanyDrug biz : list) {
            String indexResult = "";
            String salesmanName = biz.getCompanyName();
            String salesmanId = comNameIdMap.get(salesmanName);
            if (StringUtils.isBlank(salesmanName)){
                indexResult += "商业公司为空、";
            }else {
                if ( StringUtils.isBlank(salesmanId)){
                    indexResult += "商业公司不存在、";
                }else {
                    biz.setCompanyId(salesmanId);
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


            BigDecimal biddingPrice = biz.getBiddingPrice();
            if (biddingPrice == null){
                indexResult += "中标价为空、";
            }
            /*if (biz.getBillingPrice() == null){
                indexResult += "开票价为空、";
            }*/
            BigDecimal deductionRate = biz.getDeductionRate();
            if (deductionRate == null){
                indexResult += "扣率为空、";
            }

            if (biddingPrice != null && deductionRate != null){
                BigDecimal billingPrice = biddingPrice.multiply((new BigDecimal(1).subtract
                        (deductionRate.divide(new BigDecimal(100),4,BigDecimal.ROUND_UP))));
                biz.setBillingPrice(billingPrice);
            }

            String saleDateText = biz.getStartTimeText();
            if (StringUtils.isBlank(saleDateText)){
                indexResult += "开始时间为空、";
            }else {
                Date saleDate = DateUtil.convert(saleDateText);
                if (saleDate == null){
                    indexResult += "开始时间格式错误（如：2018-10-10）、";
                }else {
                    biz.setStartTime(saleDate);
                }
            }


            String traceStartDateText = biz.getEndTimeText();
            Date traceStartDate = null;
            if (StringUtils.isBlank(traceStartDateText)){
                indexResult += "结束时间为空、";
            }else {
                traceStartDate = DateUtil.convert(traceStartDateText);
                if (traceStartDate == null){
                    indexResult += "结束时间格式错误（如：2018-10-10）、";
                }else {
                    biz.setEndTime(traceStartDate);
                }
            }

            java.util.Date startTime = biz.getStartTime();
            java.util.Date endTime = biz.getEndTime();
            String companyId = biz.getCompanyId();
            String drugId1 = biz.getDrugId();
            String specDrugId1 = biz.getSpecDrugId();
            if (startTime != null && endTime != null && StringUtils.isNotBlank(companyId)
                    && StringUtils.isNotBlank(drugId1) && StringUtils.isNotBlank(specDrugId1)){
                CompanyDrug companyDrugTmp = new CompanyDrug(startTime, endTime, companyId,
                        drugId1, specDrugId1);
                List<CompanyDrug> listTmp = this.findList(companyDrugTmp);
                if (CollectionUtils.isNotEmpty(listTmp)){
                    indexResult += "当前商业公司对应品规在时间段已设置价格、";
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
    public CompanyDrug findByTime(CompanyDrug companyDrug) {
        return companyDrugDao.findByTime(companyDrug);
    }

    @Override
    public CompanyDrug findForOrder(CompanyDrug companyDrug) {
        return companyDrugDao.findForOrder(companyDrug);
    }
}
