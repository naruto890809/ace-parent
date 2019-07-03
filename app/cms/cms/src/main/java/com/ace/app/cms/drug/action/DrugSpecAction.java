package com.ace.app.cms.drug.action;


import com.ace.app.cms.drug.domain.DrugSpec;
import com.ace.app.cms.drug.model.DrugSpecModel;
import com.ace.app.cms.drug.service.DrugSpecService;
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
* 药品规格
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
@Scope("prototype")
public class DrugSpecAction  extends BaseAction<DrugSpecModel> {
    @Resource
    private DrugSpecService drugSpecService;
    @Resource
    private AccountService accountService;


    public String index() {
        super.setTarget("index");
        return COMMON;
    }

    public String addOrEditIndex(){
        String id = model.getId();
        DrugSpec drugSpec = null;
        if (StringUtils.isBlank(id)){
            drugSpec = new DrugSpec();
        }else {
            drugSpec = drugSpecService.getById(id);
        }

        model.setDrugSpec(drugSpec);
        super.setTarget("addOrEdit");
        return COMMON;
    }

    public String addOrEdit(){
        DrugSpec drugSpec = model.getDrugSpec();
        if (StringUtils.isBlank(drugSpec.getId())){
            drugSpec.setId(null);
            drugSpecService.save(drugSpec);
        }else {
            drugSpecService.update(drugSpec);
        }

        return renderJson(JsonResultUtil.location("drugSpec.index.do"));
    }

    public String search() {
        DrugSpec drugSpec = model.getDrugSpec();
        if(drugSpec==null){
            drugSpec=new DrugSpec();
        }

        drugSpec.setCorpCode(ExecutionContext.getCorpCode());
        Page<DrugSpec>  page = model.getPage();
        page = drugSpecService.search(drugSpec, page);
        List<DrugSpec> rows = page.getRows();
        if (CollectionUtils.isEmpty(rows)){
            return renderJson(page);
        }

        Map<String,String> aidNameMap = accountService.getAidNameMap();
        for (DrugSpec row : rows) {
            row.setCreateByName(aidNameMap.get(row.getCreateBy()));
        }

        return renderJson(page);
    }


    /**
    * 删除
    * @return
    */
    public String remove() {
        drugSpecService.deleteById(model.getId());
        return renderJson(JsonResultUtil.location("drugSpec.index.do"));
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
        DrugSpec drugSpec = new DrugSpec();
        drugSpec.setIds(ids);
//        drugSpec.setApprove(CmsConstant.PASSED);
        drugSpecService.update(drugSpec);
        return renderJson(JsonResultUtil.location("drugSpec.index.do"));
    }

}
