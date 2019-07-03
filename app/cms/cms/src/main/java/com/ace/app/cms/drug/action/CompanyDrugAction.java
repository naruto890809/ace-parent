package com.ace.app.cms.drug.action;


import com.ace.app.cms.drug.domain.CompanyDrug;
import com.ace.app.cms.drug.model.CompanyDrugModel;
import com.ace.app.cms.drug.service.CompanyDrugService;
import com.ace.app.cms.drug.service.CompanyService;
import com.ace.app.cms.drug.service.DrugSpecService;
import com.ace.framework.base.BaseAction;
import com.ace.framework.base.JsonResultUtil;
import com.ace.framework.base.Page;
import com.ace.framework.util.DateUtil;
import com.ace.framework.util.ExecutionContext;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import org.springframework.context.annotation.Scope;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import com.ace.app.cms.CmsConstant;
import com.ace.app.cms.account.AccountService;
import org.springframework.dao.DuplicateKeyException;

/**
* 商业公司药品关联表
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
@Scope("prototype")
public class CompanyDrugAction  extends BaseAction<CompanyDrugModel> {
    @Resource
    private CompanyDrugService companyDrugService;
    @Resource
    private AccountService accountService;
    @Resource
    private DrugSpecService drugSpecService;
    @Resource
    private CompanyService companyService;

    public String index() {
        setModel();
        super.setTarget("index");
        return COMMON;
    }

    private void setModel(){
        model.setCompanyIdNameMap(companyService.getComIdNameMap());
        model.setDrugSpecIdsNamesMap(drugSpecService.idsDrugSpecNameStrMap());
    }

    public String addOrEditIndex(){
        String id = model.getId();
        CompanyDrug companyDrug = null;
        if (StringUtils.isBlank(id)){
            companyDrug = new CompanyDrug();
        }else {
            companyDrug = companyDrugService.getById(id);
            Map<String, String> idsDrugSpecNameStrMap = drugSpecService.idsDrugSpecNameStrMap();
            companyDrug.setSpecDrugName(idsDrugSpecNameStrMap.get(companyDrug.getDrugId()+","+companyDrug.getSpecDrugId()));
            companyDrug.setStartTimeText(DateUtil.convertTo_yyyy_MM_dd(companyDrug.getStartTime()));
            companyDrug.setEndTimeText(DateUtil.convertTo_yyyy_MM_dd(companyDrug.getEndTime()));
        }

        model.setCompanyDrug(companyDrug);
        setModel();
        super.setTarget("addOrEdit");
        return COMMON;
    }

    public String addOrEdit(){
        CompanyDrug companyDrug = model.getCompanyDrug();
        Map<String, String> drugSpecNameIdsStrMap = drugSpecService.drugSpecNameIdsStrMap();
        String drugSpecIdStr = drugSpecNameIdsStrMap.get(companyDrug.getSpecDrugName());
        if (StringUtils.isBlank(drugSpecIdStr)){
            return renderJson(JsonResultUtil.err("请选择品规"));
        }
        String[] split = drugSpecIdStr.split(",");
        companyDrug.setDrugId(split[0]);
        companyDrug.setSpecDrugId(split[1]);

        try {
            //开票价=中标价*（1-（扣率/100））减法
            BigDecimal billingPrice = companyDrug.getBiddingPrice().multiply((new BigDecimal(1).subtract(companyDrug.getDeductionRate().divide(new BigDecimal(100),4,BigDecimal.ROUND_UP))));
            companyDrug.setBillingPrice(billingPrice);
            String id = companyDrug.getId();
            if (StringUtils.isBlank(id)){
                companyDrug.setId(null);
                companyDrug.setApprove(CmsConstant.APPROVING);
                CompanyDrug companyDrugTmp = new CompanyDrug(companyDrug.getStartTime(),companyDrug.getEndTime(),companyDrug.getCompanyId(),
                        companyDrug.getDrugId(), companyDrug.getSpecDrugId());
                List<CompanyDrug> list = companyDrugService.findList(companyDrugTmp);
                if (CollectionUtils.isNotEmpty(list)){
                    return renderJson(JsonResultUtil.err("当前商业公司对应品规在时间段已设置价格"));
                }

                companyDrugService.save(companyDrug);
            }else {
                CompanyDrug companyDrugTmp = new CompanyDrug(companyDrug.getStartTime(),companyDrug.getEndTime(),companyDrug.getCompanyId(),
                        companyDrug.getDrugId(), companyDrug.getSpecDrugId());
                List<CompanyDrug> list = companyDrugService.findList(companyDrugTmp);
                if (CollectionUtils.isNotEmpty(list)){
                    for (CompanyDrug drug : list) {
                        if (!drug.getId().equals(id)){
                            return renderJson(JsonResultUtil.err("当前商业公司对应品规在时间段已设置价格"));
                        }
                    }
                }

                companyDrugService.update(companyDrug);
            }
        }  catch (Exception e) {
                return renderJson(JsonResultUtil.err("请按要求填写表单"));
        }

        return renderJson(JsonResultUtil.location("companyDrug.index.do"));
    }

    public String search() {
        CompanyDrug companyDrug = model.getCompanyDrug();
        if(companyDrug==null){
            companyDrug=new CompanyDrug();
        }

        companyDrug.setCorpCode(ExecutionContext.getCorpCode());
        Page<CompanyDrug>  page = model.getPage();

        String specDrugName = companyDrug.getSpecDrugName();
        if (StringUtils.isNotBlank(specDrugName)){
            Map<String, String> drugSpecNameIdsStrMap = drugSpecService.drugSpecNameIdsStrMap();
            String drugSpecIdStr = drugSpecNameIdsStrMap.get(specDrugName);
            if (StringUtils.isNotBlank(drugSpecIdStr)){
                String[] split = drugSpecIdStr.split(",");
                companyDrug.setDrugId(split[0]);
                companyDrug.setSpecDrugId(split[1]);
            }
        }


        page = companyDrugService.search(companyDrug, page);
        List<CompanyDrug> rows = page.getRows();
        if (CollectionUtils.isEmpty(rows)){
            return renderJson(page);
        }

        Map<String,String> aidNameMap = accountService.getAidNameMap();
        Map<String, String> idsDrugSpecNameStrMap = drugSpecService.idsDrugSpecNameStrMap();
        Map<String, String> comIdNameMap = companyService.getComIdNameMap();
        for (CompanyDrug row : rows) {
            row.setCreateByName(aidNameMap.get(row.getCreateBy()));
            row.setSpecDrugName(idsDrugSpecNameStrMap.get(row.getDrugId() +","+ row.getSpecDrugId()));
            row.setCompanyName(comIdNameMap.get(row.getCompanyId()));
            row.setStartTimeText(DateUtil.convertTo_yyyy_MM_dd(row.getStartTime()));
            row.setEndTimeText(DateUtil.convertTo_yyyy_MM_dd(row.getEndTime()));
        }

        return renderJson(page);
    }


    /**
    * 删除
    * @return
    */
    public String remove() {
        companyDrugService.deleteById(model.getId());
        return renderJson(JsonResultUtil.location("companyDrug.index.do"));
    }

    public String removeBatch() {

        String ids[]=request.getParameterValues("ids[]");
        if(ids.length>0){
            companyDrugService.batchRemove(ids,ExecutionContext.getCorpCode(),ExecutionContext.getParentCorpCode());
        }

        return renderJson(JsonResultUtil.location("companyDrug.index.do"));
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
        CompanyDrug companyDrug = new CompanyDrug();
        companyDrug.setIds(ids);
        companyDrug.setApprove(CmsConstant.PASSED);
        companyDrugService.update(companyDrug);
        return renderJson(JsonResultUtil.location("companyDrug.index.do"));
    }

}
