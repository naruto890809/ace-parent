package com.ace.app.cms.account.proRel;

import com.ace.framework.base.BaseEntity;

import java.util.List;

/**
* 用户项目关联
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2015-12-30 16:42:17
*/
public class AccountProRel extends BaseEntity {


    private static final long serialVersionUID = 5159363953402068593L;
    private String accountProjectRelId;//

	private String projectId;//   

	private String accountId;//   

    //////////////////////非持久化属性//////////////////////////
    private String proCode;//   编号
    private String proName;//   名字
    private String proId;//   项目id
    private List<String> accountProjectRelIds;
    private List<String> projectIds;

    public List<String> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<String> projectIds) {
        this.projectIds = projectIds;
    }

    public List<String> getAccountProjectRelIds() {
        return accountProjectRelIds;
    }

    public void setAccountProjectRelIds(List<String> accountProjectRelIds) {
        this.accountProjectRelIds = accountProjectRelIds;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getAccountProjectRelId() {

	    return this.accountProjectRelId;

	}

	public void setAccountProjectRelId(String accountProjectRelId) {

	    this.accountProjectRelId=accountProjectRelId;

	}

	public String getProjectId() {

	    return this.projectId;

	}

	public void setProjectId(String projectId) {

	    this.projectId=projectId;

	}

	public String getAccountId() {

	    return this.accountId;

	}

	public void setAccountId(String accountId) {

	    this.accountId=accountId;

	}
}

