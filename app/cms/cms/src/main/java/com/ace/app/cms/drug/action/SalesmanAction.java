package com.ace.app.cms.drug.action;


import com.ace.app.cms.drug.domain.Biz;
import com.ace.app.cms.drug.domain.Department;
import com.ace.app.cms.drug.domain.Salesman;
import com.ace.app.cms.drug.model.SalesmanModel;
import com.ace.app.cms.drug.service.BizService;
import com.ace.app.cms.drug.service.DepartmentService;
import com.ace.app.cms.drug.service.SalesmanService;
import com.ace.framework.base.BaseAction;
import com.ace.framework.base.JsonResultUtil;
import com.ace.framework.base.Page;
import com.ace.framework.util.ExecutionContext;
import javax.annotation.Resource;
import java.util.*;

import org.springframework.context.annotation.Scope;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import com.ace.app.cms.CmsConstant;
import com.ace.app.cms.account.AccountService;
import org.springframework.dao.DuplicateKeyException;

/**
* 业务员
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
@Scope("prototype")
public class SalesmanAction  extends BaseAction<SalesmanModel> {
    @Resource
    private SalesmanService salesmanService;
    @Resource
    private AccountService accountService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private BizService bizService;


    public String index() {
        super.setTarget("index");
        setDept();
        return COMMON;
    }

    public String editSalesmanIndex(){
        setDept();
        super.setTarget("editSalesmanIndex");
        return COMMON;
    }

    public String editSalesman(){
        Salesman biz = model.getSalesman();
        biz.setIds(Arrays.asList(biz.getId().split(",")));
        biz.setId(null);
        biz.setApprove("PASSED");
        salesmanService.update(biz);
        return renderJson(JsonResultUtil.location("salesman.index.do"));
    }

    private void setDept(){
        Department entity = new Department();
        entity.setApprove(CmsConstant.PASSED);
        List<Department> departments = departmentService.findList(entity);
        model.setDepartments(departments);
    }

    public String addOrEditIndex(){
        String id = model.getId();
        Salesman salesman = null;
        if (StringUtils.isBlank(id)){
            salesman = new Salesman();
        }else {
            salesman = salesmanService.getById(id);
        }

        model.setSalesman(salesman);
        setDept();
        super.setTarget("addOrEdit");
        return COMMON;
    }

    public String addOrEdit(){
        Salesman salesman = model.getSalesman();
        try {
            salesman.setName(salesman.getName().trim());
            if (StringUtils.isBlank(salesman.getId())){
                salesman.setId(null);
                salesman.setApprove(CmsConstant.APPROVING);
                salesmanService.save(salesman);
            }else {

                salesmanService.update(salesman);
            }
        } catch (DuplicateKeyException e) {
            return renderJson(JsonResultUtil.err("姓名已存在"));
        }

        return renderJson(JsonResultUtil.location("salesman.index.do"));
    }

    public String search() {
        Salesman salesman = model.getSalesman();
        if(salesman==null){
            salesman=new Salesman();
        }

        salesman.setCorpCode(ExecutionContext.getCorpCode());
        Page<Salesman>  page = model.getPage();
        page = salesmanService.search(salesman, page);
        List<Salesman> rows = page.getRows();
        if (CollectionUtils.isEmpty(rows)){
            return renderJson(page);
        }

        List<Department> departments = departmentService.findList(null);
        Map<String,String> idNameMap = new HashMap<>(departments.size());
        for (Department department : departments) {
            idNameMap.put(department.getId(),department.getName());
        }
        Map<String,String> aidNameMap = accountService.getAidNameMap();
        for (Salesman row : rows) {
            row.setCreateByName(aidNameMap.get(row.getCreateBy()));
            row.setDepartmentName(idNameMap.get(row.getDepartmentId()));
        }

        return renderJson(page);
    }


    /**
    * 删除
    * @return
    */
    public String remove() {
        String id = model.getId();
        if (!checkBizDel(id)){
            return renderJson(JsonResultUtil.err("业务管理中存在关联数据，无法删除！"));
        }
        salesmanService.deleteById(model.getId());
        return renderJson(JsonResultUtil.location("salesman.index.do"));
    }
    private boolean checkBizDel(String id){
        Biz huming = new Biz();
        huming.setSalesmanId(id);
        List<Biz> list =bizService.findList(huming);
        return CollectionUtils.isEmpty(list) || list.size() <= 0;
    }
    public String removeBatch() {

        String ids[]=request.getParameterValues("ids[]");
        if(ids.length>0){
            for (String id : ids) {

                if (!checkBizDel(id)){
                   // salesmanService.getSalesmanIdNameMap().get(id);
                    return renderJson(JsonResultUtil.err(salesmanService.getSalesmanIdNameMap().get(id)+" -业务管理中存在关联数据，无法删除！"));
                }
            }
            salesmanService.batchRemove(ids,ExecutionContext.getCorpCode(),ExecutionContext.getParentCorpCode());
        }

        return renderJson(JsonResultUtil.location("salesman.index.do"));
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
        Salesman salesman = new Salesman();
        salesman.setIds(ids);
        salesman.setApprove(CmsConstant.PASSED);
        salesmanService.update(salesman);
        return renderJson(JsonResultUtil.location("salesman.index.do"));
    }

}
