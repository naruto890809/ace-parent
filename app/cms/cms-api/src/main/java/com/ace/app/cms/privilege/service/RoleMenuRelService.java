package com.ace.app.cms.privilege.service;

import com.ace.app.cms.privilege.domain.RoleMenuRel;
import java.util.List;
import com.ace.framework.base.BaseCrudService;

/**
* 角色菜单
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2016-01-20 19:10:11
*/
public interface RoleMenuRelService extends BaseCrudService<RoleMenuRel>  {

    public  List<RoleMenuRel> findListByRoleId(String roleId);

    public  List<String> findListByRoleId(String[] roleIds);
    public List<String> findMenuListByRoleId(String[] roleIds) ;
    public  int  deleteByRoId(String roleId);

}
