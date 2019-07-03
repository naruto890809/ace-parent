package com.ace.app.cms.privilege.service.impl;

import com.ace.app.cms.privilege.dao.RoleSpDao;
import com.ace.app.cms.privilege.domain.RoleSp;
import com.ace.app.cms.privilege.service.RoleSpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* 角色

* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2015-12-03 11:20:45
*/
@Service("roleSpService")
public class RoleSpServiceImpl implements RoleSpService {
    @Resource
    private RoleSpDao roleSpDao;


    public void add (RoleSp roleSp){
        roleSpDao.add(roleSp);
    }

    public void update (RoleSp roleSp){
        roleSpDao.update(roleSp);
    }

    public void updateBySelective (RoleSp roleSp){
        roleSpDao.updateBySelective(roleSp);
    }

    public void delete (String id){
        roleSpDao.delete(id);
    }

    public RoleSp getObject (String id){
        return roleSpDao.getObject(id);
    }

    public Integer getCount(){
        return roleSpDao.getCount();
    }

    public Integer getCountBySelective(RoleSp roleSp){
        return roleSpDao.getCountBySelective(roleSp);
    }

    public List<RoleSp> findList(RoleSp roleSp){
        return roleSpDao.findList(roleSp);
    }

    @Override
    public List<RoleSp> findList(String id) {
        RoleSp roleSp =new RoleSp();
        roleSp.setRoleSpId(id);
        return roleSpDao.findList(roleSp);
    }

    @Override
    public List<RoleSp> findListByRoleIds(String[] roleIds) {
       return roleSpDao.findListByRoleIds(roleIds);
    }

}
