package com.ace.app.cms.privilege.domain;

import com.ace.framework.base.BaseEntity;
import com.ace.framework.base.PrimaryKey;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* 角色
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2015-12-03 11:17:35
*/
public class Role extends BaseEntity {
    private static final long serialVersionUID = -3312584313474232187L;

    @PrimaryKey
    private String roleId;//   角色id

	private String roleName;//   角色名称

    private String roleCode; //角色编号

    private Integer visible;//   1 true 0 false 是否显示

    private List<String> roleIds;
    private List<String> roleCodes;


    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleId() {

	    return this.roleId;

	}

	public void setRoleId(String roleId) {

	    this.roleId=roleId;

	}

	public String getRoleName() {

	    return this.roleName;

	}

	public void setRoleName(String roleName) {

	    this.roleName=roleName;

	}
}

