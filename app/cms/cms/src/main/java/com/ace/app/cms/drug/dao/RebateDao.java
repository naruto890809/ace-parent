package com.ace.app.cms.drug.dao;


import com.ace.app.cms.drug.domain.Rebate;
import com.ace.framework.base.BaseDao;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
* 返利设置
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
@Repository
public class RebateDao extends BaseDao<Rebate> {

    private static final String STATEMENT = "com.ace.app.cms.drug.domain.Rebate.";

    public Rebate findForBright(Rebate rebate){
        List<Rebate> list = findList(STATEMENT + "findForBright", rebate);
        if (CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }

        return null;
    }


    public Rebate findForDark(Rebate rebate){
        List<Rebate> list = findList(STATEMENT + "findForDark", rebate);
        if (CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }

        return null;
    }
}
