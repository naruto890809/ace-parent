package com.ace.app.cms.drug.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseEntity;
import com.ace.framework.base.PrimaryKey;
/**
* 商业公司
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
public class Company extends BaseEntity {
    private static final long serialVersionUID = -7705718882343721579L;

	

	@PrimaryKey
	private String id;//   主键
    @Excel(name ="客户名称")
    private String name;//   名称
    @Excel(name ="客户代码")
    private String code;//   代码
    @Excel(name ="别名")
    private String alias;//   别名，多个以逗号分隔
    @Excel(name ="销售渠道")
    private String channelName;//   销售渠道 如：直销、分销（来自数据字典，编号为COMPANY_CHANNEL）
	private String channel;//   销售渠道 如：直销、分销（来自数据字典，编号为COMPANY_CHANNEL）
    @Excel(name ="所在省")
    private String sellAreaName;//   所属区域id
    @Excel(name ="所在市")
    private String sellAreaId;//   所属区域id
    @Excel(name ="详细地址")
    private String address;//   详细地址
    @Excel(name ="联系人")
    private String person;//   联系人
    @Excel(name ="联系方式")
    private String contacts;//   联系方式
	private String pid;//   总公司id
	private String approve;//   审核状态 审核中、通过（PASSED）

	///////////////////////////////////////////

	private String approveText;//   审核状态 审核中（APPROVING）、通过（PASSED） 驳回 REFUSED
	private String provinceSearch;//
	private String citySearch;//

	public String getProvinceSearch() {
		return provinceSearch;
	}

	public void setProvinceSearch(String provinceSearch) {
		this.provinceSearch = provinceSearch;
	}

	public String getCitySearch() {
		return citySearch;
	}

	public void setCitySearch(String citySearch) {
		this.citySearch = citySearch;
	}

	public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getSellAreaName() {
        return sellAreaName;
    }

    public void setSellAreaName(String sellAreaName) {
        this.sellAreaName = sellAreaName;
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
	public String getCode() {
	    return this.code;
	}
	public void setCode(String code) {
	    this.code=code;
	}
	public String getAlias() {
	    return this.alias;
	}
	public void setAlias(String alias) {
	    this.alias=alias;
	}
	public String getChannel() {
	    return this.channel;
	}
	public void setChannel(String channel) {
	    this.channel=channel;
	}
	public String getSellAreaId() {
	    return this.sellAreaId;
	}
	public void setSellAreaId(String sellAreaId) {
	    this.sellAreaId=sellAreaId;
	}
	public String getAddress() {
	    return this.address;
	}
	public void setAddress(String address) {
	    this.address=address;
	}
	public String getPerson() {
	    return this.person;
	}
	public void setPerson(String person) {
	    this.person=person;
	}
	public String getContacts() {
	    return this.contacts;
	}
	public void setContacts(String contacts) {
	    this.contacts=contacts;
	}
	public String getPid() {
	    return this.pid;
	}
	public void setPid(String pid) {
	    this.pid=pid;
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

