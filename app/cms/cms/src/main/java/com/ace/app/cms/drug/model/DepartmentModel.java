package com.ace.app.cms.drug.model;


import com.ace.app.cms.drug.domain.Department;
import com.ace.framework.base.BaseModel;
import com.ace.framework.util.ExecutionContext;

/**
* 部门
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
public class DepartmentModel extends BaseModel {
    private Department department;



    public Department getDepartment() {

        return department;
    }

    public void setDepartment(Department department) {
        department.setParentCorpCode(ExecutionContext.getParentCorpCode());
        department.setCorpCode(ExecutionContext.getCorpCode());

        this.department = department;
    }
}
