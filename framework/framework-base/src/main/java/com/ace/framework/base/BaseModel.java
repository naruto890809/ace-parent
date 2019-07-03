package com.ace.framework.base;

import com.ace.framework.util.ExecutionContext;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.List;


public class BaseModel implements Serializable {
	/**
	 * 分页对象
	 */
	@SuppressWarnings("rawtypes")
	private Page page = new Page();
	/**
	 * 公司代码
	 */
	private String corpCode;

	/**
	 * 角色编号
	 */
	private String roleCode;

	/**
	 * 用户Id
	 */
	private String userId;
	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * elnSessionId
	 */
	private String aceSessionId;
	private String id;
	private String idsStr;
	private List<String> ids;

    public String getIdsStr() {
        return idsStr;
    }

    public void setIdsStr(String idsStr) {
        this.idsStr = idsStr;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMyjSessionId() {
        return aceSessionId;
    }

    public void setMyjSessionId(String aceSessionId) {
        this.aceSessionId = aceSessionId;
    }

    // TODO 增加其他常用属性
	public BaseModel() {

		// 清空ExtraFilds。
		ExecutionContext.setExtraFields(null);
	}



	/**
	 * @return the page
	 */
	@SuppressWarnings("rawtypes")
	public Page getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	@SuppressWarnings("rawtypes")
	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * @return the corpCode
	 */
	public String getCorpCode() {
        if (StringUtils.isEmpty(corpCode)){
            corpCode = ExecutionContext.getCorpCode();
        }

		return corpCode;
	}

	/**
	 * @param corpCode the corpCode to set
	 */
	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return ExecutionContext.getUserId();
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

}
