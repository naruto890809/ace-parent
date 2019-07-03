package com.ace.app.cms.drug.action;


import com.ace.app.cms.drug.domain.DepartmentJob;
import com.ace.app.cms.drug.domain.Drug;
import com.ace.app.cms.drug.model.DepartmentJobModel;
import com.ace.app.cms.drug.service.DepartmentJobService;
import com.ace.app.cms.drug.service.DepartmentService;
import com.ace.app.cms.drug.service.DrugService;
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

/**
* 任务管理
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-10-18 21:27:37
*/
@Scope("prototype")
public class DepartmentJobAction  extends BaseAction<DepartmentJobModel> {
    @Resource
    private DepartmentJobService departmentJobService;
    @Resource
    private AccountService accountService;
    @Resource
    private DrugService drugService;


    public String index() {
        setModel();

        super.setTarget("index");
        return COMMON;
    }

    private void setModel() {
        Drug entity = new Drug();
        entity.setApprove(CmsConstant.PASSED);
        List<Drug> drugs = drugService.findList(entity);
        model.setDrugs(drugs);
    }

    public String addOrEditIndex(){
        String id = model.getId();
        DepartmentJob departmentJob = null;
        if (StringUtils.isBlank(id)){
            departmentJob = new DepartmentJob();
        }else {
            departmentJob = departmentJobService.getById(id);
        }

        model.setDepartmentJob(departmentJob);
        setModel();
        super.setTarget("addOrEdit");
        return COMMON;
    }

    public String addOrEdit(){
        DepartmentJob departmentJob = model.getDepartmentJob();
        if (StringUtils.isBlank(departmentJob.getId())){
            departmentJob.setId(null);
            departmentJobService.save(departmentJob);
        }else {
            departmentJobService.update(departmentJob);
        }

        return renderJson(JsonResultUtil.location("departmentJob.index.do"));
    }

    public String search() {
        DepartmentJob departmentJob = model.getDepartmentJob();
        if(departmentJob==null){
            departmentJob=new DepartmentJob();
        }

        departmentJob.setCorpCode(ExecutionContext.getCorpCode());
        Page<DepartmentJob>  page = model.getPage();
        page = departmentJobService.search(departmentJob, page);
        List<DepartmentJob> rows = page.getRows();
        if (CollectionUtils.isEmpty(rows)){
            return renderJson(page);
        }

        Map<String,String> aidNameMap = accountService.getAidNameMap();
        Map<String, String> drugIdNameMap = drugService.getDrugIdNameMap();
        for (DepartmentJob row : rows) {
            row.setCreateByName(aidNameMap.get(row.getCreateBy()));
            row.setDrugName(drugIdNameMap.get(row.getDrugId()));
        }

        return renderJson(page);
    }


    /**
    * 删除
    * @return
    */
    public String remove() {
        departmentJobService.deleteById(model.getId());
        return renderJson(JsonResultUtil.location("departmentJob.index.do"));
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
        DepartmentJob departmentJob = new DepartmentJob();
        departmentJob.setIds(ids);
        departmentJobService.update(departmentJob);
        return renderJson(JsonResultUtil.location("departmentJob.index.do"));
    }

}
