package com.ace.app.cms.drug.domain;

import com.ace.framework.base.BaseEntity;
import com.ace.framework.base.PrimaryKey;

import java.math.BigDecimal;

/**
* 数据字典
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
public class Chat {
    private static final long serialVersionUID = 6958014714930806932L;



	private String name;//   主键
	private BigDecimal value;//   所属业务类型编号
	private String valueStr;//   所属业务类型编号


    public Chat(String valueStr, String name) {
        this.valueStr = valueStr;
        this.name = name;
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }

    public Chat() {
    }

    public Chat(String name, BigDecimal value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}

