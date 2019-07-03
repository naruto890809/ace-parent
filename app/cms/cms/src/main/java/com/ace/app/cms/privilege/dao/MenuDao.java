package com.ace.app.cms.privilege.dao;


import com.ace.app.cms.privilege.domain.Menu;
import com.ace.framework.base.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
* 菜单
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2016-01-20 11:27:00
*/
@Repository
public class MenuDao extends BaseDao<Menu> {

    private static final String STATEMENT = "com.ace.app.cms.privilege.domain.Menu.";

    public List<Menu> findListByMenuId(String []menuIds) {
        return this.findList(STATEMENT+"findListByMenuId",menuIds);
    }

}
