package com.ace.app.cms.drug.dao;


import com.ace.app.cms.drug.domain.Biz;
import com.ace.framework.base.BaseDao;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
* 业务管理
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:30
*/
@Repository
public class BizDao extends BaseDao<Biz> {

    private static final String STATEMENT = "com.ace.app.cms.drug.domain.Biz.";

    public Biz findForOrder(Biz biz){
        List<Biz> list = findList(STATEMENT + "findForOrder", biz);
        if (CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }

        return null;
    }
}
