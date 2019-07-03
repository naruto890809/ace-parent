package com.ace.app.cms.drug.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseEntity;
import com.ace.framework.base.PrimaryKey;
/**
* 业务员
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
@ExcelTarget("Salesman")
public class Salesman extends BaseEntity {
    private static final long serialVersionUID = 6463921105504526204L;

	

	@PrimaryKey
	private String id;//   主键
	@Excel(name ="姓名")
	private String name;//   姓名
	private String departmentId;//   部门
	@Excel(name ="联系方式")
	private String contact;//   联系方式
	private String approve;//   审核状态 审核中、通过（PASSED）
    @Excel(name ="银行账号")
    private String bankNo;//   审核状态 审核中、通过（PASSED）
    @Excel(name ="开户行")
    private String bankName;//   审核状态 审核中、通过（PASSED）
	///////////////////////////////////////////

    @Excel(name ="部门名称")
    private String departmentName;//   部门
	private String approveText;//   审核状态 审核中（APPROVING）、通过（PASSED） 驳回 REFUSED

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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
	public String getDepartmentId() {
	    return this.departmentId;
	}
	public void setDepartmentId(String departmentId) {
	    this.departmentId=departmentId;
	}
	public String getContact() {
	    return this.contact;
	}
	public void setContact(String contact) {
	    this.contact=contact;
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

