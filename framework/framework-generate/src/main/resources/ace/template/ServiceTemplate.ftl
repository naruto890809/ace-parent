package ${bussPackage}.${module}.${logicModule}.service;

import ${bussPackage}.${module}.${logicModule}.domain.${clazzName};
import java.util.List;
import com.ace.framework.base.BaseCrudService;
import com.ace.app.cms.ExcelService;

/**
* ${codeName}
* WuZhiWei v1.0
* @author ${annotation.authorName}
* @since  ${annotation.date}
*/
public interface ${clazzName}Service extends BaseCrudService<${clazzName}> , ExcelService<${clazzName}> {
<#--

    public void add (${clazzName} ${lowerClazzName}) ;

    public void update (${clazzName} ${lowerClazzName});

    public void updateBySelective (${clazzName} ${lowerClazzName});

    public void delete (String id);

    public ${clazzName} getObject (String id);

    public Integer getCount();

    public Integer getCountBySelective(${clazzName} ${lowerClazzName});

    public List<${clazzName}> findList(${clazzName} ${lowerClazzName});
-->


}
