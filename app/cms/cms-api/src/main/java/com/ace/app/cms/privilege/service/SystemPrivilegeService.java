package com.ace.app.cms.privilege.service;

import com.ace.app.cms.privilege.domain.SystemPrivilege;
import java.util.List;

/**
* 角色
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2015-12-03 11:21:26
*/
public interface SystemPrivilegeService  {

    public void add (SystemPrivilege systemPrivilege) ;

    public void update (SystemPrivilege systemPrivilege);

    public void updateBySelective (SystemPrivilege systemPrivilege);

    public void delete (String id);

    public SystemPrivilege getObject (String id);

    public Integer getCount();

    public Integer getCountBySelective(SystemPrivilege systemPrivilege);

    public List<SystemPrivilege> findList(SystemPrivilege systemPrivilege);


}
