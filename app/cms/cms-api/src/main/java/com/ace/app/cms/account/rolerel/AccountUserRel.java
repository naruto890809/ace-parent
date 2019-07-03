package com.ace.app.cms.account.rolerel;

import com.ace.framework.base.BaseEntity;
import com.ace.framework.base.PrimaryKey;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* 用户客户关系
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2016-05-17 17:13:41
*/
public class AccountUserRel extends BaseEntity {


    private static final long serialVersionUID = -445334665058141993L;
    @PrimaryKey
    private String accountUserRelId;//   用户客户关系表主键id

	private String accountId;//   

	private String userId;//

    ///////////////////////////////////////////
    private List<String> accountIds;
    private String accountName;
    private String employeeCode;
    private String userName;
    private String userCode;
    private String statusText;
    private String phone;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<String> getAccountIds() {
        return accountIds;
    }

    public void setAccountIds(List<String> accountIds) {
        this.accountIds = accountIds;
    }

    public String getAccountUserRelId() {

	    return this.accountUserRelId;

	}

	public void setAccountUserRelId(String accountUserRelId) {

	    this.accountUserRelId=accountUserRelId;

	}

	public String getAccountId() {

	    return this.accountId;

	}

	public void setAccountId(String accountId) {

	    this.accountId=accountId;

	}

	public String getUserId() {

	    return this.userId;

	}

	public void setUserId(String userId) {

	    this.userId=userId;

	}
}

