package com.ace.app.cms.drug.service;

import com.ace.app.cms.drug.domain.Dic;
import java.util.List;
import java.util.Map;

import com.ace.framework.base.BaseCrudService;
import com.ace.app.cms.ExcelService;

/**
* 数据字典
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
public interface DicService extends BaseCrudService<Dic> , ExcelService<Dic> {

    List<Dic> findByCode(String code);

    Map<String,String> getIdNameMap();
    Map<String,String> getNameIdMap();

}
