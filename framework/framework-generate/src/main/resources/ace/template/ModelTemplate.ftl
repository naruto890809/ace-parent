package ${bussPackage}.${module}.${logicModule}.model;


import ${bussPackage}.${module}.${logicModule}.domain.${clazzName};
import com.ace.framework.base.BaseModel;
import com.ace.framework.util.ExecutionContext;

/**
* ${codeName}
* WuZhiWei v1.0
* @author ${annotation.authorName}
* @since  ${annotation.date}
*/
public class ${clazzName}Model extends BaseModel {
    private ${clazzName} ${lowerClazzName};



    public ${clazzName} get${clazzName}() {

        return ${lowerClazzName};
    }

    public void set${clazzName}(${clazzName} ${lowerClazzName}) {
        ${lowerClazzName}.setParentCorpCode(ExecutionContext.getParentCorpCode());
        ${lowerClazzName}.setCorpCode(ExecutionContext.getCorpCode());

        this.${lowerClazzName} = ${lowerClazzName};
    }
}
