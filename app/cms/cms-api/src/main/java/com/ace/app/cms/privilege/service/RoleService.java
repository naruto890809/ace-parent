package com.ace.app.cms.privilege.service;

import com.ace.app.cms.privilege.domain.Menu;
import com.ace.app.cms.privilege.domain.Role;
import com.ace.framework.base.Page;

import java.util.List;
import java.util.Map;

/**
 * 角色
 * WuZhiWei v1.0
 *
 * @author 代码生成器 v1.0
 * @since 2015-12-03 11:17:35
 */
public interface RoleService {

    void add(Role role);

    void update(Role role);

    void updateBySelective(Role role);

    void delete(String id);

    Role getObject(String id);

    Integer getCount();

    Integer getCountBySelective(Role role);

    List<Role> findList(Role role);

    Page<Role> search(Role role, Page<Role> page);


}
