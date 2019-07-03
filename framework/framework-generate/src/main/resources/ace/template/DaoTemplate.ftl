package ${bussPackage}.${module}.${logicModule}.dao;


import ${bussPackage}.${module}.${logicModule}.domain.${clazzName};
import com.ace.framework.base.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
* ${codeName}
* WuZhiWei v1.0
* @author ${annotation.authorName}
* @since  ${annotation.date}
*/
@Repository
public class ${clazzName}Dao extends BaseDao<${clazzName}> {

    private static final String STATEMENT = "${bussPackage}.${module}.${logicModule}.domain.${clazzName}.";
    <#--
    public void add (${clazzName} ${lowerClazzName}){
        insert(STATEMENT+"insert",${lowerClazzName});
    }
    public void update (${clazzName} ${lowerClazzName}){
         update(STATEMENT+"update",${lowerClazzName});
    }
    public void updateBySelective (${clazzName} ${lowerClazzName}){
          update(STATEMENT+"updateBySelective",${lowerClazzName});
    }

    public void delete (String id){
        delete(STATEMENT+"delete",id);
    }

    public ${clazzName} getObject (String id){
        return getObject(STATEMENT+"getObject",id);
    }

    public Integer getCount(){
        return getOne(STATEMENT+"getCount",null);
    }

    public Integer getCountBySelective(${clazzName} ${lowerClazzName}){
        return getObject(STATEMENT+"getCountBySelective",${lowerClazzName});
    }

    public List<${clazzName}> findList(${clazzName} ${lowerClazzName}){
        return findList(STATEMENT+"findList",${lowerClazzName});
    }-->

}
