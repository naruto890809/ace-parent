package com.ace.app.cms.drug.model;


import com.ace.app.cms.drug.domain.Department;
import com.ace.app.cms.drug.domain.Salesman;
import com.ace.framework.base.BaseModel;
import com.ace.framework.util.ExecutionContext;

import java.util.List;

/**
* 业务员
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
public class SalesmanModel extends BaseModel {
    private Salesman salesman;
    private List<Department> departments;

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public Salesman getSalesman() {

        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        salesman.setParentCorpCode(ExecutionContext.getParentCorpCode());
        salesman.setCorpCode(ExecutionContext.getCorpCode());

        this.salesman = salesman;
    }
}
