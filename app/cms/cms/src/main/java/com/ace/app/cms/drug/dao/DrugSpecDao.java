package com.ace.app.cms.drug.dao;


import com.ace.app.cms.drug.domain.DrugSpec;
import com.ace.framework.base.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
* 药品规格
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
@Repository
public class DrugSpecDao extends BaseDao<DrugSpec> {

    private static final String STATEMENT = "com.ace.app.cms.drug.domain.DrugSpec.";

    public List<DrugSpec> findJoin(){
        return findList(STATEMENT + "findJoin","");
    }
}
