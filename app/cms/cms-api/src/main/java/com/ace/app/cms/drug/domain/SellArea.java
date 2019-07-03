package com.ace.app.cms.drug.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseEntity;
import com.ace.framework.base.PrimaryKey;
import org.apache.commons.lang.StringUtils;

/**
* 销售区域
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-06 21:00:55
*/
public class SellArea extends BaseEntity {
    private static final long serialVersionUID = 1901769327087946863L;


	@PrimaryKey
	private String id;//   主键
    @Excel(name = "省")
	private String province;//   省 113
    @Excel(name = "市")
	private String city;//   市0
    @Excel(name = "区")
	private String area;//   区（手动输入，不与省市联动）

	private String approve;//   审核状态 审核中（APPROVING）、通过（PASSED） 驳回 REFUSED

    ///////////////////////////////////////////
    private String approveText;//   审核状态 审核中（APPROVING）、通过（PASSED） 驳回 REFUSED
    private String provinceSearch;
    private String citySearch;
    private String areaSearch;


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

    public String getAreaSearch() {
        return areaSearch;
    }

    public void setAreaSearch(String areaSearch) {
        this.areaSearch = areaSearch;
    }

    public SellArea() {
    }

    public SellArea(String province, String city, String area) {
        this.province = province;
        this.city = city;
        this.area = area;
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

    public String getId() {

	    return this.id;

	}

	public void setId(String id) {

	    this.id=id;

	}

	public String getProvince() {
        if (StringUtils.isNotBlank(provinceSearch)){
            province = provinceSearch;
        }
	    return this.province;

	}

	public void setProvince(String province) {

	    this.province=province;

	}

	public String getCity() {
        if (StringUtils.isNotBlank(citySearch)){
            province = citySearch;
        }
	    return this.city;

	}

	public void setCity(String city) {

	    this.city=city;

	}

	public String getArea() {
        if (StringUtils.isNotBlank(areaSearch)){
            province = areaSearch;
        }
	    return this.area;

	}

	public void setArea(String area) {

	    this.area=area;

	}

	public String getApprove() {

	    return this.approve;

	}

	public void setApprove(String approve) {

	    this.approve=approve;

	}

}

