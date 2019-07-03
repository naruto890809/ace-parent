package com.ace.app.cms.drug.service.impl;

import com.ace.app.cms.drug.dao.DepartmentJobDao;
import com.ace.app.cms.drug.domain.DepartmentJob;
import com.ace.app.cms.drug.service.DepartmentJobService;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseCrudServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ace.framework.base.BaseDao;

import java.util.List;

/**
* 任务管理

* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-10-18 21:27:37
*/
@Service("departmentJobService")
public class DepartmentJobServiceImpl  extends BaseCrudServiceImpl<DepartmentJob> implements DepartmentJobService {
    @Resource
    private DepartmentJobDao departmentJobDao;

    @Override
    public BaseDao<DepartmentJob> getEntityDao() {
        return departmentJobDao;
    }



}
