package com.ace.app.cms.privilege.dao;


import com.ace.app.cms.privilege.domain.SystemPrivilege;
import com.ace.framework.base.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
* 角色
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2015-12-03 11:21:26
*/
@Repository
public class SystemPrivilegeDao extends BaseDao<SystemPrivilege> {

    private static final String STATEMENT = "com.ace.app.cms.privilege.domain.SystemPrivilege.";

    public void add (SystemPrivilege systemPrivilege){
        insert(STATEMENT+"insert",systemPrivilege);
    }
    public void update (SystemPrivilege systemPrivilege){
         update(STATEMENT+"update",systemPrivilege);
    }
    public void updateBySelective (SystemPrivilege systemPrivilege){
          update(STATEMENT+"updateBySelective",systemPrivilege);
    }

    public void delete (String id){
        delete(STATEMENT+"delete",id);
    }

    public SystemPrivilege getObject (String id){
        return getObject(STATEMENT+"getObject",id);
    }

    public Integer getCount(){
        return getOne(STATEMENT+"getCount",null);
    }

    public Integer getCountBySelective(SystemPrivilege systemPrivilege){
        return getObject(STATEMENT+"getCountBySelective",systemPrivilege);
    }

    public List<SystemPrivilege> findList(SystemPrivilege systemPrivilege){
        return findList(STATEMENT+"findList",systemPrivilege);
    }


}
