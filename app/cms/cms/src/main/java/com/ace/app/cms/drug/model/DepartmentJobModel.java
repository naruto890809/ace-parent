package com.ace.app.cms.drug.model;


import com.ace.app.cms.drug.domain.Department;
import com.ace.app.cms.drug.domain.DepartmentJob;
import com.ace.app.cms.drug.domain.Drug;
import com.ace.framework.base.BaseModel;
import com.ace.framework.util.ExecutionContext;

import java.util.List;

/**
* 任务管理
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-10-18 21:27:37
*/
public class DepartmentJobModel extends BaseModel {
    private DepartmentJob departmentJob;
    private String departmentId;
    private List<Department> departments;
    private List<Drug> drugs;

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public DepartmentJob getDepartmentJob() {

        return departmentJob;
    }

    public void setDepartmentJob(DepartmentJob departmentJob) {
        departmentJob.setParentCorpCode(ExecutionContext.getParentCorpCode());
        departmentJob.setCorpCode(ExecutionContext.getCorpCode());

        this.departmentJob = departmentJob;
    }
}
