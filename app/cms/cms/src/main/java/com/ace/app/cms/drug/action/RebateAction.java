package com.ace.app.cms.drug.action;


import com.ace.app.cms.drug.domain.CompanyDrug;
import com.ace.app.cms.drug.domain.Dic;
import com.ace.app.cms.drug.domain.Hospital;
import com.ace.app.cms.drug.domain.Rebate;
import com.ace.app.cms.drug.model.RebateModel;
import com.ace.app.cms.drug.service.*;
import com.ace.framework.base.BaseAction;
import com.ace.framework.base.JsonResultUtil;
import com.ace.framework.base.Page;
import com.ace.framework.util.DateUtil;
import com.ace.framework.util.ExecutionContext;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import com.ace.framework.util.common.NumberUtil;
import org.springframework.context.annotation.Scope;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import com.ace.app.cms.CmsConstant;
import com.ace.app.cms.account.AccountService;

/**
* 返利设置
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
@Scope("prototype")
public class RebateAction  extends BaseAction<RebateModel> {
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

    private void setModel(){
//        model.setHospitalIdNameMap(hospitalService.getIdNameMap());
        Hospital hospital = new Hospital();
        hospital.setApprove("PASSED");
        model.setHospitals(hospitalService.findList(hospital));
        model.setDrugSpecIdsNamesMap(drugSpecService.idsDrugSpecNameStrMap());
        model.setCompanyIdNameMap(companyService.getComIdNameMap());
        List<Dic> priceTopics = dicService.findByCode(CmsConstant.DIC_TYPE_REBATE_PRICE_TOPIC);
        model.setPriceTopics(priceTopics);
        List<Dic> rebateStyles = dicService.findByCode(CmsConstant.DIC_TYPE_REBATE_STYLE);
        model.setRebateStyles(rebateStyles);
    }

    public String index() {
        super.setTarget("index");
        setModel();
        return COMMON;
    }

    public String addOrEditIndex(){
        String id = model.getId();
        Rebate rebate = null;
        if (StringUtils.isBlank(id)){
            rebate = new Rebate();
        }else {
            rebate = rebateService.getById(id);
            rebate.setBrightExecuteDateText(DateUtil.convertTo_yyyy_MM_dd(rebate.getBrightExecuteDate()));
            rebate.setDarkExecuteDateText(DateUtil.convertTo_yyyy_MM_dd(rebate.getDarkExecuteDate()));
            Map<String, String> idsDrugSpecNameStrMap = drugSpecService.idsDrugSpecNameStrMap();
            rebate.setSpecDrugName(idsDrugSpecNameStrMap.get(rebate.getDrugId() +","+ rebate.getSpecDrugId()));

        }

        model.setRebate(rebate);
        setModel();
        super.setTarget("addOrEdit");
        return COMMON;
    }

    public String addOrEdit(){
        Rebate rebate = model.getRebate();
        String checkResult = "";
        Map<String, String> drugSpecNameIdsStrMap = drugSpecService.drugSpecNameIdsStrMap();
        String drugSpecIdStr = drugSpecNameIdsStrMap.get(rebate.getSpecDrugName());
        if (StringUtils.isBlank(drugSpecIdStr)){
            checkResult += "品规不能为空、";
        }
        String[] split = drugSpecIdStr.split(",");
        rebate.setDrugId(split[0]);
        rebate.setSpecDrugId(split[1]);
        checkResult = rebateService.checkRebate(rebate,checkResult);

        if (StringUtils.isNotBlank(checkResult)){
            return renderJson(JsonResultUtil.err(checkResult));
        }

        rebate.setApprove(CmsConstant.APPROVING);
        if (StringUtils.isBlank(rebate.getId())){
            rebate.setId(null);
            rebateService.save(rebate);
        }else {
            rebateService.update(rebate);
        }

        return renderJson(JsonResultUtil.location("rebate.index.do"));
    }

    public String search() {
        Rebate rebate = model.getRebate();
        if(rebate==null){
            rebate=new Rebate();
        }

        rebate.setCorpCode(ExecutionContext.getCorpCode());
        Page<Rebate>  page = model.getPage();
        String specDrugName = rebate.getSpecDrugName();
        if (StringUtils.isNotBlank(specDrugName)){
            Map<String, String> drugSpecNameIdsStrMap = drugSpecService.drugSpecNameIdsStrMap();
            String drugSpecIdStr = drugSpecNameIdsStrMap.get(specDrugName);
            if (StringUtils.isNotBlank(drugSpecIdStr)){
                String[] split = drugSpecIdStr.split(",");
                rebate.setDrugId(split[0]);
                rebate.setSpecDrugId(split[1]);
            }
        }

        page = rebateService.search(rebate, page);
        List<Rebate> rows = page.getRows();
        if (CollectionUtils.isEmpty(rows)){
            return renderJson(page);
        }

        Map<String,String> aidNameMap = accountService.getAidNameMap();
        Map<String, Hospital> hosIdHosMap = hospitalService.getIdHosMap();
        Map<String, String> idsDrugSpecNameStrMap = drugSpecService.idsDrugSpecNameStrMap();
        Map<String, String> comIdNameMap = companyService.getComIdNameMap();
        Map<String, String> dicIdNameMap = dicService.getIdNameMap();
        Map<String, String> deptIdNameMap = departmentService.getDeptIdNameMap();

        for (Rebate row : rows) {
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
                    row.setRebateRate(BigDecimal.ZERO);
                }else {
                    BigDecimal biddingPrice = companyDrug.getBiddingPrice();
                    row.setBiddingPrice(biddingPrice);
                    //设置返利点数
                    row.setRebateRate(rebatePrice.divide(biddingPrice, 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
                }
            }else {
                row.setRebateRate(BigDecimal.ZERO);
            }

        }

        return renderJson(page);
    }


    /**
    * 删除
    * @return
    */
    public String remove() {
        rebateService.deleteById(model.getId());
        return renderJson(JsonResultUtil.location("rebate.index.do"));
    }

    public String removeBatch() {

        String ids[]=request.getParameterValues("ids[]");
        if(ids.length>0){
            rebateService.batchRemove(ids,ExecutionContext.getCorpCode(),ExecutionContext.getParentCorpCode());
        }

        return renderJson(JsonResultUtil.location("rebate.index.do"));
    }

    /**
    * 审批
    * @return
    */
    public String approve() {
        String idsStr = model.getIdsStr();
        if (StringUtils.isEmpty(idsStr)){
            return renderJson(JsonResultUtil.err("请选择操作的列"));
        }

        List<String> ids = Arrays.asList(idsStr.split(","));
        Rebate rebate = new Rebate();
        rebate.setIds(ids);
        rebate.setApprove(CmsConstant.PASSED);
        rebate.setApproveCheck("YES");
        rebateService.update(rebate);
        return renderJson(JsonResultUtil.location("rebate.index.do"));
    }


    /**
    * 审批
    * @return
    */
    public String freeze() {
        String id = model.getId();
        Rebate rebate = new Rebate();
        rebate.setId(id);
        rebate.setApprove(CmsConstant.FREEZED);
        rebateService.update(rebate);
        return renderJson(JsonResultUtil.location("rebate.index.do"));
    }

}
