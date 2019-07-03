package com.ace.app.cms.drug.service;

import com.ace.app.cms.drug.domain.Biz;
import java.util.List;
import com.ace.framework.base.BaseCrudService;
import com.ace.app.cms.ExcelService;

/**
* 业务管理
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:30
*/
public interface BizService extends BaseCrudService<Biz> , ExcelService<Biz> {


    Biz findForOrder(Biz biz);
}
