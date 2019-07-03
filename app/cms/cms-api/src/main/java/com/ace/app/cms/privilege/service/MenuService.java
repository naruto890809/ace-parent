package com.ace.app.cms.privilege.service;

import com.ace.app.cms.privilege.domain.Menu;
import java.util.List;
import com.ace.framework.base.BaseCrudService;

/**
* 菜单
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2016-01-20 11:27:00
*/
public interface MenuService extends BaseCrudService<Menu>  {

    public List<Menu> findListByMenuId(String[] menuIds );

    public List<Menu> resolveTree(List<Menu> allTeacherTypes, String rootId) ;
}
