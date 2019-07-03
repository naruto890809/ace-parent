package com.ace.app.cms.drug.service;

import com.ace.app.cms.drug.domain.Rebate;

import java.math.BigDecimal;
import java.util.List;
import com.ace.framework.base.BaseCrudService;
import com.ace.app.cms.ExcelService;

/**
* 返利设置
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
public interface RebateService extends BaseCrudService<Rebate> , ExcelService<Rebate> {

    BigDecimal getRebatePrice(Rebate rebate);

    String checkRebate(Rebate rebate,String checkResult );

    Rebate findForBright(Rebate rebate);
    Rebate findForDark(Rebate rebate);
}
