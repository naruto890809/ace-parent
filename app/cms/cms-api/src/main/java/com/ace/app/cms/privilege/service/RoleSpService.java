package com.ace.app.cms.privilege.service;

import com.ace.app.cms.privilege.domain.RoleSp;
import java.util.List;

/**
* 角色
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2015-12-03 11:20:45
*/
public interface RoleSpService  {

    public void add (RoleSp roleSp) ;

    public void update (RoleSp roleSp);

    public void updateBySelective (RoleSp roleSp);

    public void delete (String id);

    public RoleSp getObject (String id);

    public Integer getCount();

    public Integer getCountBySelective(RoleSp roleSp);

    public List<RoleSp> findList(RoleSp roleSp);

    public List<RoleSp> findList(String id);

    public List<RoleSp> findListByRoleIds(String[] roleIds);

}
