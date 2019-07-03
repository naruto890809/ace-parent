package com.ace.app.cms.privilege.service.impl;

import com.ace.app.cms.CmsConstant;
import com.ace.app.cms.privilege.dao.RoleDao;
import com.ace.app.cms.privilege.domain.Role;
import com.ace.app.cms.privilege.service.RoleService;
import com.ace.framework.base.Page;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 角色

* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2015-12-03 11:17:35
*/
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;


    public void add (Role role){
        roleDao.add(role);
    }

    public void update (Role role){
        roleDao.update(role);
    }

    public void updateBySelective (Role role){
        roleDao.updateBySelective(role);
    }

    public void delete (String id){
        roleDao.delete(id);
    }

    public Role getObject (String id){
        return roleDao.getObject(id);
    }

    public Integer getCount(){
        return roleDao.getCount();
    }

    public Integer getCountBySelective(Role role){
        return roleDao.getCountBySelective(role);
    }

    public List<Role> findList(Role role){
        return roleDao.findList(role);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<Role> search(Role role, Page<Role> page) {
        return roleDao.findListRole(role,page);
    }

}
