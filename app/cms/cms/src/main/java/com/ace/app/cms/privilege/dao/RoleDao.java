package com.ace.app.cms.privilege.dao;


import com.ace.app.cms.privilege.domain.Role;
import com.ace.framework.base.BaseDao;
import com.ace.framework.base.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
* 角色
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2015-12-03 11:17:35
*/
@Repository
public class RoleDao extends BaseDao<Role> {

    private static final String STATEMENT = "com.ace.app.cms.privilege.domain.Role.";

    public void add (Role role){
        insert(STATEMENT+"insert",role);
    }
    public void update (Role role){
         update(STATEMENT+"update",role);
    }
    public void updateBySelective (Role role){
          update(STATEMENT+"updateBySelective",role);
    }

    public void delete (String id){
        delete(STATEMENT+"delete",id);
    }

    public Role getObject (String id){
        return getObject(STATEMENT+"getObject",id);
    }

    public Integer getCount(){
        return getOne(STATEMENT+"getCount",null);
    }

    public Integer getCountBySelective(Role role){
        return getObject(STATEMENT+"getCountBySelective",role);
    }

    public List<Role> findList(Role role){
        return findList(STATEMENT+"findList",role);
    }

    public List<String> findRoleCodes(Role role) {
        return findList(STATEMENT + "findRoleCodes", role);
    }
    public Page<Role> findListRole(Role role, Page<Role> page) {
        return search(STATEMENT+"findList",role,page);
    }


}
