package com.ace.app.cms.privilege.domain;

import com.ace.framework.base.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;
/**
* 角色
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2015-12-03 11:21:26
*/
public class SystemPrivilege extends BaseEntity {


    private static final long serialVersionUID = 2662738832484012324L;
    private String spId;//   权限id

	private String module;//   模块

	private String privilege;//   权限值

	private String name;//   名称


    public SystemPrivilege() {
    }

    public SystemPrivilege(String module, String privilege) {
        this.module = module;
        this.privilege = privilege;
    }


    public String getSpId() {

	    return this.spId;

	}

	public void setSpId(String spId) {

	    this.spId=spId;

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

	public String getName() {

	    return this.name;

	}

	public void setName(String name) {

	    this.name=name;

	}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SystemPrivilege)) return false;

        SystemPrivilege that = (SystemPrivilege) o;

        if (!module.equals(that.module)) return false;
        if (!privilege.equals(that.privilege)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = module.hashCode();
        result = 31 * result + privilege.hashCode();
        return result;
    }
}

