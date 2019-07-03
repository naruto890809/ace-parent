package com.ace.app.cms.drug.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseEntity;
import com.ace.framework.base.PrimaryKey;
/**
* 数据字典
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
public class Dic extends BaseEntity {
    private static final long serialVersionUID = 6958014714930806932L;

	

	@PrimaryKey
	private String id;//   主键
	private String code;//   所属业务类型编号
	private String name;//   名称


    ////////////////////////////////
    private String des;//   描述

    public Dic(String code, String name, String des) {
        this.code = code;
        this.name = name;
        this.des = des;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Dic() {
    }

    public Dic(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getId() {
	    return this.id;
	}
	public void setId(String id) {
	    this.id=id;
	}
	public String getCode() {
	    return this.code;
	}
	public void setCode(String code) {
	    this.code=code;
	}
	public String getName() {
	    return this.name;
	}
	public void setName(String name) {
	    this.name=name;
	}

}

