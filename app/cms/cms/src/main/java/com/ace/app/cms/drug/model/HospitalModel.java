package com.ace.app.cms.drug.model;


import com.ace.app.cms.drug.domain.Department;
import com.ace.app.cms.drug.domain.Dic;
import com.ace.app.cms.drug.domain.Hospital;
import com.ace.framework.base.BaseModel;
import com.ace.framework.util.ExecutionContext;

import java.util.List;

/**
* 医院
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
public class HospitalModel extends BaseModel {
    private Hospital hospital;
    private List<Department> departments;
    private List<Dic> levels;
    private List<Dic> types;
    private List<String> areas;

    public List<String> getAreas() {
        return areas;
    }

    public void setAreas(List<String> areas) {
        this.areas = areas;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Dic> getLevels() {
        return levels;
    }

    public void setLevels(List<Dic> levels) {
        this.levels = levels;
    }

    public List<Dic> getTypes() {
        return types;
    }

    public void setTypes(List<Dic> types) {
        this.types = types;
    }

    public Hospital getHospital() {

        return hospital;
    }

    public void setHospital(Hospital hospital) {
        hospital.setParentCorpCode(ExecutionContext.getParentCorpCode());
        hospital.setCorpCode(ExecutionContext.getCorpCode());

        this.hospital = hospital;
    }
}
