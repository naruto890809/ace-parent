package com.ace.app.cms.privilege.dao;


import com.ace.app.cms.privilege.domain.RoleMenuRel;
import com.ace.framework.base.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
/**
* 角色菜单
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2016-01-20 19:10:11
*/
@Repository
public class RoleMenuRelDao extends BaseDao<RoleMenuRel> {

    private static final String STATEMENT = "com.ace.app.cms.privilege.domain.RoleMenuRel.";

    public List<String> findListByRoleId(HashMap<String, Object> hashMap ) {

        return this.findList(STATEMENT+"findListByRoleId",hashMap);
    }

    public List<String> findMenuListByRoleId(HashMap<String, Object> hashMap ) {

        return this.findList(STATEMENT+"findMenuListByRoleId",hashMap);
    }
}
