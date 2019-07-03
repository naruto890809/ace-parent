package com.ace.app.cms.privilege.model;


import com.ace.app.cms.privilege.domain.Role;
import com.ace.app.cms.privilege.domain.RoleMenuRel;
import com.ace.framework.base.BaseModel;

/**
* 角色菜单
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2016-01-20 19:10:11
*/
public class RoleMenuRelModel extends BaseModel {
    private RoleMenuRel roleMenuRel;

    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public RoleMenuRel getRoleMenuRel() {
        return roleMenuRel;
    }

    public void setRoleMenuRel(RoleMenuRel roleMenuRel) {
        this.roleMenuRel = roleMenuRel;
    }
}
