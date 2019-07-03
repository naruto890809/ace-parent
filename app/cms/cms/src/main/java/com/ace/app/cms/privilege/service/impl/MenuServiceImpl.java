package com.ace.app.cms.privilege.service.impl;

import com.ace.app.cms.privilege.dao.MenuDao;
import com.ace.app.cms.privilege.domain.Menu;
import com.ace.app.cms.privilege.service.MenuService;
import com.ace.framework.base.BaseCrudServiceImpl;
import com.ace.framework.base.BaseDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* 菜单

* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2016-01-20 11:27:00
*/
@Service("menuService")
public class MenuServiceImpl  extends BaseCrudServiceImpl<Menu> implements MenuService {
    @Resource
    private MenuDao menuDao;

    @Override
    public BaseDao<Menu> getEntityDao() {
        return menuDao;
    }

    @Override
    public List<Menu> findListByMenuId(String []menuIds) {
        return menuDao.findListByMenuId(menuIds);
    }

    /**
     * 解析树
     */
    public List<Menu> resolveTree(List<Menu> allTeacherTypes, String rootId) {
        List<Menu> rootNodes = getRootNode(allTeacherTypes, rootId);
        for (Menu root : rootNodes) {
            addTreeNode(root, allTeacherTypes);
        }

        return rootNodes;
    }
    /**
     * 为指定的树节点增加子孙节点
     */
    private void addTreeNode(Menu parentNode, List<Menu> ecTypes) {
        for (Menu t : ecTypes) {
            if (t.getParentId().equals(parentNode.getMenuId())) {
                // 构造对象
                if(StringUtils.isNotBlank(t.getPrivilege())||StringUtils.isNotBlank(t.getModule())){
                    continue;
                }
                addTreeNode(t, ecTypes);
                if (parentNode.getSubMenuList() == null) {
                    parentNode.setSubMenuList(new ArrayList<Menu>());
                }
                parentNode.getSubMenuList().add(t);
            }
        }
    }
    /**
     * 获取根节点
     */
    private List<Menu> getRootNode(List<Menu> trees, String rootId) {
        List<Menu> ret = new ArrayList<Menu>();
        for (int i = 0; i < trees.size(); i++) {
            Menu menu = trees.get(i);
            if (isSame(rootId, menu.getParentId())) {
                ret.add(menu);
                trees.remove(i);
                i = i - 1;
            }
        }

        return ret;
    }
    /**
     * 判断两个字符串是否一样
     */
    private boolean isSame(String str1, String str2) {
        if (null == str1 && null == str2) {
            return true;
        } else if (null != str1 && null != str2 && (str1.equals(str2))) {
            return true;
        } else {
            return false;
        }
    }
}
