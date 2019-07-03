package ${bussPackage}.${module}.${logicModule}.service.impl;

import ${bussPackage}.${module}.${logicModule}.dao.${clazzName}Dao;
import ${bussPackage}.${module}.${logicModule}.domain.${clazzName};
import ${bussPackage}.${module}.${logicModule}.service.${clazzName}Service;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseCrudServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ace.framework.base.BaseDao;

import java.util.List;

/**
* ${codeName}

* WuZhiWei v1.0
* @author ${annotation.authorName}
* @since  ${annotation.date}
*/
@Service("${lowerClazzName}Service")
public class ${clazzName}ServiceImpl  extends BaseCrudServiceImpl<${clazzName}> implements ${clazzName}Service {
    @Resource
    private ${clazzName}Dao ${lowerClazzName}Dao;

    @Override
    public BaseDao<${clazzName}> getEntityDao() {
        return ${lowerClazzName}Dao;
    }

    @Override
    public List findListForExport(${clazzName} condition) {
        List list = this.findList(condition);
        return list;
    }

    @Override
    public String importBatch(List<${clazzName}> list) {
        for (${clazzName} ${lowerClazzName} : list) {
        //TODO 导入条件判断，空、重复
        //${lowerClazzName}.setApprove(CmsConstant.APPROVING);
        }

        this.saveBatch(list);
        return null;
    }

    <#--
    public void add (${clazzName} ${lowerClazzName}){
        ${lowerClazzName}Dao.add(${lowerClazzName});
    }

    public void update (${clazzName} ${lowerClazzName}){
        ${lowerClazzName}Dao.update(${lowerClazzName});
    }

    public void updateBySelective (${clazzName} ${lowerClazzName}){
        ${lowerClazzName}Dao.updateBySelective(${lowerClazzName});
    }

    public void delete (String id){
        ${lowerClazzName}Dao.delete(id);
    }

    public ${clazzName} getObject (String id){
        return ${lowerClazzName}Dao.getObject(id);
    }

    public Integer getCount(){
        return ${lowerClazzName}Dao.getCount();
    }

    public Integer getCountBySelective(${clazzName} ${lowerClazzName}){
        return ${lowerClazzName}Dao.getCountBySelective(${lowerClazzName});
    }

    public List<${clazzName}> findList(${clazzName} ${lowerClazzName}){
        return ${lowerClazzName}Dao.findList(${lowerClazzName});
    }-->

}
