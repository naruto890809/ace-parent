package com.ace.app.cms.drug.service.impl;

import com.ace.app.cms.account.AccountService;
import com.ace.app.cms.drug.dao.RebateDao;
import com.ace.app.cms.drug.domain.CompanyDrug;
import com.ace.app.cms.drug.domain.Hospital;
import com.ace.app.cms.drug.domain.Rebate;
import com.ace.app.cms.drug.service.*;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseCrudServiceImpl;
import com.ace.framework.util.DateUtil;
import com.ace.framework.util.common.NumberUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ace.framework.base.BaseDao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* 返利设置

* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
@Service("rebateService")
public class RebateServiceImpl  extends BaseCrudServiceImpl<Rebate> implements RebateService {
    @Resource
    private RebateDao rebateDao;
    @Resource
    private RebateService rebateService;
    @Resource
    private AccountService accountService;
    @Resource
    private HospitalService hospitalService;
    @Resource
    private DrugSpecService drugSpecService;
    @Resource
    private DicService dicService;
    @Resource
    private CompanyService companyService;
    @Resource
    private CompanyDrugService companyDrugService;
    @Resource
    private DepartmentService departmentService;

    @Override
    public BaseDao<Rebate> getEntityDao() {
        return rebateDao;
    }

    @Override
    public List findListForExport(Rebate condition) {
        List<Rebate> list = this.findList(condition);
        if (CollectionUtils.isEmpty(list)){
            return list;
        }

        Map<String,String> aidNameMap = accountService.getAidNameMap();
        Map<String, Hospital> hosIdHosMap = hospitalService.getIdHosMap();
        Map<String, String> idsDrugSpecNameStrMap = drugSpecService.idsDrugSpecNameStrMap();
        Map<String, String> comIdNameMap = companyService.getComIdNameMap();
        Map<String, String> dicIdNameMap = dicService.getIdNameMap();
        Map<String, String> deptIdNameMap = departmentService.getDeptIdNameMap();

        for (Rebate row : list) {
            String companyId = row.getCompanyId();
            row.setCompanyName(comIdNameMap.get(companyId));
            String hospitalId = row.getHospitalId();
            Hospital hospital = hosIdHosMap.get(hospitalId);
            if (hospital != null){
                row.setHospitalName(hospital.getName());
                row.setHospitalArea(hospital.getArea());
                row.setHospitalDeptName(deptIdNameMap.get(hospital.getDepartmentId()));
            }

            row.setCreateByName(aidNameMap.get(row.getCreateBy()));
            String drugId = row.getDrugId();
            String specDrugId = row.getSpecDrugId();
            row.setSpecDrugName(idsDrugSpecNameStrMap.get(drugId +","+ specDrugId));
            row.setRebateStyleName(dicIdNameMap.get(row.getRebateStyle()));
            row.setPriceTopicName(dicIdNameMap.get(row.getPriceTopic()));

            Date brightExecuteDate = row.getBrightExecuteDate();
            row.setBrightExecuteDateText(DateUtil.convertTo_yyyy_MM_dd(brightExecuteDate));
            Date darkExecuteDate = row.getDarkExecuteDate();
            row.setDarkExecuteDateText(DateUtil.convertTo_yyyy_MM_dd(darkExecuteDate));

            BigDecimal rebatePrice = rebateService.getRebatePrice(row);
            row.setRebatePrice(rebatePrice);

            if (StringUtils.isNotBlank(companyId) && StringUtils.isNotBlank(drugId) && StringUtils.isNotBlank(specDrugId)
                    && !(darkExecuteDate== null && brightExecuteDate == null)){
                CompanyDrug companyDrug = companyDrugService.findByTime(new CompanyDrug(companyId, drugId, specDrugId,
                        brightExecuteDate, darkExecuteDate));
                if (companyDrug == null || companyDrug.getBiddingPrice().compareTo(BigDecimal.ZERO) <= 0){
                    row.setBiddingPrice(null);
                    //设置返利点数
                    row.setRebateRate(null);
                }else {
                    BigDecimal biddingPrice = companyDrug.getBiddingPrice();
                    row.setBiddingPrice(biddingPrice);
                    //设置返利点数
                    row.setRebateRate(NumberUtil.scale(rebatePrice.divide(biddingPrice, 2, RoundingMode.HALF_UP)).multiply(new BigDecimal(100)));
                }
            }

            BigDecimal executePrice = row.getExecutePrice();
            if (executePrice == null){
                row.setExecutePriceText("");
            }else {
                row.setExecutePriceText(executePrice.toString());
            }

            BigDecimal brightPrice = row.getBrightPrice();
            if (brightPrice == null){
                row.setBrightPriceText("");
            }else {
                row.setBrightPriceText(brightPrice.toString());
            }

            BigDecimal darkPrice = row.getDarkPrice();
            if (darkPrice == null){
                row.setDarkPriceText("");
            }else {
                row.setDarkPriceText(darkPrice.toString());
            }
        }
        return list;
    }

    @Override
    public BigDecimal getRebatePrice(Rebate rebate) {
        BigDecimal brightPrice = rebate.getBrightPrice();
        BigDecimal darkPrice = rebate.getDarkPrice();
        BigDecimal rebatePrice = BigDecimal.ZERO;
        if (darkPrice == null){
            darkPrice = BigDecimal.ZERO;
        }

        if (brightPrice == null){
            brightPrice = BigDecimal.ZERO;
        }

        if (brightPrice.compareTo(BigDecimal.ZERO) <= 0 && darkPrice.compareTo(BigDecimal.ZERO) > 0){
            rebate.setDifType("暗");
            rebatePrice = darkPrice;
        }

        if (darkPrice.compareTo(BigDecimal.ZERO) <= 0 && brightPrice.compareTo(BigDecimal.ZERO) > 0){
            rebate.setDifType("明");
            rebatePrice = brightPrice;
        }

        if (darkPrice.compareTo(BigDecimal.ZERO) > 0 && brightPrice.compareTo(BigDecimal.ZERO) > 0){
            rebate.setDifType("明+暗");
            rebatePrice = brightPrice.add(darkPrice);
        }

        return rebatePrice;
    }

    @Override
    public String checkRebate(Rebate rebate,String checkResult ) {
        Date brightExecuteDate = rebate.getBrightExecuteDate();
        BigDecimal darkPrice = rebate.getDarkPrice();

        Date darkExecuteDate = rebate.getDarkExecuteDate();
        if (darkPrice != null && darkExecuteDate == null){
            checkResult += "暗返单价不为空时，执行时间不能为空、";
        }
        if (darkPrice == null && darkExecuteDate != null){
            checkResult += "暗返执行时间不为空时，暗返单价不能为空、";
        }


        String companyId = rebate.getCompanyId();
        String drugId = rebate.getDrugId();
        String specDrugId = rebate.getSpecDrugId();
        if (StringUtils.isNotBlank(companyId) && StringUtils.isNotBlank(drugId) && StringUtils.isNotBlank(specDrugId)
                && !(darkExecuteDate== null && brightExecuteDate == null)){
            CompanyDrug companyDrug = companyDrugService.findByTime(new CompanyDrug(companyId, drugId, specDrugId,
                    brightExecuteDate, darkExecuteDate));
            if (companyDrug == null || companyDrug.getBiddingPrice().compareTo(BigDecimal.ZERO) <= 0){
                checkResult += "价格管理中商业公司对应品规在返利执行时间内未设置中标价、";
            }else {
                //设置中标价
                BigDecimal biddingPrice = companyDrug.getBiddingPrice();
                rebate.setBiddingPrice(biddingPrice);

                //设置明返单价    明返单价就是中标价减执行价
                BigDecimal executePrice = rebate.getExecutePrice();
                if (biddingPrice != null && executePrice != null){
                    rebate.setBrightPrice(biddingPrice.subtract(executePrice));
                }

                //设置返利单价
                BigDecimal rebatePrice = this.getRebatePrice(rebate);
                rebate.setRebatePrice(rebatePrice);

                //设置返利点数
                rebate.setRebateRate(rebatePrice.divide(biddingPrice, 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
            }
        }

        if (darkPrice!= null && darkPrice.compareTo(BigDecimal.ZERO) < 0){
            checkResult += "暗返金额不能是负数、";
        }

        BigDecimal brightPrice = rebate.getBrightPrice();
        if (brightPrice != null && brightPrice.compareTo(BigDecimal.ZERO) < 0){
            checkResult += "明返金额不能是负数（执行价大于中标价）、";
        }

        if (brightPrice != null && brightPrice.compareTo(BigDecimal.ZERO) > 0 && rebate.getBrightExecuteDate() == null){
            checkResult += "明返时间不能为空、";
        }

        return checkResult;
    }

    @Override
    public Rebate findForBright(Rebate rebate) {
        return rebateDao.findForBright(rebate);
    }
    @Override
    public Rebate findForDark(Rebate rebate) {
        return rebateDao.findForDark(rebate);
    }

    @Override
    public String importBatch(List<Rebate> list,String extParam) {
        String result = "";
        Map<String, String> hospitalNameIdMap = hospitalService.getNameIdMap();
        Map<String, String> drugSpecNameIdsStrMap = drugSpecService.drugSpecNameIdsStrMap();
        Map<String, String> dicNameIdMap = dicService.getNameIdMap();
        Map<String, String> comNameIdMap = companyService.getComNameIdMap();

        for (Rebate biz : list) {
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


            String hospitalName = biz.getHospitalName();
            String hospitalId = hospitalNameIdMap.get(hospitalName);
            if (StringUtils.isBlank(hospitalName)) {
                indexResult += "医院名称为空、";
            } else {
                if (StringUtils.isBlank(hospitalId)) {
                    indexResult += "医院不存在、";
                }else {
                    biz.setHospitalId(hospitalId);
                }
            }

            indexResult = this.checkRebate(biz,indexResult);

            String priceTopicName = biz.getPriceTopicName();
            if (StringUtils.isBlank(priceTopicName)) {
                indexResult += "议价主体为空、";
            } else {
                String priceTopic = dicNameIdMap.get(priceTopicName);
                if (StringUtils.isBlank(priceTopic)) {
                    indexResult += "议价主体不存在、";
                }else {
                    biz.setPriceTopic(priceTopic);
                }
            }

            String rebateStyleName = biz.getRebateStyleName();
            if (StringUtils.isBlank(rebateStyleName)) {
                indexResult += "返利形式为空、";
            } else {
                String rebateStyle = dicNameIdMap.get(rebateStyleName);
                if (StringUtils.isBlank(rebateStyle)) {
                    indexResult += "返利形式不存在、";
                } else {
                    biz.setRebateStyle(rebateStyle);
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


}
