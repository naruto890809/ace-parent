package com.ace.app.cms.privilege.service.impl;

import com.ace.app.cms.privilege.dao.SystemPrivilegeDao;
import com.ace.app.cms.privilege.domain.SystemPrivilege;
import com.ace.app.cms.privilege.service.SystemPrivilegeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* 角色

* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2015-12-03 11:21:26
*/
@Service("systemPrivilegeService")
public class SystemPrivilegeServiceImpl implements SystemPrivilegeService {
    @Resource
    private SystemPrivilegeDao systemPrivilegeDao;


    public void add (SystemPrivilege systemPrivilege){
        systemPrivilegeDao.add(systemPrivilege);
    }

    public void update (SystemPrivilege systemPrivilege){
        systemPrivilegeDao.update(systemPrivilege);
    }

    public void updateBySelective (SystemPrivilege systemPrivilege){
        systemPrivilegeDao.updateBySelective(systemPrivilege);
    }

    public void delete (String id){
        systemPrivilegeDao.delete(id);
    }

    public SystemPrivilege getObject (String id){
        return systemPrivilegeDao.getObject(id);
    }

    public Integer getCount(){
        return systemPrivilegeDao.getCount();
    }

    public Integer getCountBySelective(SystemPrivilege systemPrivilege){
        return systemPrivilegeDao.getCountBySelective(systemPrivilege);
    }

    public List<SystemPrivilege> findList(SystemPrivilege systemPrivilege){
        return systemPrivilegeDao.findList(systemPrivilege);
    }

}
