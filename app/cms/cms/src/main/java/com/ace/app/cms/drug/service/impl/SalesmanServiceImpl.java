package com.ace.app.cms.drug.service.impl;

import com.ace.app.cms.drug.dao.SalesmanDao;
import com.ace.app.cms.drug.domain.Department;
import com.ace.app.cms.drug.domain.Salesman;
import com.ace.app.cms.drug.service.DepartmentService;
import com.ace.app.cms.drug.service.SalesmanService;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseCrudServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ace.framework.base.BaseDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 业务员

* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
@Service("salesmanService")
public class SalesmanServiceImpl  extends BaseCrudServiceImpl<Salesman> implements SalesmanService {
    @Resource
    private SalesmanDao salesmanDao;
    @Resource
    private DepartmentService departmentService;


    @Override
    public BaseDao<Salesman> getEntityDao() {
        return salesmanDao;
    }

    @Override
    public List findListForExport(Salesman condition) {
        List<Salesman> list = this.findList(condition);
        Map<String, String> idNameMap = getDeptIdNameMap();

        for (Salesman row : list) {
            row.setDepartmentName(idNameMap.get(row.getDepartmentId()));
        }
        return list;
    }

    private Map<String, String> getDeptIdNameMap() {
        List<Department> departments = departmentService.findList(null);
        Map<String,String> idNameMap = new HashMap<>(departments.size());
        for (Department department : departments) {
            idNameMap.put(department.getId(),department.getName());
        }
        return idNameMap;
    }
    private Map<String, String> getDeptNameIdMap() {
        Department entity = new Department();
        entity.setApprove(CmsConstant.PASSED);
        List<Department> departments = departmentService.findList(entity);
        Map<String,String> idNameMap = new HashMap<>(departments.size());
        for (Department department : departments) {
            idNameMap.put(department.getName(),department.getId());
        }
        return idNameMap;
    }

    @Override
    public String importBatch(List<Salesman> list,String extParam) {
        String result = "";
        List<Salesman> salesmans = this.findList(null);
        List<String> db = new ArrayList<>(salesmans.size());
        for (Salesman salesman : salesmans) {
            db.add(salesman.getName());
        }

        Map<String, String> deptNameIdMap = getDeptNameIdMap();
        for (Salesman salesman : list) {
            String indexResult = "";
            String name = salesman.getName();
            if (StringUtils.isBlank(name)){
                indexResult += "姓名为空、";
            }else {
                salesman.setName(salesman.getName().trim());
                if ( db.contains(name)){
                    indexResult += "姓名已存在、";
                }
            }
            String departmentName = salesman.getDepartmentName();
            if (StringUtils.isBlank(departmentName)) {
                indexResult += "部门名称为空、";
            } else {
                String deptId = deptNameIdMap.get(departmentName);
                if (StringUtils.isBlank(deptId)) {
                    indexResult += "部门不存在、";
                }else {
                    salesman.setDepartmentId(deptId);
                }
            }

            if (StringUtils.isNotBlank(indexResult)){
                int index = list.indexOf(salesman )+2;
                indexResult = "第"+index+"行数据有误："+indexResult+"<br/>";
                result += indexResult;
                continue;
            }

            salesman.setApprove(CmsConstant.APPROVING);
        }
        if (StringUtils.isBlank(result)){
            this.saveBatch(list);
        }

        return result;
    }


    @Override
    public Map<String, String> getSalesmanIdNameMap() {
        Salesman entity = new Salesman();
        entity.setApprove(CmsConstant.PASSED);
        List<Salesman> list = findList(entity);
        if (CollectionUtils.isEmpty(list)){
            return MapUtils.EMPTY_MAP;
        }

        Map<String,String> result = new HashMap<>();
        for (Salesman salesman : list) {
            result.put(salesman.getId(),salesman.getName());
        }

        return result;
    }

    @Override
    public Map<String, Salesman> getIdSalesmanMap() {
        Salesman entity = new Salesman();
        entity.setApprove(CmsConstant.PASSED);
        List<Salesman> list = findList(entity);
        if (CollectionUtils.isEmpty(list)){
            return MapUtils.EMPTY_MAP;
        }

        Map<String,Salesman> result = new HashMap<>();
        for (Salesman salesman : list) {
            result.put(salesman.getId(),salesman);
        }

        return result;
    }

    @Override
    public Map<String, String> getSalesmanNameIdMap() {
        Salesman entity = new Salesman();
        entity.setApprove(CmsConstant.PASSED);
        List<Salesman> list = findList(entity);
        if (CollectionUtils.isEmpty(list)){
            return MapUtils.EMPTY_MAP;
        }

        Map<String,String> result = new HashMap<>();
        for (Salesman salesman : list) {
            result.put(salesman.getName(),salesman.getId());
        }

        return result;
    }
}
