package com.ace.app.cms.drug.action;


import com.ace.app.cms.drug.domain.*;
import com.ace.app.cms.drug.model.CompanyModel;
import com.ace.app.cms.drug.service.*;
import com.ace.framework.base.BaseAction;
import com.ace.framework.base.JsonResultUtil;
import com.ace.framework.base.Page;
import com.ace.framework.util.ExecutionContext;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import com.ace.app.cms.CmsConstant;
import java.util.Arrays;
import com.ace.app.cms.account.AccountService;
import org.springframework.dao.DuplicateKeyException;

/**
* 商业公司
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
@Scope("prototype")
public class CompanyAction  extends BaseAction<CompanyModel> {
    @Resource
    private CompanyService companyService;
    @Resource
    private AccountService accountService;
    @Resource
    private DicService dicService;
    @Resource
    private SellAreaService sellAreaService;
    @Resource
    private RebateService rebateService;
    @Resource
    private CompanyDrugService companyDrugService;
    @Resource
    private BizService bizService;


    public String index() {
        setModel();
        super.setTarget("index");
        return COMMON;
    }

    private void  setModel(){
        List<Dic> dics = dicService.findByCode(CmsConstant.DIC_TYPE_COMPANY_CHANNEL);
        model.setChannels(dics);
    }

    public String addOrEditIndex(){
        String id = model.getId();
        Company company = null;
        if (StringUtils.isBlank(id)){
            company = new Company();
        }else {
            company = companyService.getById(id);
        }

        model.setCompany(company);
        setModel();
        super.setTarget("addOrEdit");
        return COMMON;
    }

    public String addOrEdit(){
        Company company = model.getCompany();
        try {
            company.setName(company.getName().trim());
            if (StringUtils.isBlank(company.getId())){
                company.setId(null);
                company.setApprove(CmsConstant.APPROVING);
                companyService.save(company);
            }else {
                companyService.update(company);
            }
        }  catch (DuplicateKeyException e) {
            return renderJson(JsonResultUtil.err("公司名称已存在"));
        }

        return renderJson(JsonResultUtil.location("company.index.do"));
    }

    public String search() {
        Company company = model.getCompany();
        if(company==null){
            company=new Company();
            company.setProvinceSearch("浙江省");
        }

        company.setSellAreaName(company.getProvinceSearch());
        company.setSellAreaId(company.getCitySearch());

        company.setCorpCode(ExecutionContext.getCorpCode());
        Page<Company>  page = model.getPage();
        page = companyService.search(company, page);
        List<Company> rows = page.getRows();
        if (CollectionUtils.isEmpty(rows)){
            return renderJson(page);
        }

        Map<String,String> channelIdNameMap = dicService.getIdNameMap();
        Map<String,String> aidNameMap = accountService.getAidNameMap();
        for (Company row : rows) {
            row.setCreateByName(aidNameMap.get(row.getCreateBy()));
            row.setChannelName(channelIdNameMap.get(row.getChannel()));
        }

        return renderJson(page);
    }


    /**
    * 删除
    * @return
    */
    public String remove() {
        String id = model.getId();
        if (!checkRebateDel(id)){
            return renderJson(JsonResultUtil.err("返利信息中存在关联数据，无法删除！"));
        }
        if (!checkCompanyDrugDel(id)){
            return renderJson(JsonResultUtil.err("价格管理中存在关联数据，无法删除！"));
        }
        companyService.deleteById(model.getId());
        return renderJson(JsonResultUtil.location("company.index.do"));
    }

    private boolean checkRebateDel(String id){
        Rebate rebate = new Rebate();
        rebate.setCompanyId(id);
        List<Rebate> list = rebateService.findList(rebate);
        return CollectionUtils.isEmpty(list) || list.size() <= 0;
    }

    private boolean checkCompanyDrugDel(String id){
        CompanyDrug rebate = new CompanyDrug();
        rebate.setCompanyId(id);
        List<CompanyDrug> list = companyDrugService.findList(rebate);
        return CollectionUtils.isEmpty(list) || list.size() <= 0;
    }

    public String removeBatch() {

        String ids[]=request.getParameterValues("ids[]");
        if(ids.length>0){
            for (String id : ids) {
                String cname = companyService.getComIdNameMap().get(id);
                if (!checkRebateDel(id)){
                    return renderJson(JsonResultUtil.err(cname+" -返利信息中存在关联数据，无法删除！"));
                }
                if (!checkCompanyDrugDel(id)){
                    return renderJson(JsonResultUtil.err(cname+" -价格管理中存在关联数据，无法删除！"));
                }
            }

            companyService.batchRemove(ids,ExecutionContext.getCorpCode(),ExecutionContext.getParentCorpCode());
        }

        return renderJson(JsonResultUtil.location("company.index.do"));
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
        Company company = new Company();
        company.setIds(ids);
        company.setApprove(CmsConstant.PASSED);
        companyService.update(company);
        return renderJson(JsonResultUtil.location("company.index.do"));
    }

}
