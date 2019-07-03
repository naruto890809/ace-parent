package com.ace.app.cms.drug.service.impl;

import com.ace.app.cms.drug.dao.DepartmentDao;
import com.ace.app.cms.drug.domain.Department;
import com.ace.app.cms.drug.service.DepartmentService;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseCrudServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ace.framework.base.BaseDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 部门

* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
@Service("departmentService")
public class DepartmentServiceImpl  extends BaseCrudServiceImpl<Department> implements DepartmentService {
    @Resource
    private DepartmentDao departmentDao;

    @Override
    public BaseDao<Department> getEntityDao() {
        return departmentDao;
    }

    @Override
    public List findListForExport(Department condition) {
        List list = this.findList(condition);
        return list;
    }

    @Override
    public String importBatch(List<Department> list,String extParam) {
        String result = "";
        List<Department> departments = this.findList(null);
        List<String> db = new ArrayList<>(departments.size());
        for (Department department : departments) {
            db.add(department.getName());
        }

        for (Department department : list) {
            String indexResult = "";
            String name = department.getName().trim();
            if (StringUtils.isBlank(name)){
                indexResult += "部门名字为空、";
            }else {
                department.setName(department.getName().trim());
                if ( db.contains(name)){
                    indexResult += "部门名字已存在";
                }
            }

            if (StringUtils.isNotBlank(indexResult)){
                int index = list.indexOf(department )+2;
                indexResult = "第"+index+"行数据有误："+indexResult+"<br/>";
                result += indexResult;
                continue;
            }

            department.setApprove(CmsConstant.APPROVING);
        }
        if (StringUtils.isBlank(result)){
            this.saveBatch(list);
        }

        return result;
    }


    @Override
    public Map<String, String> getDeptIdNameMap() {
        Map<String, String> result = new HashMap<>();
        Department entity = new Department();
        entity.setApprove(CmsConstant.PASSED);
        List<Department> list = this.findList(entity);
        for (Department department : list) {
            result.put(department.getId(),department.getName());
        }

        return result;
    }

    @Override
    public Map<String,Department> getDeptIdDeptMap() {
        Map<String, Department> result = new HashMap<>();
        Department entity = new Department();
        entity.setApprove(CmsConstant.PASSED);
        List<Department> list = this.findList(entity);
        for (Department department : list) {
            result.put(department.getId(),department);
        }

        return result;
    }


    @Override
    public Map<String, String> getDeptNameIdMap() {
        Map<String, String> result = new HashMap<>();
        Department entity = new Department();
        entity.setApprove(CmsConstant.PASSED);
        List<Department> list = this.findList(entity);
        for (Department department : list) {
            result.put(department.getName(),department.getId());
        }

        return result;
    }
}
