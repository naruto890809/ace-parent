package com.ace.app.cms.drug.service;

import com.ace.app.cms.drug.domain.Department;
import java.util.List;
import java.util.Map;

import com.ace.framework.base.BaseCrudService;
import com.ace.app.cms.ExcelService;

/**
* 部门
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
public interface DepartmentService extends BaseCrudService<Department> , ExcelService<Department> {

    Map<String,String> getDeptIdNameMap();
    Map<String,String> getDeptNameIdMap();

    Map<String,Department> getDeptIdDeptMap();

}
