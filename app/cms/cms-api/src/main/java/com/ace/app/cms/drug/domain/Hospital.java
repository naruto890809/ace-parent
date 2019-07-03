package com.ace.app.cms.drug.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseEntity;
import com.ace.framework.base.PrimaryKey;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
* 医院
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
public class Hospital extends BaseEntity {
    private static final long serialVersionUID = -684647058224085809L;

	

	@PrimaryKey
	private String id;//   主键
    @Excel(name ="医院名称")
    private String name;//   名称
    @Excel(name ="医院别名（多个以逗号分隔）")
    private String alias;//   别名，多个以逗号分隔
	private String level;//   等级，如：三甲 来自字典表，编号是：HOSPITAL_LEVEL
	private String type;//   医院性质，如：医院、卫生院 来自字典表，编号是：HOSPITAL_LEVEL
	private String departmentId;//   所属部门id
    @Excel(name ="所在省")
    private String province;//   省
    @Excel(name ="所在市")
    private String city;//   市
    @Excel(name ="所在区|县")
    private String area;//   区、县
    @Excel(name ="详细地址")
    private String address;//   详细地址
	private String approve;//   审核状态 审核中、通过（PASSED）

	///////////////////////////////////////////
    @Excel(name ="医院等级")
    private String levelName;//   等级，如：三甲 来自字典表，编号是：HOSPITAL_LEVEL
    @Excel(name ="医院性质")
    private String typeName;//   医院性质，如：医院、卫生院 来自字典表，编号是：HOSPITAL_LEVEL
	@Excel(name ="所属部门")
    private String departmentName;//   所属部门id

	private String approveText;//   审核状态 审核中（APPROVING）、通过（PASSED） 驳回 REFUSED
	private String citySearch;//   审核状态 审核中（APPROVING）、通过（PASSED） 驳回 REFUSED


    private String hospitalNameSplit;
    private List<String> hospitalNameList;

    private String hospitalAliasSplit;
    private List<String> hospitalAliasList;

    public List<String> getHospitalNameList() {
        if (StringUtils.isNotBlank(hospitalNameSplit)){
            hospitalNameList = Arrays.asList(hospitalNameSplit.split("\\s+"));
        }
        return hospitalNameList;
    }

    public void setHospitalNameList(List<String> hospitalNameList) {
        this.hospitalNameList = hospitalNameList;
    }

    public String getHospitalNameSplit() {
        return hospitalNameSplit;
    }

    public void setHospitalNameSplit(String hospitalNameSplit) {
        this.hospitalNameSplit = hospitalNameSplit;
    }


    public List<String> getHospitalAliasList() {
        if (StringUtils.isNotBlank(hospitalAliasSplit)){
            hospitalAliasList = Arrays.asList(hospitalAliasSplit.split("\\s+"));
        }
        return hospitalAliasList;
    }

    public void setHospitalAliasList(List<String> hospitalAliasList) {
        this.hospitalAliasList = hospitalAliasList;
    }

    public String getHospitalAliasSplit() {
        return hospitalAliasSplit;
    }

    public void setHospitalAliasSplit(String hospitalAliasSplit) {
        this.hospitalAliasSplit = hospitalAliasSplit;
    }




    public String getCitySearch() {
        return citySearch;
    }

    public void setCitySearch(String citySearch) {
        this.citySearch = citySearch;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
	public String getAlias() {
	    return this.alias;
	}
	public void setAlias(String alias) {
	    this.alias=alias;
	}
	public String getLevel() {
	    return this.level;
	}
	public void setLevel(String level) {
	    this.level=level;
	}
	public String getType() {
	    return this.type;
	}
	public void setType(String type) {
	    this.type=type;
	}
	public String getDepartmentId() {
	    return this.departmentId;
	}
	public void setDepartmentId(String departmentId) {
	    this.departmentId=departmentId;
	}
	public String getProvince() {
	    return this.province;
	}
	public void setProvince(String province) {
	    this.province=province;
	}
	public String getCity() {
	    return this.city;
	}
	public void setCity(String city) {
	    this.city=city;
	}
	public String getArea() {
	    return this.area;
	}
	public void setArea(String area) {
	    this.area=area;
	}
	public String getAddress() {
	    return this.address;
	}
	public void setAddress(String address) {
	    this.address=address;
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

