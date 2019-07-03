package com.ace.app.cms.privilege.domain;

import com.ace.framework.base.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;
/**
* 角色
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2015-12-03 11:20:45
*/
public class RoleSp extends BaseEntity {


    private static final long serialVersionUID = 4451579267362547806L;
    private String roleSpId;//   角色权限id

	private String roleId;//   

	private String module;//   模块

	private String privilege;//   权限值

    public RoleSp() {
    }

    public RoleSp(String privilege, String module) {
        this.privilege = privilege;
        this.module = module;
    }

    public String getRoleSpId() {

	    return this.roleSpId;

	}

	public void setRoleSpId(String roleSpId) {

	    this.roleSpId=roleSpId;

	}

	public String getRoleId() {

	    return this.roleId;

	}

	public void setRoleId(String roleId) {

	    this.roleId=roleId;

	}

	public String getModule() {

	    return this.module;

	}

	public void setModule(String module) {

	    this.module=module;

	}

	public String getPrivilege() {

	    return this.privilege;

	}

	public void setPrivilege(String privilege) {

	    this.privilege=privilege;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleSp)) return false;

        RoleSp roleSp = (RoleSp) o;

        if (!module.equals(roleSp.module)) return false;
        if (!privilege.equals(roleSp.privilege)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = module.hashCode();
        result = 31 * result + privilege.hashCode();
        return result;
    }
}

