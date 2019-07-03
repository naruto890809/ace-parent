package com.ace.app.cms.drug.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseEntity;
import com.ace.framework.base.PrimaryKey;
/**
* 部门
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
@ExcelTarget("Department")
public class Department extends BaseEntity {
    private static final long serialVersionUID = -2468650246363651305L;

	

	@PrimaryKey
	private String id;//   主键
    @Excel(name ="大区名称")
	private String fuck;//   大区
    @Excel(name ="部门名称")
	private String name;//   部门名称
    @Excel(name ="描述")
    private String description;//   描述
	private String approve;//   审核状态 审核中、通过（PASSED）
	private String pid;//   审核状态 审核中、通过（PASSED）

	///////////////////////////////////////////

	private String approveText;//   审核状态 审核中（APPROVING）、通过（PASSED） 驳回 REFUSED
	private String pName;//   审核状态 审核中（APPROVING）、通过（PASSED） 驳回 REFUSED

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getFuck() {
        return fuck;
    }

    public void setFuck(String fuck) {
        this.fuck = fuck;
    }

    public String getId() {
	    return this.id;
	}
	public void setId(String id) {
	    this.id=id;
	}
	public String getName() {
	    return this.name;
	}
	public void setName(String name) {
	    this.name=name;
	}
	public String getDescription() {
	    return this.description;
	}
	public void setDescription(String description) {
	    this.description=description;
	}
	public String getApprove() {
	    return this.approve;
	}
	public void setApprove(String approve) {
	    this.approve=approve;
	}

	public String getApproveText() {
		if (CmsConstant.APPROVING.equals(approve)){
		    approveText = CmsConstant.APPROVING_TEXT;
		}else if (CmsConstant.PASSED.equals(approve)){
		    approveText = CmsConstant.PASSED_TEXT;
		}else if (CmsConstant.REFUSED.equals(approve)){
		    approveText = CmsConstant.REFUSED_TEXT;
		}else {
		    approveText = "未定义";
		}
		return approveText;
	}
		
	public void setApproveText(String approveText) {
		this.approveText = approveText;
	}

}

