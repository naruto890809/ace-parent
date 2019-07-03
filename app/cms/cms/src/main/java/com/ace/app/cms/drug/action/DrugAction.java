package com.ace.app.cms.drug.action;


import com.ace.app.cms.drug.domain.*;
import com.ace.app.cms.drug.model.DrugModel;
import com.ace.app.cms.drug.service.*;
import com.ace.framework.base.BaseAction;
import com.ace.framework.base.JsonResultUtil;
import com.ace.framework.base.Page;
import com.ace.framework.util.ExecutionContext;
import javax.annotation.Resource;
import java.util.*;

import com.ace.framework.util.UUIDUtil;
import org.springframework.context.annotation.Scope;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import com.ace.app.cms.CmsConstant;
import com.ace.app.cms.account.AccountService;
import org.springframework.dao.DuplicateKeyException;

/**
* 药品
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
@Scope("prototype")
public class DrugAction  extends BaseAction<DrugModel> {
    @Resource
    private DrugService drugService;
    @Resource
    private AccountService accountService;
    @Resource
    private DrugSpecService drugSpecService;
    @Resource
    private RebateService rebateService;
    @Resource
    private CompanyDrugService companyDrugService;
    @Resource
    private BizService bizService;


    public String index() {
        super.setTarget("index");
        return COMMON;
    }

    public String addOrEditIndex(){
        String id = model.getId();
        Drug drug = null;
        if (StringUtils.isBlank(id)){
            drug = new Drug();
        }else {
            drug = drugService.getById(id);
            DrugSpec entity = new DrugSpec();
            entity.setDrugId(id);
            List<DrugSpec> drugSpecs = drugSpecService.findList(entity);
            model.setDrugSpecs(drugSpecs);
        }

        model.setDrug(drug);
        super.setTarget("addOrEdit");
        return COMMON;
    }



    private List<String> getSpecNames(String id) {
        if (StringUtils.isBlank(id)){
            return new ArrayList<>(0);
        }

        DrugSpec entity = new DrugSpec();
        entity.setDrugId(id);
        List<DrugSpec> drugSpecs = drugSpecService.findList(entity);
        List<String> specNames = new ArrayList<>(drugSpecs.size());
        for (DrugSpec spec : drugSpecs) {
            specNames.add(spec.getSpecName());
        }

        return specNames;
    }

    public String addOrEdit(){
        Drug drug = model.getDrug();
        String drugId = drug.getId();
        try {
            drug.setName(drug.getName().trim());
            if (StringUtils.isBlank(drugId)){
                drug.setId(null);
                drug.setApprove(CmsConstant.APPROVING);
                drugService.save(drug);
            }else {
                drugService.update(drug);
            }
        }  catch (DuplicateKeyException e) {
            return renderJson(JsonResultUtil.err("名称已存在"));
        }

        List<DrugSpec> drugSpecs = model.getDrugSpecs();
        if (CollectionUtils.isEmpty(drugSpecs)){
            return renderJson(JsonResultUtil.location("drug.index.do"));
        }

        DrugSpec entity = new DrugSpec();
        entity.setDrugId(drug.getId());
        drugSpecService.delete(entity);
        for (DrugSpec drugSpec : drugSpecs) {
            drugSpec.setDrugId(drug.getId());
            String id = drugSpec.getId();
            if (StringUtils.isBlank(id)){
                drugSpec.setId(UUIDUtil.genUUID());
            }
        }
        drugSpecService.saveBatch(drugSpecs);
        return renderJson(JsonResultUtil.location("drug.index.do"));
    }

    public String search() {
        Drug drug = model.getDrug();
        if(drug==null){
            drug=new Drug();
        }

        drug.setCorpCode(ExecutionContext.getCorpCode());
        Page<Drug>  page = model.getPage();
        page = drugService.search(drug, page);
        List<Drug> rows = page.getRows();
        if (CollectionUtils.isEmpty(rows)){
            return renderJson(page);
        }

        Map<String,String> aidNameMap = accountService.getAidNameMap();
        Map<String, String> drugIdSpecNameMap = drugSpecService.getDrugIdSpecNameMap();
        for (Drug row : rows) {
            row.setSpecName(drugIdSpecNameMap.get(row.getId()));
            row.setCreateByName(aidNameMap.get(row.getCreateBy()));
        }

        return renderJson(page);
    }


    /**
    * 删除
    * @return
    */
    public String remove() {
        //System.out.print("-------------------"+drugService.deleteById(model.getId())+"--------------");
       // drugService.deleteById(model.getId());

//        if(drugService.deleteById(model.getId()) ==0)
//            return renderJson(JsonResultUtil.err("数据已经被使用，删除失败！"));
//        else
//            return renderJson(JsonResultUtil.location("drug.index.do"));

        String id = model.getId();
        if (!checkRebateDel(id)){
            return renderJson(JsonResultUtil.err("返利信息中存在关联数据，无法删除！"));
        }
        if (!checkCompanyDrugDel(id)){
            return renderJson(JsonResultUtil.err("价格管理中存在关联数据，无法删除！"));
        }
        if (!checkBizDel(id)){
            return renderJson(JsonResultUtil.err("业务管理中存在关联数据，无法删除！"));
        }
        drugService.deleteById(model.getId());
        return renderJson(JsonResultUtil.location("drug.index.do"));
    }


    private boolean checkRebateDel(String id){
        Rebate rebate = new Rebate();
        rebate.setDrugId(id);
        List<Rebate> list = rebateService.findList(rebate);
        return CollectionUtils.isEmpty(list) || list.size() <= 0;
    }

    private boolean checkCompanyDrugDel(String id){
        CompanyDrug rebate = new CompanyDrug();
        rebate.setDrugId(id);
        List<CompanyDrug> list = companyDrugService.findList(rebate);
        return CollectionUtils.isEmpty(list) || list.size() <= 0;
    }

    private boolean checkBizDel(String id){
        Biz huming = new Biz();
        huming.setDrugId(id);
        List<Biz> list =bizService.findList(huming);
        return CollectionUtils.isEmpty(list) || list.size() <= 0;
    }

    public String removeBatch() {
        int cs = 0;
        String ids[]=request.getParameterValues("ids[]");
        if(ids.length>0){
            for (String id : ids) {
                String dname = drugService.getDrugIdNameMap().get(id);
                if (!checkRebateDel(id)){
                    return renderJson(JsonResultUtil.err(dname+" -返利信息中存在关联数据，无法删除！"));
                }
                if (!checkCompanyDrugDel(id)){
                    return renderJson(JsonResultUtil.err(dname+" -价格管理中存在关联数据，无法删除！"));
                }
                if (!checkBizDel(id)){
                    return renderJson(JsonResultUtil.err(dname+" -业务管理中存在关联数据，无法删除！"));
                }
            }



            cs = drugService.batchRemoveInt(ids, ExecutionContext.getCorpCode(), ExecutionContext.getParentCorpCode());
        }

        return renderJson(JsonResultUtil.location("company.index.do"));

//        if(ids.length == cs){
//            return renderJson(JsonResultUtil.location("drug.index.do"));
//        }else{
//            int cc = ids.length-cs;
//            String str = cc+"条数据已被使用无法删除！成功删除"+cs;
//            return renderJson(JsonResultUtil.err(str));
//
//        }

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
        Drug drug = new Drug();
        drug.setIds(ids);
        drug.setApprove(CmsConstant.PASSED);
        drugService.update(drug);
        return renderJson(JsonResultUtil.location("drug.index.do"));
    }

}
