package com.ace.app.cms.drug.action;


import com.ace.app.cms.drug.domain.Department;
import com.ace.app.cms.drug.model.DepartmentModel;
import com.ace.app.cms.drug.service.DepartmentService;
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
* 部门
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
@Scope("prototype")
public class DepartmentAction  extends BaseAction<DepartmentModel> {
    @Resource
    private DepartmentService departmentService;
    @Resource
    private AccountService accountService;


    public String index() {
        super.setTarget("index");
        return COMMON;
    }

    public String addOrEditIndex(){
        String id = model.getId();
        Department department = null;
        if (StringUtils.isBlank(id)){
            department = new Department();
        }else {
            department = departmentService.getById(id);
        }

        model.setDepartment(department);
        super.setTarget("addOrEdit");
        return COMMON;
    }

    public String addOrEdit(){
        Department department = model.getDepartment();
        try {
            department.setName(department.getName().trim());
            if (StringUtils.isBlank(department.getId())){
                department.setId(null);
                department.setApprove(CmsConstant.APPROVING);
                departmentService.save(department);
            }else {
                departmentService.update(department);
            }
        } catch (DuplicateKeyException e) {
            return renderJson(JsonResultUtil.err("部门名称已存在"));
        }

        return renderJson(JsonResultUtil.location("department.index.do"));
    }

    public String search() {
        Department department = model.getDepartment();
        if(department==null){
            department=new Department();
        }

        department.setCorpCode(ExecutionContext.getCorpCode());
        Page<Department>  page = model.getPage();
        page = departmentService.search(department, page);
        List<Department> rows = page.getRows();
        if (CollectionUtils.isEmpty(rows)){
            return renderJson(page);
        }

        Map<String,String> aidNameMap = accountService.getAidNameMap();
        for (Department row : rows) {
            row.setCreateByName(aidNameMap.get(row.getCreateBy()));
        }

        return renderJson(page);
    }


    /**
    * 删除
    * @return
    */
    public String remove() {
        departmentService.deleteById(model.getId());
        return renderJson(JsonResultUtil.location("department.index.do"));
    }

    public String removeBatch() {

        String ids[]=request.getParameterValues("ids[]");
        if(ids.length>0){
            departmentService.batchRemove(ids,ExecutionContext.getCorpCode(),ExecutionContext.getParentCorpCode());
        }

        return renderJson(JsonResultUtil.location("department.index.do"));
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
        Department department = new Department();
        department.setIds(ids);
        department.setApprove(CmsConstant.PASSED);
        departmentService.update(department);
        return renderJson(JsonResultUtil.location("department.index.do"));
    }

}
