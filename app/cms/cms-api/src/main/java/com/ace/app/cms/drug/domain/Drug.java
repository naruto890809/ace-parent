package com.ace.app.cms.drug.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseEntity;
import com.ace.framework.base.PrimaryKey;
/**
* 药品
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
public class Drug extends BaseEntity {
    private static final long serialVersionUID = -8570284981897906127L;

	

	@PrimaryKey
	private String id;//   主键
    @Excel(name = "药品名称")
    private String name;//   药品名称
    @Excel(name = "药品别名")
    private String alias;//   药品别名
    @Excel(name = "药品代码")
    private String code;//   药品别名

	private String approve;//   审核状态 审核中、通过（PASSED）

	///////////////////////////////////////////

    @Excel(name = "规格名称（多个以逗号分隔）")
    private String specName      ;//   药品别名
	@Excel(name = "规格系数（多个以逗号分隔）")
    private String coefficientText      ;//   药品别名

	private String approveText;//   审核状态 审核中（APPROVING）、通过（PASSED） 驳回 REFUSED

	public String getCoefficientText() {
		return coefficientText;
	}

	public void setCoefficientText(String coefficientText) {
		this.coefficientText = coefficientText;
	}

	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
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
	public String getAlias() {
	    return this.alias;
	}
	public void setAlias(String alias) {
	    this.alias=alias;
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

