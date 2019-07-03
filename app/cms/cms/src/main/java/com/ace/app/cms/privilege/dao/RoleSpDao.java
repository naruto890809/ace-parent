package com.ace.app.cms.privilege.dao;


import com.ace.app.cms.privilege.domain.RoleSp;
import com.ace.framework.base.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
* 角色
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2015-12-03 11:20:45
*/
@Repository
public class RoleSpDao extends BaseDao<RoleSp> {

    private static final String STATEMENT = "com.ace.app.cms.privilege.domain.RoleSp.";

    public void add (RoleSp roleSp){
        insert(STATEMENT+"insert",roleSp);
    }
    public void update (RoleSp roleSp){
         update(STATEMENT+"update",roleSp);
    }
    public void updateBySelective (RoleSp roleSp){
          update(STATEMENT+"updateBySelective",roleSp);
    }

    public void delete (String id){
        delete(STATEMENT+"delete",id);
    }

    public RoleSp getObject (String id){
        return getObject(STATEMENT+"getObject",id);
    }

    public Integer getCount(){
        return getOne(STATEMENT+"getCount",null);
    }

    public Integer getCountBySelective(RoleSp roleSp){
        return getObject(STATEMENT+"getCountBySelective",roleSp);
    }

    public List<RoleSp> findList(RoleSp roleSp){
        return findList(STATEMENT+"findList",roleSp);
    }

    public List<RoleSp> findListByRoleIds(String[] roleIds) {
        return findList(STATEMENT+"findListByRoleIds",roleIds);
    }

}
