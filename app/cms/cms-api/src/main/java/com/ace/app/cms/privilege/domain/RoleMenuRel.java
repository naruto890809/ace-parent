package com.ace.app.cms.privilege.domain;

import com.ace.framework.base.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;
/**
* 角色菜单
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2016-01-20 19:10:11
*/
public class RoleMenuRel extends BaseEntity {


    private static final long serialVersionUID = 3167057049893151499L;
    private String roleMenuRelId;//   主键id

	private String menuId;//   

	private String roleId;//   

    private Integer menu;// 1 是菜单  0 不是菜单是权限

	public String getRoleMenuRelId() {

	    return this.roleMenuRelId;

	}

	public void setRoleMenuRelId(String roleMenuRelId) {

	    this.roleMenuRelId=roleMenuRelId;

	}

	public String getMenuId() {

	    return this.menuId;

	}

	public void setMenuId(String menuId) {

	    this.menuId=menuId;

	}

	public String getRoleId() {

	    return this.roleId;

	}

	public void setRoleId(String roleId) {

	    this.roleId=roleId;

	}

    public Integer getMenu() {
        return menu;
    }

    public void setMenu(Integer menu) {
        this.menu = menu;
    }
}

