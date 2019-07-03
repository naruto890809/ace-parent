package com.ace.app.cms.drug.model;


import com.ace.app.cms.drug.domain.Dic;
import com.ace.framework.base.BaseModel;
import com.ace.framework.util.ExecutionContext;

import java.util.List;

/**
* 数据字典
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
public class DicModel extends BaseModel {
    private List<Dic> dics;
    private String code;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dic> getDics() {
        return dics;
    }

    public void setDics(List<Dic> dics) {
        this.dics = dics;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
