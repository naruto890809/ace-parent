package ${bussPackage}.${module}.${logicModule}.action;


import ${bussPackage}.${module}.${logicModule}.domain.${clazzName};
import ${bussPackage}.${module}.${logicModule}.model.${clazzName}Model;
import ${bussPackage}.${module}.${logicModule}.service.${clazzName}Service;
import com.ace.framework.base.BaseAction;
import com.ace.framework.base.JsonResultUtil;
import com.ace.framework.base.Page;
import com.ace.framework.util.ExecutionContext;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import com.ace.app.cms.CmsConstant;
import java.util.Arrays;
import com.ace.app.cms.account.AccountService;

/**
* ${codeName}
* WuZhiWei v1.0
* @author ${annotation.authorName}
* @since  ${annotation.date}
*/
@Scope("prototype")
public class ${clazzName}Action  extends BaseAction<${clazzName}Model> {
    @Resource
    private ${clazzName}Service ${lowerClazzName}Service;
    @Resource
    private AccountService accountService;


    public String index() {
        super.setTarget("index");
        return COMMON;
    }

    public String addOrEditIndex(){
        String id = model.getId();
        ${clazzName} ${lowerClazzName} = null;
        if (StringUtils.isBlank(id)){
            ${lowerClazzName} = new ${clazzName}();
        }else {
            ${lowerClazzName} = ${lowerClazzName}Service.getById(id);
        }

        model.set${clazzName}(${lowerClazzName});
        super.setTarget("addOrEdit");
        return COMMON;
    }

    public String addOrEdit(){
        ${clazzName} ${lowerClazzName} = model.get${clazzName}();
        if (StringUtils.isBlank(${lowerClazzName}.getId())){
            ${lowerClazzName}.setId(null);
            ${lowerClazzName}Service.save(${lowerClazzName});
        }else {
            ${lowerClazzName}Service.update(${lowerClazzName});
        }

        return renderJson(JsonResultUtil.location("${lowerClazzName}.index.do"));
    }

    public String search() {
        ${clazzName} ${lowerClazzName} = model.get${clazzName}();
        if(${lowerClazzName}==null){
            ${lowerClazzName}=new ${clazzName}();
        }

        ${lowerClazzName}.setCorpCode(ExecutionContext.getCorpCode());
        Page<${clazzName}>  page = model.getPage();
        page = ${lowerClazzName}Service.search(${lowerClazzName}, page);
        List<${clazzName}> rows = page.getRows();
        if (CollectionUtils.isEmpty(rows)){
            return renderJson(page);
        }

        Map<String,String> aidNameMap = accountService.getAidNameMap();
        for (${clazzName} row : rows) {
            row.setCreateByName(aidNameMap.get(row.getCreateBy()));
        }

        return renderJson(page);
    }


    /**
    * 删除
    * @return
    */
    public String remove() {
        ${lowerClazzName}Service.deleteById(model.getId());
        return renderJson(JsonResultUtil.location("${lowerClazzName}.index.do"));
    }

    /**
    * 审批
    * @return
    */
    public String approve() {
        String idsStr = model.getIdsStr();
        if (StringUtils.isEmpty(idsStr)){
            return renderJson(JsonResultUtil.err("请选择操作的列"));
        }

        List<String> ids = Arrays.asList(idsStr.split(","));
        ${clazzName} ${lowerClazzName} = new ${clazzName}();
        ${lowerClazzName}.setIds(ids);
        ${lowerClazzName}.setApprove(CmsConstant.PASSED);
        ${lowerClazzName}Service.update(${lowerClazzName});
        return renderJson(JsonResultUtil.location("${lowerClazzName}.index.do"));
    }

}
