package com.ace.app.cms.privilege.service.impl;

import com.ace.app.cms.privilege.dao.RoleMenuRelDao;
import com.ace.app.cms.privilege.domain.RoleMenuRel;
import com.ace.app.cms.privilege.service.RoleMenuRelService;
import com.ace.framework.base.BaseCrudServiceImpl;
import com.ace.framework.base.BaseDao;
import com.ace.framework.util.ExecutionContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
* 角色菜单

* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2016-01-20 19:10:11
*/
@Service("roleMenuRelService")
public class RoleMenuRelServiceImpl  extends BaseCrudServiceImpl<RoleMenuRel> implements RoleMenuRelService {
    @Resource
    private RoleMenuRelDao roleMenuRelDao;

    @Override
    public BaseDao<RoleMenuRel> getEntityDao() {
        return roleMenuRelDao;
    }

    public List<RoleMenuRel> findListByRoleId(String roleId) {

        RoleMenuRel roleMenuRel=new RoleMenuRel();
        roleMenuRel.setRoleId(roleId);
        roleMenuRel.setParentCorpCode(ExecutionContext.getParentCorpCode());
        roleMenuRel.setCorpCode(ExecutionContext.getCorpCode());
        return roleMenuRelDao.findList(roleMenuRel);
    }



    public List<String> findListByRoleId(String[] roleIds) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("roleIds",roleIds);
        hashMap.put("parentCorpCode",ExecutionContext.getParentCorpCode());
        return roleMenuRelDao.findListByRoleId(hashMap);
    }

    public List<String> findMenuListByRoleId(String[] roleIds) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("roleIds",roleIds);
        hashMap.put("parentCorpCode",ExecutionContext.getParentCorpCode());
        return roleMenuRelDao.findMenuListByRoleId(hashMap);
    }
    @Override
    public int deleteByRoId(String roleId) {
        RoleMenuRel roleMenuRel=new RoleMenuRel();
        roleMenuRel.setRoleId(roleId);
        roleMenuRel.setCorpCode(ExecutionContext.getCorpCode());
        return roleMenuRelDao.delete(roleMenuRel);
    }
}
